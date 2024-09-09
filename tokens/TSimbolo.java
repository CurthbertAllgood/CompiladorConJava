package compilador.tokens;

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
            case "-":
                this.tipoDato = ETerminal.MENOS;
                break;
            case "+":
                this.tipoDato = ETerminal.MAS;
                break;
            case "*":
                this.tipoDato = ETerminal.POR;
                break;
            case "/":
                this.tipoDato = ETerminal.DIVIDIDO;
                break;

            case ";":
                this.tipoDato = ETerminal.PUNTO_Y_COMA;
                break;
            case ",":
                this.tipoDato = ETerminal.COMA;
                break;
            case "=":
                this.tipoDato = ETerminal.IGUAL;
                break;
            case "<":
                this.tipoDato = ETerminal.MENOR;
                break;
            case ".":
                this.tipoDato = ETerminal.PUNTO;
                break;
            case "<=":
                this.tipoDato = ETerminal.MENOR_IGUAL;
                break;
            case ">=":
                this.tipoDato = ETerminal.MAYOR_IGUAL;
                break;
            case ">":
                this.tipoDato = ETerminal.MAYOR;
                break;
            case "<>":
                this.tipoDato = ETerminal.DISTINTO;
                break;
            case "(":
                this.tipoDato = ETerminal.ABRE_PARENTESIS;
                break;
            case ")":
                this.tipoDato = ETerminal.CIERRA_PARENTESIS;
                break;
            case "'":
                this.tipoDato= ETerminal.CADENA_LITERAL;
                break;
            case ":=":
                this.tipoDato = ETerminal.ASIGNACION;
                break;

            default:
                this.tipoDato=null;
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
