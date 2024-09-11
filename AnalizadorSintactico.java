package compilador;

import compilador.tokens.ETerminal;
import compilador.tokens.Token;

import java.io.IOException;

public class AnalizadorSintactico {

    private AnalizadorLexico aLex;
    private Token token;
    private IndicadorErrores ie = new IndicadorErrores();

    public AnalizadorSintactico(AnalizadorLexico aLex) {
        this.aLex = aLex;
    }

    public void programa() throws IOException {
        System.out.println("Inicio del análisis del programa.");
        bloque();

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
        // INICIO DE LA CADENA CONST
        if (token.getTipo().equals(ETerminal.CONST)) {
            System.out.println("Inicio de la sección CONST.");
            token = aLex.scanear();
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
            while (token.getTipo().equals(ETerminal.COMA)) {
                token = aLex.scanear();
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
            }
            if (token.getTipo().equals(ETerminal.PUNTO_Y_COMA)) {
                token = aLex.scanear();
            } else {
                ie.getError(4, token.getContador());
            }
            System.out.println("Fin de la sección CONST.");
        }

        // INICIO DE LA CADENA VAR
        if (token.getTipo().equals(ETerminal.VAR)) {
            System.out.println("Inicio de la sección VAR.");
            token = aLex.scanear();

            if (token.getTipo().equals(ETerminal.IDENTIFICADOR)) {
                token = aLex.scanear();
            } else {
                ie.getError(1, token.getContador());
            }
            while (token.getTipo().equals(ETerminal.COMA)) {
                token = aLex.scanear();
                if (token.getTipo().equals(ETerminal.IDENTIFICADOR)) {
                    token = aLex.scanear();
                } else {
                    ie.getError(1, token.getContador());
                }
            }
            if (token.getTipo().equals(ETerminal.PUNTO_Y_COMA)) {
                token = aLex.scanear();
            } else {
                ie.getError(4, token.getContador());
            }
            System.out.println("Fin de la sección VAR.");
        }

        // INICIO DE LA CADENA PROCEDURE
        if (token.getTipo().equals(ETerminal.PROCEDURE)) {
            System.out.println("Inicio de la sección PROCEDURE.");
            token = aLex.scanear();
            if (token.getTipo().equals(ETerminal.IDENTIFICADOR)) {
                token = aLex.scanear();
            } else {
                ie.getError(1, token.getContador());
            }
            if (token.getTipo().equals(ETerminal.PUNTO_Y_COMA)) {
                bloque();
            }
            if (token.getTipo().equals(ETerminal.PUNTO_Y_COMA)) {
                token = aLex.scanear();
            } else {
                ie.getError(4, token.getContador());
            }
            System.out.println("Fin de la sección PROCEDURE.");
        }

        System.out.println("Inicio de proposiciones dentro del bloque.");
        proposicion();
        System.out.println("Fin del bloque.");
    }

