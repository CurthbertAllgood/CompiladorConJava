package compilador;

import compilador.tokens.ETerminal;
import compilador.tokens.Token;
import java.io.IOException;

public class AnalizadorSintactico {

    private final AnalizadorLexico aLex;
    private Token token;
    private final IndicadorErrores ie = new IndicadorErrores();

    public AnalizadorSintactico(AnalizadorLexico aLex) {
        this.aLex = aLex;
    }

    public void programa() throws IOException {
        System.out.println("Inicio del análisis del programa.");
        bloque();
        System.out.println("Verificando final del programa...");
        if (token.getTipo().equals(ETerminal.PUNTO)) {
            aLex.scanear();
            System.out.println("Programa terminado correctamente.");
        } else {
            throw new IllegalStateException("Error: Se esperaba un punto al final del programa.");
        }
    }

    public void bloque() throws IOException {
        System.out.println("Inicio del bloque.");
        token = aLex.scanear();
        System.out.println("Token inicial del bloque: " + token);

        // Manejo de la sección CONST
        if (token.getTipo().equals(ETerminal.CONST)) {
            manejarConst();
        }

        // Manejo de la sección VAR
        if (token.getTipo().equals(ETerminal.VAR)) {
            manejarVar();
        }

        // Manejo de la sección PROCEDURE
        while (token.getTipo().equals(ETerminal.PROCEDURE)) {
            manejarProcedure();
        }

        // Proposiciones
        System.out.println("Inicio de proposiciones en bloque.");
        proposicion();
        System.out.println("Fin del bloque.");
    }

    private void manejarConst() throws IOException {
        System.out.println("Inicio de la sección CONST.");
        do {
            token = aLex.scanear();
            System.out.println("Token en CONST: " + token);
            if (token.getTipo().equals(ETerminal.IDENTIFICADOR)) {
                token = aLex.scanear();
            } else {
                ie.getError(1, token.getContador());
            }
            if (token.getTipo().equals(ETerminal.IGUAL)) {
                token = aLex.scanear();
            } else {
                ie.getError(2, token.getContador());
            }
            if (token.getTipo().equals(ETerminal.NUMERO)) {
                token = aLex.scanear();
            } else {
                ie.getError(3, token.getContador());
            }
        } while (token.getTipo().equals(ETerminal.COMA));

        if (token.getTipo().equals(ETerminal.PUNTO_Y_COMA)) {
            token = aLex.scanear();
        } else {
            ie.getError(4, token.getContador());
        }
        System.out.println("Fin de la sección CONST.");
    }

    private void manejarVar() throws IOException {
        System.out.println("Inicio de la sección VAR.");
        do {
            token = aLex.scanear();
            System.out.println("Token en VAR: " + token);
            if (token.getTipo().equals(ETerminal.IDENTIFICADOR)) {
                token = aLex.scanear();
            } else {
                ie.getError(1, token.getContador());
            }
        } while (token.getTipo().equals(ETerminal.COMA));

        if (token.getTipo().equals(ETerminal.PUNTO_Y_COMA)) {
            token = aLex.scanear();
        } else {
            ie.getError(4, token.getContador());
        }
        System.out.println("Fin de la sección VAR.");
    }

    private void manejarProcedure() throws IOException {
        System.out.println("Inicio de la sección PROCEDURE.");
        token = aLex.scanear();
        if (token.getTipo().equals(ETerminal.IDENTIFICADOR)) {
            token = aLex.scanear();
        } else {
            ie.getError(1, token.getContador());
        }
        if (token.getTipo().equals(ETerminal.PUNTO_Y_COMA)) {
            token = aLex.scanear();
        } else {
            ie.getError(4, token.getContador());
        }
        bloque();
        if (token.getTipo().equals(ETerminal.PUNTO_Y_COMA)) {
            token = aLex.scanear();
        } else {
            ie.getError(4, token.getContador());
        }
        System.out.println("Fin de la sección PROCEDURE.");
    }

