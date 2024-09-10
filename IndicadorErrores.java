package compilador;


public class IndicadorErrores {




    public void getError(int num, int contador) {
        switch (num){
            case 1-> System.out.println("se esperaba un IDENTIFICADOR \tLinea: "+contador);
            case 2-> System.out.println("se esperaba un IGUAL \tLinea: "+contador);
            case 3-> System.out.printf("Se esperaba un NUMERO \tLinea: "+contador);
            case 4 -> System.out.println("se esperaba un PUNTO Y COMA \tLinea: "+contador);
            case 5 -> System.out.println("se esperaba un DO \tLinea: "+contador);
            case 6 -> System.out.println("se esperaba una ASIGNACION \tLinea: "+contador);
            case 7 -> System.out.println("se esperaba un END \tLinea: "+contador);
            case 8 -> System.out.println("se esperaba un operador aritmetico o comparativo \tLinea: "+contador);
            case 9 -> System.out.println("Se esperaba un cierre de parentesis \tLinea: "+contador);
            case 10 -> System.out.println("Se esperaba una apertura de parentesis \tLinea: "+contador);


        }
    }
}
