package compilador;
import compilador.tokens.ETerminal;
import compilador.tokens.Token;

import java.util.HashMap;
import java.util.Map;
public class AnalizadorSemantico {



    /*

    //EL LLAMADO A BLOQUE DESDE PROGRAMA VA A TENER UN PARAMETRO ENTERO LLAMADO BASE, TMB UNA VARIABLE LOCAL LLAMADA DESPLAZAMIENTO

    void bloqueo(int base){

        int despe=0;
    }


    AL SEGUNDO LLAMADO VA A TENER UN PARAMETRO QUE SE VA A LLAMAR (BASE+DESPLAZAMIENTO)


    CADA VEZ QUE SE LLAMA  AUN IDENTIFICADOR HAY QUE CARGARLO EN LA TABLA, A MENOS QUE YA ESTE CARGADO.

    UN PROCEDURE ES CANDIDATO PARA CARGARLO EN LA TABLA

   */

    //El analizador semantico se encarga de cargar en una tabla los objetos tomados, el sintactico los recibe y el analizador semantico se encarga de verificar que los identificadores no se repitan, revisando en la tabla cargada, por ejemplo.

        private Map<String, Integer> tablaSimbolos;
        private Token ultimoToken;
        private boolean declarandoVariable;

        public AnalizadorSemantico() {
            tablaSimbolos = new HashMap<>();
            ultimoToken = null;
            declarandoVariable = false;
        }

        public void analizar(Token token) {
            switch (token.getTipo()) {
                case IDENTIFICADOR:
                    if (declarandoVariable) {
                        // Estamos declarando una variable, verificamos que no exista en el ámbito actual
                        String nombreVariable = token.getValor();
                        if (tablaSimbolos.containsKey(nombreVariable)) {
                            reportarError("Variable ya declarada: " + nombreVariable);
                        } else {
                            tablaSimbolos.put(nombreVariable, 0); // Asignamos un valor por defecto (0) a la variable
                        }
                    } else {
                        // Estamos usando una variable, verificamos que haya sido declarada
                        String nombreVariable = token.getValor();
                        if (!tablaSimbolos.containsKey(nombreVariable)) {
                            reportarError("Variable no declarada: " + nombreVariable);
                        }
                    }
                    break;

                case VAR:
                    if (token.getValor().equals("var")) {
                        // Comienza la declaración de una variable
                        declarandoVariable = true;
                    } else if (token.getValor().equals("begin")) {
                        // Termina la declaración de variables
                        declarandoVariable = false;
                    }
                    break;

                // Agrega más reglas semánticas según las construcciones de PL/0

                default:
                    // Otras reglas semánticas
                    break;
            }
            ultimoToken = token;
        }

        private void reportarError(String mensaje) {
            System.err.println("Error semántico en línea " + ultimoToken.getContador()
                    + ": " + mensaje);
        }




    public void guardarEnTabla(int i, String nombre, ETerminal terminal, String valor) {
    }
}
