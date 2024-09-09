package compilador;

import compilador.tokens.Token;
import compilador.tokens.ETerminal;

import java.io.IOException;

public class AnalizadorSintactico {

    private AnalizadorLexico aLex;
    private Token token;

    public AnalizadorSintactico(AnalizadorLexico aLex) {
        this.aLex = aLex;
    }

    private void avanzar() throws IOException {
        token = aLex.scanear();
        if (token != null) {
            System.out.println("Token leído: " + token.getTipo() + " con valor: " + token.getValor());
        }
    }

    public void programa() throws IOException {
        avanzar(); // Avanzar al primer token
        bloque();  // Analizar el bloque del programa

        // Después de analizar el bloque, esperamos un punto para terminar el programa
        if (token != null && token.getTipo().equals(ETerminal.PUNTO)) {
            System.out.println("Programa terminado correctamente.");
        } else {
            throw new IllegalStateException("Error: Se esperaba un punto al final del programa.");
        }
    }

    public void bloque() throws IOException {
        // Manejar `CONST`
        if (token.getTipo().equals(ETerminal.CONST)) {
            avanzar();
            do {
                if (token.getTipo().equals(ETerminal.IDENTIFICADOR)) {
                    avanzar();
                    if (token.getTipo().equals(ETerminal.IGUAL)) {
                        avanzar();
                        if (token.getTipo().equals(ETerminal.NUMERO)) {
                            avanzar();
                        } else {
                            throw new IllegalStateException("Error: Se esperaba un número después de '='.");
                        }
                    } else {
                        throw new IllegalStateException("Error: Se esperaba un '=' después del identificador.");
                    }
                }
            } while (token.getTipo().equals(ETerminal.COMA));
            if (token.getTipo().equals(ETerminal.PUNTO_Y_COMA)) {
                avanzar();
            } else {
                throw new IllegalStateException("Error: Se esperaba un ';' después de la declaración.");
            }
        }

        // Manejar `VAR`
        if (token.getTipo().equals(ETerminal.VAR)) {
            avanzar();
            do {
                if (token.getTipo().equals(ETerminal.IDENTIFICADOR)) {
                    avanzar();
                } else {
                    throw new IllegalStateException("Error: Se esperaba un identificador después de 'VAR'.");
                }
            } while (token.getTipo().equals(ETerminal.COMA));
            if (token.getTipo().equals(ETerminal.PUNTO_Y_COMA)) {
                avanzar();
            } else {
                throw new IllegalStateException("Error: Se esperaba un ';' después de la declaración.");
            }
        }

        // Manejar `PROCEDURE`
        while (token.getTipo().equals(ETerminal.PROCEDURE)) {
            avanzar();
            if (token.getTipo().equals(ETerminal.IDENTIFICADOR)) {
                avanzar();
                if (token.getTipo().equals(ETerminal.PUNTO_Y_COMA)) {
                    avanzar();
                    bloque(); // Analizar el bloque del procedimiento
                    if (token.getTipo().equals(ETerminal.PUNTO_Y_COMA)) {
                        avanzar();
                    } else {
                        throw new IllegalStateException("Error: Se esperaba un ';' después del bloque del procedimiento.");
                    }
                } else {
                    throw new IllegalStateException("Error: Se esperaba un ';' después del identificador del procedimiento.");
                }
            } else {
                throw new IllegalStateException("Error: Se esperaba un identificador después de 'PROCEDURE'.");
            }
        }

        // Llamar a proposicion() después de manejar CONST, VAR y PROCEDURE
        proposicion();
    }

