package org.example.turismouq.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Saca de una excepción el mensaje de Oracle
 */
public final class ErrorOracle {

    private static final Pattern ORA = Pattern.compile("ORA-(\\d{5}): ([^\\n]*)");

    private ErrorOracle() {
    }

    public static String mensaje(Throwable e) {
        for (Throwable t = e; t != null; t = t.getCause()) {
            if (t.getMessage() == null) continue;
            Matcher m = ORA.matcher(t.getMessage());
            if (m.find()) {
                String codigo = m.group(1);
                // Error de llave foránea: el cliente (o la habitación) no existe
                if (codigo.equals("02291")) {
                    return "ORA-02291: El cliente o la habitación indicados no existen.";
                }
                return "ORA-" + codigo + ": " + m.group(2).trim();
            }
        }
        return "Error al procesar la solicitud: " + e.getMessage();
    }
}

