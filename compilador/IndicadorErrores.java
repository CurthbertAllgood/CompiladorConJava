package compilador.compilador;

public class IndicadorErrores {

    public void getError(int num, int contador) throws Exception {
        switch (num) {
            case 1 -> throw new Exception("Error sintáctico: Se esperaba un IDENTIFICADOR en la línea: " + contador);
            case 2 -> throw new Exception("Error sintáctico: Se esperaba un IGUAL en la línea: " + contador);
            case 3 -> throw new Exception("Error sintáctico: Se esperaba un NÚMERO en la línea: " + contador);
            case 4 -> throw new Exception("Error sintáctico: Se esperaba un PUNTO Y COMA después de una instrucción en la línea: " + contador);
            case 5 -> throw new Exception("Error sintáctico: Se esperaba un THEN en la línea: " + contador);  // THEN para IF
            case 6 -> throw new Exception("Error sintáctico: Se esperaba una ASIGNACIÓN en la línea: " + contador);
            case 7 -> throw new Exception("Error sintáctico: Se esperaba un END para cerrar el bloque correctamente en la línea: " + contador + ". Verifique si falta un 'call' antes de la llamada al procedimiento o un punto y coma en alguna instrucción.");
            case 8 -> throw new Exception("Error sintáctico: Se esperaba un operador comparativo en la línea: " + contador);
            case 9 -> throw new Exception("Error sintáctico: Se esperaba un cierre de paréntesis en la línea: " + contador);
            case 10 -> throw new Exception("Error sintáctico: Se esperaba una apertura de paréntesis en la línea: " + contador);
            case 11 -> throw new Exception("Error sintáctico: Se esperaba un THEN después del IF en la línea: " + contador);  // THEN específico para IF
            case 12 -> throw new Exception("Error sintáctico: Se esperaba un DO después de WHILE en la línea: " + contador);  // DO específico para WHILE
        }
    }

}