    public void proposicion() throws IOException {
        if (token.getTipo().equals(ETerminal.IDENTIFICADOR)) {
            avanzar();
            if (token.getTipo().equals(ETerminal.ASIGNACION)) {
                avanzar();
                expresion();
            } else {
                throw new IllegalStateException("Error: Se esperaba ':=' después del identificador.");
            }
        } else if (token.getTipo().equals(ETerminal.CALL)) {
            avanzar();
            if (token.getTipo().equals(ETerminal.IDENTIFICADOR)) {
                avanzar();
            } else {
                throw new IllegalStateException("Error: Se esperaba un identificador después de 'CALL'.");
            }
        } else if (token.getTipo().equals(ETerminal.BEGIN)) {
            do {
                avanzar();
                proposicion();
            } while (token.getTipo().equals(ETerminal.PUNTO_Y_COMA));
            if (token.getTipo().equals(ETerminal.END)) {
                avanzar();
            } else {
                throw new IllegalStateException("Error: Se esperaba 'END' para cerrar el bloque BEGIN.");
            }
        } else if (token.getTipo().equals(ETerminal.IF)) {
            avanzar();
            condicion();
            if (token.getTipo().equals(ETerminal.THEN)) {
                avanzar();
                proposicion();
            } else {
                throw new IllegalStateException("Error: Se esperaba 'THEN' después de la condición 'IF'.");
            }
        } else if (token.getTipo().equals(ETerminal.WHILE)) {
            avanzar();
            condicion();
            if (token.getTipo().equals(ETerminal.DO)) {
                avanzar();
                proposicion();
            } else {
                throw new IllegalStateException("Error: Se esperaba 'DO' después de la condición 'WHILE'.");
            }
        }
    }

    public void condicion() throws IOException {
        if (token.getTipo().equals(ETerminal.ODD)) {
            avanzar();
            expresion();
        } else {
            expresion();
            if (token.getTipo().equals(ETerminal.IGUAL) ||
                    token.getTipo().equals(ETerminal.MENOR) ||
                    token.getTipo().equals(ETerminal.MENOR_IGUAL) ||
                    token.getTipo().equals(ETerminal.MAYOR) ||
                    token.getTipo().equals(ETerminal.MAYOR_IGUAL) ||
                    token.getTipo().equals(ETerminal.DISTINTO)) {
                avanzar();
                expresion();
            }
        }
    }

    public void expresion() throws IOException {
        if (token.getTipo().equals(ETerminal.MAS) || token.getTipo().equals(ETerminal.MENOS)) {
            avanzar();
        }
        termino();
        while (token.getTipo().equals(ETerminal.MAS) || token.getTipo().equals(ETerminal.MENOS)) {
            avanzar();
            termino();
        }
    }

    public void termino() throws IOException {
        factor();
        while (token.getTipo().equals(ETerminal.POR) || token.getTipo().equals(ETerminal.DIVIDIDO)) {
            avanzar();
            factor();
        }
    }

    public void factor() throws IOException {
        if (token.getTipo().equals(ETerminal.IDENTIFICADOR)) {
            avanzar();
        } else if (token.getTipo().equals(ETerminal.NUMERO)) {
            avanzar();
        } else if (token.getTipo().equals(ETerminal.ABRE_PARENTESIS)) {
            avanzar();
            expresion();
            if (token.getTipo().equals(ETerminal.CIERRA_PARENTESIS)) {
                avanzar();
            }
        }
    }

    public void readln() throws IOException {
        if (token.getTipo().equals(ETerminal.READLN)) {
            avanzar();
            if (token.getTipo().equals(ETerminal.ABRE_PARENTESIS)) {
                avanzar();
                if (token.getTipo().equals(ETerminal.IDENTIFICADOR)) {
                    avanzar();
                    while (token.getTipo().equals(ETerminal.COMA)) {
                        avanzar();
                        if (token.getTipo().equals(ETerminal.IDENTIFICADOR)) {
                            avanzar();
                        }
                    }
                    if (token.getTipo().equals(ETerminal.CIERRA_PARENTESIS)) {
                        avanzar();
                    }
                }
            }
        }
    }

    public void writeln() throws IOException {
        if (token.getTipo().equals(ETerminal.WRITELN) || token.getTipo().equals(ETerminal.WRITE)) {
            avanzar();
            if (token.getTipo().equals(ETerminal.ABRE_PARENTESIS)) {
                avanzar();
                if (token.getTipo().equals(ETerminal.CADENA_LITERAL) || token.getTipo().equals(ETerminal.IDENTIFICADOR)) {
                    avanzar();
                    while (token.getTipo().equals(ETerminal.COMA)) {
                        avanzar();
                        expresion();
                    }
                    if (token.getTipo().equals(ETerminal.CIERRA_PARENTESIS)) {
                        avanzar();
                    }
                }
            }
        }
    }
}
