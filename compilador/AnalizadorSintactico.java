package compilador.compilador;

import compilador.compilador.tokens.ETerminal;
import compilador.compilador.tokens.Token;

public class AnalizadorSintactico {

    private final AnalizadorLexico aLex;
    private final AnalizadorSemantico aSem = new AnalizadorSemantico();
    private Token token;
    private final IndicadorErrores ie = new IndicadorErrores();
    private int base = 0;

    public AnalizadorSintactico(AnalizadorLexico aLex) {
        this.aLex = aLex;
    }

    public void programa() throws Exception {
        token = aLex.scanear();
        bloque(base);
        if (token.getTipo().equals(ETerminal.PUNTO)) {
            aLex.scanear();
            System.out.println("Programa terminado correctamente.");
        } else {
            throw new IllegalStateException("Error: Se esperaba un punto al final del programa.");
        }
    }

    public void bloque(int base) throws Exception {
        int desplazamiento = 0;

        // Sección CONST
        if (token.getTipo().equals(ETerminal.CONST)) {
            do {
                token = aLex.scanear();
                if (token.getTipo().equals(ETerminal.IDENTIFICADOR)) {
                    aSem.agregarToken(token, base);
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
        }

        // Sección VAR
        if (token.getTipo().equals(ETerminal.VAR)) {
            do {
                token = aLex.scanear();
                if (token.getTipo().equals(ETerminal.IDENTIFICADOR)) {
                    aSem.agregarToken(token, base);
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
        }

        // Sección PROCEDURE
        while (token.getTipo().equals(ETerminal.PROCEDURE)) {
            token = aLex.scanear();
            if (token.getTipo().equals(ETerminal.IDENTIFICADOR)) {
                aSem.agregarToken(token, base);
                token = aLex.scanear();
            } else {
                ie.getError(1, token.getContador());
            }
            if (token.getTipo().equals(ETerminal.PUNTO_Y_COMA)) {
                token = aLex.scanear();
            } else {
                ie.getError(4, token.getContador());
            }

            desplazamiento++;
            bloque(base + desplazamiento);

            if (token.getTipo().equals(ETerminal.PUNTO_Y_COMA)) {
                token = aLex.scanear();
            } else {
                ie.getError(4, token.getContador());
            }
        }

        proposicion();
    }

    public void proposicion() throws Exception {

        switch (token.getTipo()) {
            case IDENTIFICADOR -> {
                aSem.verificarVariableDeclarada(token.getValor());
                token = aLex.scanear();
                if (token.getTipo().equals(ETerminal.ASIGNACION)) {
                    token = aLex.scanear();
                    expresion();
                } else {
                    ie.getError(6, token.getContador());
                }
            }
            case CALL -> {
                token = aLex.scanear();
                if (token.getTipo().equals(ETerminal.IDENTIFICADOR)) {
                    aSem.verificarVariableDeclarada(token.getValor());
                    token = aLex.scanear();
                } else {
                    ie.getError(1, token.getContador());
                }
            }
            case BEGIN -> {
                token = aLex.scanear();
                proposicion();
                while (token.getTipo().equals(ETerminal.PUNTO_Y_COMA)) {
                    token = aLex.scanear();
                    proposicion();
                }
                if (token.getTipo().equals(ETerminal.END)) {
                    token = aLex.scanear();
                } else {
                    ie.getError(7, token.getContador());
                }
            }
            case IF -> {
                token = aLex.scanear();
                condicion();
                if (token.getTipo().equals(ETerminal.THEN)) {
                    token = aLex.scanear();
                    proposicion();
                } else {
                    ie.getError(5, token.getContador());
                }
            }
            case WHILE -> {
                token = aLex.scanear();
                condicion();
                if (token.getTipo().equals(ETerminal.DO)) {
                    token = aLex.scanear();
                    proposicion();
                } else {
                    ie.getError(5, token.getContador());
                }
            }
            case READLN -> {
                token = aLex.scanear();
                if (token.getTipo().equals(ETerminal.ABRE_PARENTESIS)) {
                    token = aLex.scanear();
                    if (token.getTipo().equals(ETerminal.IDENTIFICADOR)) {
                        aSem.verificarVariableDeclarada(token.getValor());
                        token = aLex.scanear();
                        while (token.getTipo().equals(ETerminal.COMA)) {
                            token = aLex.scanear();
                            if (token.getTipo().equals(ETerminal.IDENTIFICADOR)) {
                                aSem.verificarVariableDeclarada(token.getValor());
                                token = aLex.scanear();
                            } else {
                                ie.getError(1, token.getContador());
                            }
                        }
                    } else {
                        ie.getError(1, token.getContador());
                    }
                    if (token.getTipo().equals(ETerminal.CIERRA_PARENTESIS)) {
                        token = aLex.scanear();
                    } else {
                        ie.getError(9, token.getContador());
                    }
                } else {
                    ie.getError(10, token.getContador());
                }
            }
            case WRITELN, WRITE -> { // Sección WRITELN y WRITE ajustada
                boolean nuevaLinea = token.getTipo().equals(ETerminal.WRITELN);
                token = aLex.scanear();

                System.out.println("Procesando WRITELN/WRITE, esperando '('. Token actual: " + token);

                if (token.getTipo().equals(ETerminal.ABRE_PARENTESIS)) {
                    token = aLex.scanear();

                    System.out.println("Paréntesis abierto encontrado. Procesando argumentos.");

                    // Procesar primer argumento, que puede ser cadena o expresión
                    if (token.getTipo().equals(ETerminal.CADENA_LITERAL)) {
                        System.out.println("Cadena literal encontrada: " + token);
                        token = aLex.scanear();
                    } else {
                        expresion();
                    }

                    // Procesar argumentos adicionales separados por coma
                    while (token.getTipo().equals(ETerminal.COMA)) {
                        token = aLex.scanear();
                        if (token.getTipo().equals(ETerminal.CADENA_LITERAL)) {
                            System.out.println("Cadena literal encontrada después de coma: " + token);
                            token = aLex.scanear();
                        } else {
                            expresion();
                        }
                    }

                    if (token.getTipo().equals(ETerminal.CIERRA_PARENTESIS)) {
                        System.out.println("Paréntesis cerrado encontrado.");
                        token = aLex.scanear();
                    } else {
                        System.out.println("Error: Se esperaba ')' al final de WRITELN/WRITE.");
                        ie.getError(9, token.getContador());
                    }
                } else if (!token.getTipo().equals(ETerminal.END) && !token.getTipo().equals(ETerminal.PUNTO_Y_COMA)) {
                    // Se esperaba un paréntesis abierto, pero también validamos si el próximo token es END o PUNTO_Y_COMA
                    System.out.println("Error: Se esperaba '(' después de WRITELN/WRITE.");
                    ie.getError(10, token.getContador());
                }
            }
        }
    }


    public void condicion() throws Exception {
        if (token.getTipo().equals(ETerminal.ODD)) {
            token = aLex.scanear();
            expresion();
        } else {
            expresion();
            if (token.getTipo().equals(ETerminal.IGUAL) || token.getTipo().equals(ETerminal.MENOR) ||
                    token.getTipo().equals(ETerminal.MENOR_IGUAL) || token.getTipo().equals(ETerminal.MAYOR) ||
                    token.getTipo().equals(ETerminal.MAYOR_IGUAL) || token.getTipo().equals(ETerminal.DISTINTO)) {
                token = aLex.scanear();
                expresion();
            } else {
                ie.getError(8, token.getContador());
            }
        }
    }

    public void expresion() throws Exception {
        if (token.getTipo().equals(ETerminal.MAS) || token.getTipo().equals(ETerminal.MENOS)) {
            token = aLex.scanear();
        }
        termino();
        while (token.getTipo().equals(ETerminal.MAS) || token.getTipo().equals(ETerminal.MENOS)) {
            token = aLex.scanear();
            termino();
        }
    }

    public void termino() throws Exception {
        factor();
        while (token.getTipo().equals(ETerminal.POR) || token.getTipo().equals(ETerminal.DIVIDIDO)) {
            token = aLex.scanear();
            factor();
        }
    }

    public void factor() throws Exception {
        switch (token.getTipo()) {
            case IDENTIFICADOR -> {
                aSem.verificarVariableDeclarada(token.getValor());
                token = aLex.scanear();
            }
            case NUMERO -> token = aLex.scanear();
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
    }
}
