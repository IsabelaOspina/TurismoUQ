package org.example.turismouq.Repository;

import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Repository
public class IndicesRepository {

    private final JdbcTemplate jdbc;

    public IndicesRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    /** Todo el experimento va en la misma sesión: PLAN_TABLE guarda los planes por sesión. */
    public <T> T enUnaSesion(Function<Connection, T> trabajo) {
        return jdbc.execute((ConnectionCallback<T>) trabajo::apply);
    }

    public boolean existeIndice(String nombre) {
        Integer n = jdbc.queryForObject(
                "SELECT COUNT(*) FROM user_indexes WHERE index_name = ?", Integer.class, nombre.toUpperCase());
        return n != null && n > 0;
    }

    /** Borra el índice si existe (ORA-01418 = no existe). */
    public void eliminarIndice(Connection con, String nombre) {
        ejecutar(con, "BEGIN EXECUTE IMMEDIATE 'DROP INDEX " + nombre + "'; "
                + "EXCEPTION WHEN OTHERS THEN IF SQLCODE <> -1418 THEN RAISE; END IF; END;");
    }

    /** Crea el índice y actualiza estadísticas para que el optimizador lo tenga en cuenta. */
    public void crearIndice(Connection con, String sentencia, String tabla, boolean basadoEnFuncion) {
        ejecutar(con, sentencia);
        String opciones = basadoEnFuncion ? ", method_opt => 'FOR ALL HIDDEN COLUMNS SIZE 1'" : "";
        ejecutar(con, "BEGIN DBMS_STATS.GATHER_TABLE_STATS(USER, '" + tabla + "', cascade => TRUE" + opciones + "); END;");
    }

    /** Plan de ejecución (EXPLAIN PLAN) y tiempo promedio de la consulta. */
    public Map<String, Object> medir(Connection con, String consulta, int repeticiones) {
        String id = "TUQ" + (System.nanoTime() % 1_000_000_000L);
        List<Map<String, Object>> plan = new ArrayList<>();
        Number costo = null;

        try {
            try (Statement st = con.createStatement()) {
                st.execute("EXPLAIN PLAN SET STATEMENT_ID = '" + id + "' FOR " + consulta);
            }

            try (PreparedStatement ps = con.prepareStatement(
                    "SELECT id, depth, operation, options, object_name, cost, cardinality "
                            + "FROM plan_table WHERE statement_id = ? ORDER BY id")) {
                ps.setString(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        String opciones = rs.getString("options");
                        Map<String, Object> paso = new LinkedHashMap<>();
                        paso.put("nivel", rs.getInt("depth"));
                        paso.put("operacion", rs.getString("operation") + (opciones != null ? " " + opciones : ""));
                        paso.put("objeto", rs.getString("object_name"));
                        paso.put("costo", (Number) rs.getObject("cost"));
                        paso.put("filas", (Number) rs.getObject("cardinality"));
                        if (rs.getInt("id") == 0) {
                            costo = (Number) rs.getObject("cost");
                        }
                        plan.add(paso);
                    }
                }
            }

            try (PreparedStatement ps = con.prepareStatement("DELETE FROM plan_table WHERE statement_id = ?")) {
                ps.setString(1, id);
                ps.executeUpdate();
            }

            int filas = contarFilas(con, consulta); // primera ejecución, no se cuenta en el tiempo

            long inicio = System.nanoTime();
            for (int i = 0; i < repeticiones; i++) {
                contarFilas(con, consulta);
            }
            double ms = (System.nanoTime() - inicio) / 1_000_000.0 / repeticiones;

            Map<String, Object> resultado = new LinkedHashMap<>();
            resultado.put("costo", costo);
            resultado.put("msPromedio", Math.round(ms * 1000) / 1000.0);
            resultado.put("repeticiones", repeticiones);
            resultado.put("filasDevueltas", filas);
            resultado.put("plan", plan);
            return resultado;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private int contarFilas(Connection con, String consulta) throws SQLException {
        int filas = 0;
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(consulta)) {
            while (rs.next()) {
                filas++;
            }
        }
        return filas;
    }

    private void ejecutar(Connection con, String sql) {
        try (Statement st = con.createStatement()) {
            st.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}