    // INICIA EL METODO PROPOSICION
    public void proposicion() throws IOException {
        System.out.println("Inicio de proposición.");
        switch (token.getTipo()) {
            case IDENTIFICADOR -> {
                System.out.println("Proposición: IDENTIFICADOR encontrado.");
                token = aLex.scanear();
                if (token.getTipo().equals(ETerminal.ASIGNACION)) {
                    System.out.println("Asignación encontrada.");
                    expresion();
                } else {
                    ie.getError(1, token.getContador());
                }
            }
            case CALL -> {
                System.out.println("Proposición: CALL encontrado.");
                token = aLex.scanear();
                if (token.getTipo().equals(ETerminal.IDENTIFICADOR)) {
                    token = aLex.scanear();
                } else {
                    ie.getError(1, token.getContador());
                }
            }
            case BEGIN -> {
                System.out.println("Proposición: BEGIN encontrado.");
                proposicion();
                token = aLex.scanear();
                while (token.getTipo().equals(ETerminal.PUNTO_Y_COMA)) {
                    proposicion();
                    token = aLex.scanear();
                }
                if (token.getTipo().equals(ETerminal.END)) {
                    token = aLex.scanear();
                } else {
                    ie.getError(7, token.getContador());
                }
            }
            case IF -> {
                System.out.println("Proposición: IF encontrado.");
                condicion();
                token = aLex.scanear();
                if (token.getTipo().equals(ETerminal.THEN)) {
                    proposicion();
                }
            }
            case WHILE -> {
                System.out.println("Proposición: WHILE encontrado.");
                condicion();
                token = aLex.scanear();
                if (token.getTipo().equals(ETerminal.DO)) {
                    proposicion();
                } else {
                    ie.getError(5, token.getContador());
                }
            }
            case READLN -> {
                System.out.println("Proposición: READLN encontrado.");
                token = aLex.scanear();
                if (token.getTipo().equals(ETerminal.ABRE_PARENTESIS)) {
                    token = aLex.scanear();
                } else {
                    ie.getError(10, token.getContador());
                }
                if (token.getTipo().equals(ETerminal.IDENTIFICADOR)) {
                    token = aLex.scanear();
                } else {
                    ie.getError(1, token.getContador());
                }
                while (token.getTipo().equals(ETerminal.COMA)) {
                    token = aLex.scanear();
                    if (token.getTipo().equals(ETerminal.IDENTIFICADOR)) {
                        token = aLex.scanear();
                    } else {
                        ie.getError(1, token.getContador());
                    }
                }
                if (token.getTipo().equals(ETerminal.CIERRA_PARENTESIS)) {
                    token = aLex.scanear();
                } else {
                    ie.getError(9, token.getContador());
                }
            }
            case WRITE, WRITELN -> {
                System.out.println("Proposición: " + token.getTipo() + " encontrado.");
                token = aLex.scanear();
                if (token.getTipo().equals(ETerminal.ABRE_PARENTESIS)) {
                    token = aLex.scanear();
                } else {
                    ie.getError(10, token.getContador());
                }
                if (token.getTipo().equals(ETerminal.CADENA_LITERAL)) {
                    token = aLex.scanear();
                } else {
                    expresion();
                }
                while (token.getTipo().equals(ETerminal.COMA)) {
                    token = aLex.scanear();
                    if (token.getTipo().equals(ETerminal.CADENA_LITERAL)) {
                        token = aLex.scanear();
                    } else {
                        expresion();
                    }
                }
                if (token.getTipo().equals(ETerminal.CIERRA_PARENTESIS)) {
                    token = aLex.scanear();
                } else {
                    ie.getError(9, token.getContador());
                }
            }
        }
        System.out.println("Fin de proposición.");
    }

    public void condicion() throws IOException {
        System.out.println("Inicio de condición.");
        if (token.getTipo().equals(ETerminal.ODD)) {
            expresion();
        } else {
            expresion();
            token = aLex.scanear();
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
        System.out.println("Inicio de expresión.");
        if (token.getTipo().equals(ETerminal.MAS) || token.getTipo().equals(ETerminal.MENOS)) {
            token = aLex.scanear();
        }
        termino();
        while (token.getTipo().equals(ETerminal.MAS) || token.getTipo().equals(ETerminal.MENOS)) {
            token = aLex.scanear();
            termino();
        }
        System.out.println("Fin de expresión.");
    }

    public void termino() throws IOException {
        System.out.println("Inicio de término.");
        factor();
        while (token.getTipo().equals(ETerminal.POR) || token.getTipo().equals(ETerminal.DIVIDIDO)) {
            token = aLex.scanear();
            factor();
        }
        System.out.println("Fin de término.");
    }

    public void factor() throws IOException {
        System.out.println("Inicio de factor.");
        switch (token.getTipo()) {
            case IDENTIFICADOR, NUMERO -> token = aLex.scanear();
            case ABRE_PARENTESIS -> {
                token = aLex.scanear();
                expresion();
                if (token.getTipo().equals(ETerminal.CIERRA_PARENTESIS)) {
                    token = aLex.scanear();
                } else {
                    ie.getError(9, token.getContador());
                }
            }
        }
        System.out.println("Fin de factor.");
    }
}
