package com.aml.app.shared;

public class StringUtils {
        /**
     * Elimina espacios en blanco al inicio y al final de la cadena, y reemplaza
     * múltiples espacios internos por un solo espacio.
     * Si el resultado es una cadena vacía, devuelve null.
     * @param texto la cadena a normalizar
     * @return la cadena normalizada o null si el resultado es una cadena vacía
     */

    public static String normalizarEspacios(String texto) {
        if (texto == null)
            return null;

        String resultado = texto.trim().replaceAll("\\s+", " ");
        return resultado.isEmpty() ? null : resultado;
    }
}