    public void proposicion() throws IOException {
        System.out.println("Inicio de proposición. Token actual: " + token);

        switch (token.getTipo()) {
            case IDENTIFICADOR -> {
                System.out.println("Proposición: IDENTIFICADOR encontrado.");
                token = aLex.scanear();
                if (token.getTipo().equals(ETerminal.ASIGNACION)) {
                    token = aLex.scanear(); // escanea ':='
                } else {
                    ie.getError(6, token.getContador()); // error si no hay ':='
                }
                expresion();
            }
            case CALL -> {
                System.out.println("Proposición: CALL encontrado.");
                token = aLex.scanear();
                if (token.getTipo().equals(ETerminal.IDENTIFICADOR)) {
                    token = aLex.scanear(); // escanea el IDENTIFICADOR
                } else {
                    ie.getError(1, token.getContador()); // error si no hay IDENTIFICADOR
                }
            }
            case BEGIN -> {
                System.out.println("Proposición: BEGIN encontrado.");
                token = aLex.scanear(); // escanea 'BEGIN'
                proposicion(); // llama al método proposicion()
                while (token.getTipo().equals(ETerminal.PUNTO_Y_COMA)) {
                    token = aLex.scanear(); // escanea ';'
                    proposicion(); // llama al método proposicion()
                }
                if (token.getTipo().equals(ETerminal.END)) {
                    token = aLex.scanear();
                } else {
                    ie.getError(7, token.getContador()); // error si no hay 'END'
                }
            }
            case IF -> {
                System.out.println("Proposición: IF encontrado.");
                token = aLex.scanear(); // escanea 'IF'
                condicion(); // llama al método condicion()
                if (token.getTipo().equals(ETerminal.THEN)) {
                    token = aLex.scanear(); // escanea 'THEN'
                } else {
                    ie.getError(5, token.getContador()); // error si no hay 'THEN'
                }
                proposicion();
            }
            case WHILE -> {
                System.out.println("Proposición: WHILE encontrado.");
                token = aLex.scanear(); // escanea 'WHILE'
                condicion(); // llama al método condicion()
                if (token.getTipo().equals(ETerminal.DO)) {
                    token = aLex.scanear(); // escanea 'DO'
                } else {
                    ie.getError(5, token.getContador()); // error si no hay 'DO'
                }
                proposicion();
            }
            case READLN -> {
                System.out.println("Proposición: READLN encontrado.");
                token = aLex.scanear(); // escanea 'READLN'
                if (token.getTipo().equals(ETerminal.ABRE_PARENTESIS)) {
                    token = aLex.scanear(); // escanea '('
                } else {
                    ie.getError(10, token.getContador()); // error si no hay '('
                }
                if (token.getTipo().equals(ETerminal.IDENTIFICADOR)) {
                    token = aLex.scanear(); // escanea el IDENTIFICADOR
                } else {
                    ie.getError(1, token.getContador()); // error si no hay IDENTIFICADOR
                }
                while (token.getTipo().equals(ETerminal.COMA)) {
                    token = aLex.scanear(); // escanea ','
                    if (token.getTipo().equals(ETerminal.IDENTIFICADOR)) {
                        token = aLex.scanear(); // escanea otro IDENTIFICADOR
                    } else {
                        ie.getError(1, token.getContador()); // error si no hay IDENTIFICADOR
                    }
                }
                if (token.getTipo().equals(ETerminal.CIERRA_PARENTESIS)) {
                    token = aLex.scanear(); // escanea ')'
                } else {
                    ie.getError(9, token.getContador()); // error si no hay ')'
                }
            }
            case WRITE -> {
                System.out.println("Proposición: WRITE encontrado.");
                token = aLex.scanear(); // Escanea 'WRITE'
                if (token.getTipo().equals(ETerminal.ABRE_PARENTESIS)) {
                    token = aLex.scanear(); // Escanea '('
                } else {
                    ie.getError(10, token.getContador()); // Error si no hay '('
                }
                if (token.getTipo() == ETerminal.CADENA_LITERAL) {
                    token = aLex.scanear(); // Maneja la cadena literal
                } else {
                    expresion(); // Maneja la expresión
                }
                if (token.getTipo().equals(ETerminal.COMA)) {
                    token = aLex.scanear(); // Escanea la coma
                }
                if (token.getTipo().equals(ETerminal.CIERRA_PARENTESIS)) {
                    token = aLex.scanear(); // Escanea ')'
                } else {
                    ie.getError(9, token.getContador()); // Error si no hay ')'
                }
            }
            case WRITELN -> {
                System.out.println("Proposición: WRITELN encontrado.");
                token = aLex.scanear(); // Escanea 'WRITELN'
                if (token.getTipo().equals(ETerminal.ABRE_PARENTESIS)) {
                    token = aLex.scanear(); // Escanea '('
                    if (token.getTipo().equals(ETerminal.CADENA_LITERAL)) {
                        token = aLex.scanear(); // Escanea la cadena literal
                    } else {
                        expresion(); // Maneja la expresión
                    }
                    if (token.getTipo().equals(ETerminal.COMA)) {
                        token = aLex.scanear(); // Escanea la coma
                        if (token.getTipo().equals(ETerminal.CADENA_LITERAL)) {
                            token = aLex.scanear(); // Escanea la cadena literal
                        } else {
                            expresion(); // Maneja la expresión
                        }
                    }
                    if (token.getTipo().equals(ETerminal.CIERRA_PARENTESIS)) {
                        token = aLex.scanear(); // Escanea ')'
                    } else {
                        ie.getError(9, token.getContador()); // Error si no hay ')'
                    }
                }
            }
        }

        System.out.println("Fin de proposición.");
    }

