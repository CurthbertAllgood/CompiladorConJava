package compilador;

import compilador.tokens.Token;
import compilador.tokens.ETerminal;

import java.io.IOException;
import java.sql.SQLOutput;

public class AnalizadorSintactico {

    private AnalizadorLexico aLex;
    private Token token;
    private IndicadorErrores ie= new IndicadorErrores();
    public AnalizadorSintactico(AnalizadorLexico aLex) {
        this.aLex = aLex;
    }


    public void programa() throws IOException {
        token = aLex.scanear();
        bloque();

        if (token != null && token.getTipo().equals(ETerminal.PUNTO)) {
            System.out.println("Programa terminado correctamente.");
        } else {
            throw new IllegalStateException("Error: Se esperaba un punto al final del programa.");
        }
    }




    public void bloque() throws IOException {

        //INICIO DE LA CADENA CONST
        if (token.getTipo().equals(ETerminal.CONST)) {
            token = aLex.scanear();
            if(token.getTipo().equals(ETerminal.IDENTIFICADOR)){
                token = aLex.scanear();
            }
            else{
                ie.getError(1, token.getContador());
            }
            if(token.getTipo().equals(ETerminal.IGUAL)){
                token = aLex.scanear();
            }
            else{
                ie.getError(2, token.getContador());
            }
            if(token.getTipo().equals(ETerminal.NUMERO)){
                token = aLex.scanear();
            }
            else{
                ie.getError(3, token.getContador());
            }
            while(token.getTipo().equals(ETerminal.COMA)){
                token = aLex.scanear();
                if(token.getTipo().equals(ETerminal.IDENTIFICADOR)){
                    token = aLex.scanear();
                }
                else{
                    ie.getError(1, token.getContador());
                }
                if(token.getTipo().equals(ETerminal.IGUAL)){
                    token = aLex.scanear();
                }
                else{
                    ie.getError(2,token.getContador());
                }
                if(token.getTipo().equals(ETerminal.NUMERO)){
                    token = aLex.scanear();
                }
                else{
                    ie.getError(3, token.getContador());
                }

            }
            if(token.getTipo().equals(ETerminal.PUNTO_Y_COMA)){
                token = aLex.scanear();
            }
            else{
                ie.getError(4, token.getContador());
            }
        }
        //INICIO DE LA CADENA VAR
        if(token.getTipo().equals(ETerminal.VAR)){
            token = aLex.scanear();
            
            if(token.getTipo().equals(ETerminal.IDENTIFICADOR)){
                token = aLex.scanear();
            }
            else{
                ie.getError(1,token.getContador());
            }
            while(token.getTipo().equals(ETerminal.COMA)){
                token = aLex.scanear();
                if(token.getTipo().equals(ETerminal.IDENTIFICADOR)){
                    token = aLex.scanear();
                }
                else{
                    ie.getError(1,token.getContador());
                }
            }
            //ERROR, toma la Y como tipo de dato no
            System.out.println("El valor es: "+token.getValor()+" "+token.getTipo());
            if(token.getTipo().equals(ETerminal.PUNTO_Y_COMA)){
                token = aLex.scanear();
            }
            else{
                ie.getError(4,token.getContador());
            }
        }
        if(token.getTipo().equals(ETerminal.PROCEDURE)){
            token = aLex.scanear();
            if(token.getTipo().equals(ETerminal.IDENTIFICADOR)){
                token = aLex.scanear();
            }
            else{
                ie.getError(1, token.getContador());
            }
            if(token.getTipo().equals(ETerminal.PUNTO_Y_COMA)){
                bloque();
            }
            if(token.getTipo().equals(ETerminal.PUNTO_Y_COMA)){
                token = aLex.scanear();
            }
            else{
                ie.getError(4, token.getContador());
            }
        }
        proposicion();
    }


        //INICIA EL METODO PROPOSICION
    public void proposicion() throws IOException {
        switch (token.getTipo()) {
            case IDENTIFICADOR -> {
                token = aLex.scanear();
                if (token.getTipo().equals(ETerminal.ASIGNACION)) {
                    expresion();
                } else {
                    ie.getError(1, token.getContador());
                }
            }
            case CALL -> {
                token = aLex.scanear();
                if (token.getTipo().equals(ETerminal.IDENTIFICADOR)) {
                    token = aLex.scanear();
                } else {
                    ie.getError(1, token.getContador());
                }

            }
            case BEGIN -> {
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
            case IF-> {
                condicion();
                token = aLex.scanear();
                if (token.getTipo().equals(ETerminal.THEN)) {
                    proposicion();
                }
            }
            case WHILE-> {
                condicion();
                token = aLex.scanear();
                if (token.getTipo().equals(ETerminal.DO)){
                    proposicion();
                }
                else{
                    ie.getError(5, token.getContador());
                }
            }
            case READLN->{
                if (token.getTipo().equals(ETerminal.READLN)) {
                    token = aLex.scanear();
                    if(token.getTipo().equals(ETerminal.ABRE_PARENTESIS)){
                        token = aLex.scanear();
                    }
                    else {
                        ie.getError(10, token.getContador());
                    }
                    if(token.getTipo().equals(ETerminal.IDENTIFICADOR)) {
                        token = aLex.scanear();
                    }
                    else{
                        ie.getError(1, token.getContador());
                    }
                    while(token.getTipo().equals(ETerminal.COMA)){
                        token = aLex.scanear();
                        if(token.getTipo().equals(ETerminal.IDENTIFICADOR)){
                            token = aLex.scanear();
                        }
                        else{
                            ie.getError(1, token.getContador());
                        }
                    }
                    if(token.getTipo().equals(ETerminal.CIERRA_PARENTESIS)){
                        token = aLex.scanear();
                    }
                    else{
                        ie.getError(9, token.getContador());
                    }
                }

            }
            case WRITE, WRITELN ->{
                token = aLex.scanear();
                if (token.getTipo().equals(ETerminal.ABRE_PARENTESIS)) {
                    token = aLex.scanear();
                }
                else {
                    ie.getError(10, token.getContador());
                }
                if (token.getTipo().equals(ETerminal.CADENA_LITERAL)) {
                    token = aLex.scanear();
                }
                else{
                    expresion();
                }
                while(token.getTipo().equals(ETerminal.COMA)){
                    token = aLex.scanear();
                    if(token.getTipo().equals(ETerminal.CADENA_LITERAL)){
                        token = aLex.scanear();
                    }
                    else{
                        expresion();
                    }
                }
                if(token.getTipo().equals(ETerminal.CIERRA_PARENTESIS)){
                    token = aLex.scanear();
                }
                else{
                    ie.getError(9, token.getContador());
                }
            }
        }

    }


    public void condicion() throws IOException {
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
            }
            else{
                ie.getError(8, token.getContador());
            }
        }
    }

    public void expresion() throws IOException {
        if (    token.getTipo().equals(ETerminal.MAS) || token.getTipo().equals(ETerminal.MENOS)) {
            token = aLex.scanear();
        }
        termino();
        while (token.getTipo().equals(ETerminal.MAS) || token.getTipo().equals(ETerminal.MENOS)) {
            token = aLex.scanear();
            termino();
        }
    }

    public void termino() throws IOException {
        factor();
        while (token.getTipo().equals(ETerminal.POR) || token.getTipo().equals(ETerminal.DIVIDIDO)) {
            token = aLex.scanear();
            factor();
        }
    }

    public void factor() throws IOException {
        switch (token.getTipo()) {
            case IDENTIFICADOR, NUMERO -> {
                token = aLex.scanear();
            }
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
