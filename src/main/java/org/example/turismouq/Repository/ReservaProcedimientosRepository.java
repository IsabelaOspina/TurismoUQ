package org.example.turismouq.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Repository
public class ReservaProcedimientosRepository {

    @PersistenceContext
    private EntityManager em;

    /**
     * Llama a PKG_TURISMO_UQ.sp_crear_reserva
     * @return ID de la reserva creada
     */
    @Transactional
    public BigDecimal crearReserva(Long idCliente, Long idHabitacion,
                                   Date fechaIn, Date fechaOut, Integer numeroPersonas) {
        StoredProcedureQuery query = em.createStoredProcedureQuery("PKG_TURISMO_UQ.sp_crear_reserva");

        query.registerStoredProcedureParameter("p_id_cliente", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_id_habitacion", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_fecha_in", Date.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_fecha_out", Date.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_numero_personas", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_id_reserva", BigDecimal.class, ParameterMode.OUT);

        query.setParameter("p_id_cliente", idCliente);
        query.setParameter("p_id_habitacion", idHabitacion);
        query.setParameter("p_fecha_in", fechaIn);
        query.setParameter("p_fecha_out", fechaOut);
        query.setParameter("p_numero_personas", numeroPersonas);

        query.execute();

        return (BigDecimal) query.getOutputParameterValue("p_id_reserva");
    }

    /**
     * Llama a PKG_TURISMO_UQ.fn_valor_estadia
     */
    public BigDecimal calcularValorEstadia(Long idHabitacion, Date fechaIn, Date fechaOut) {
        // Una función de PL/SQL se invoca desde un SELECT
        Object valor = em.createNativeQuery(
                        "SELECT PKG_TURISMO_UQ.fn_valor_estadia(:idHabitacion, :fechaIn, :fechaOut) FROM DUAL")
                .setParameter("idHabitacion", idHabitacion)
                .setParameter("fechaIn", fechaIn)
                .setParameter("fechaOut", fechaOut)
                .getSingleResult();

        return new BigDecimal(valor.toString());
    }
}