    public void condicion() throws IOException {
        System.out.println("Inicio de condición.");
        if (token.getTipo().equals(ETerminal.ODD)) {
            System.out.println("Condición ODD encontrada.");
            token = aLex.scanear();
            expresion();
        } else {
            expresion();
            if (token.getTipo().equals(ETerminal.IGUAL) ||
                    token.getTipo().equals(ETerminal.MENOR) ||
                    token.getTipo().equals(ETerminal.MENOR_IGUAL) ||
                    token.getTipo().equals(ETerminal.MAYOR) ||
                    token.getTipo().equals(ETerminal.MAYOR_IGUAL) ||
                    token.getTipo().equals(ETerminal.DISTINTO)) {
                token = aLex.scanear();
                expresion();
            } else {
                ie.getError(8, token.getContador());
            }
        }
        System.out.println("Fin de condición.");
    }

    public void expresion() throws IOException {
        System.out.println("Inicio de expresión. Token actual: " + token);
        switch (token.getTipo()) {
            case MAS, MENOS -> {
                System.out.println("Operador unario encontrado: " + token);
                token = aLex.scanear();
            }
        }
        termino();
        while (token.getTipo().equals(ETerminal.MAS) || token.getTipo().equals(ETerminal.MENOS)) {
            System.out.println("Operador encontrado en expresión: " + token);
            token = aLex.scanear();
            termino();
        }
        System.out.println("Fin de expresión.");
    }

    public void termino() throws IOException {
        System.out.println("Inicio de término.");
        factor();
        while (token.getTipo().equals(ETerminal.POR) || token.getTipo().equals(ETerminal.DIVIDIDO)) {
            System.out.println("Operador encontrado en término: " + token);
            token = aLex.scanear();
            factor();
        }
        System.out.println("Fin de término.");
    }

    public void factor() throws IOException {
        System.out.println("Inicio de factor.");
        switch (token.getTipo()) {
            case IDENTIFICADOR, NUMERO -> {
                System.out.println("Factor encontrado: " + token);
                token = aLex.scanear();
            }
            case ABRE_PARENTESIS -> {
                System.out.println("Paréntesis abierto encontrado.");
                token = aLex.scanear();
                expresion();
                if (token.getTipo().equals(ETerminal.CIERRA_PARENTESIS)) {
                    System.out.println("Paréntesis cerrado encontrado.");
                    token = aLex.scanear();
                } else {
                    ie.getError(9, token.getContador());
                }
            }
        }
        System.out.println("Fin de factor.");
    }
}
