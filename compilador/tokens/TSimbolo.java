package compilador.compilador.tokens;

public class TSimbolo implements Token {

    private String simbolo;
    private int contador;

    private ETerminal tipoDato;
	public TSimbolo(String valor, int contador) {
		setValor(valor);
        setContador(contador);
        setTipoDato(valor);
	}



    public String getValor() {
        return simbolo;
    }

    public ETerminal getTipo() {
		return this.tipoDato;
	}

    public void setValor(String simbolo) {
        if(simbolo!=" ") {
            this.simbolo = simbolo;
        }else{
            System.out.println("el dato ingresado es incorrecto");

        }

    }

    @Override
    public int getScope() {
        return 0;
    }

    @Override
    public void setScope(int scope) {

    }

    @Override
    public int getContador() {
        return contador;
    }

    @Override
    public void setContador(int contador){
        if(contador!=0){
            this.contador= contador;
        }
    }

    @Override
    public void setTipoDato(String valor){

        switch (valor){
            case "-"-> this.tipoDato = ETerminal.MENOS;

            case "+"-> this.tipoDato = ETerminal.MAS;

            case "*"-> this.tipoDato = ETerminal.POR;

            case "/"-> this.tipoDato = ETerminal.DIVIDIDO;

            case ";"-> this.tipoDato = ETerminal.PUNTO_Y_COMA;

            case ","-> this.tipoDato = ETerminal.COMA;

            case "="-> this.tipoDato = ETerminal.IGUAL;

            case "<"-> this.tipoDato = ETerminal.MENOR;

            case "."-> this.tipoDato = ETerminal.PUNTO;

            case "<="-> this.tipoDato = ETerminal.MENOR_IGUAL;

            case ">="-> this.tipoDato = ETerminal.MAYOR_IGUAL;

            case ">"-> this.tipoDato = ETerminal.MAYOR;

            case "<>"-> this.tipoDato = ETerminal.DISTINTO;

            case "("-> this.tipoDato = ETerminal.ABRE_PARENTESIS;

            case ")"-> this.tipoDato = ETerminal.CIERRA_PARENTESIS;

            case "'"-> this.tipoDato= ETerminal.CADENA_LITERAL;

            case ":="-> this.tipoDato = ETerminal.ASIGNACION;

        }


    }

    public String toString() {
        return "TokenVariable{" +
                "variable='" + simbolo + '\'' +
                ", contador=" + contador +
                ", tipoDato=" + tipoDato +
                '}';
    }

}
