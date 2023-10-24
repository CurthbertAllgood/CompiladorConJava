package compilador;

import java.io.IOException;



public class AnalizadorSintactico {

    private AnalizadorLexico aLex;

    private AnalizadorSemantico aSem;

    private IndicadorErrores indicadorErrores;


    public AnalizadorSintactico(AnalizadorLexico aLex, AnalizadorSemantico aSem, IndicadorErrores indicadorErrores) {
        this.aLex = aLex;
        this.aSem = aSem;
        this.indicadorErrores = indicadorErrores;
    }

    public void parser() throws IOException {
        programa();
    }

    public void programa() throws IOException{

        Token t = tipoDeDato();
        if(t!=null){
                bloque(0,t);
        }else if(t.getTipo()==Terminal.PUNTO){
                t= tipoDeDato();
        }else{
            indicadorErrores.getError();
        }
    }

    public void bloque(int dato, Token t) throws IOException {
        int desplazamiento=0;
        String nombre=null;
        switch(t.getTipo()){
            case CONST:{
                while(t.getTipo()!=Terminal.PUNTO_Y_COMA){
                    t= tipoDeDato();
                    if(t.getTipo()==Terminal.IDENTIFICADOR){
                            nombre=t.getValor();
                    }
                    t=tipoDeDato();
                    if(t.getTipo()==Terminal.IGUAL) {
                        t = tipoDeDato();

                        if (t.getTipo() == Terminal.NUMERO) {
                            aSem.guardarEnTabla(dato + desplazamiento, nombre, Terminal.CONST, t.getValor());
                        }
                    t=tipoDeDato();
                        if(t.getTipo()!=Terminal.PUNTO_Y_COMA || t.getTipo()!= Terminal.COMA){
                            indicadorErrores.getError();
                        }
                    }else {
                        indicadorErrores.getError();
                    }
                }
            }
        }

    }


    private Token tipoDeDato() throws IOException {
       Token tipoDato= aLex.scanner();
       return tipoDato;

    }
}
