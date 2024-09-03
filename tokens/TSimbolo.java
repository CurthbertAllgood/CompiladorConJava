package compilador.tokens;

public class TSimbolo implements Token {

    private String simbolo;
    private int contador;

    private Terminal tipoDato;
	public TSimbolo(String valor, int contador) {
		setValor(valor);
        setContador(contador);
        setTipoDato(valor);
	}



    public String getValor() {
        return simbolo;
    }

    public Terminal getTipo() {
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
                this.tipoDato =Terminal.MENOS;
                break;
            case "+":
                this.tipoDato =Terminal.MAS;
                break;
            case "*":
                this.tipoDato =Terminal.POR;
                break;
            case "/":
                this.tipoDato =Terminal.DIVIDIDO;
                break;

            case ";":
                this.tipoDato =Terminal.PUNTO_Y_COMA;
                break;
            case ",":
                this.tipoDato =Terminal.COMA;
                break;
            case "=":
                this.tipoDato =Terminal.IGUAL;
                break;
            case "<":
                this.tipoDato =Terminal.MENOR;
                break;
            case ".":
                this.tipoDato =Terminal.PUNTO;
                break;
            case "<=":
                this.tipoDato =Terminal.MENOR_IGUAL;
                break;
            case ">=":
                this.tipoDato =Terminal.MAYOR_IGUAL;
                break;
            case ">":
                this.tipoDato =Terminal.MAYOR;
                break;
            case "<>":
                this.tipoDato =Terminal.DISTINTO;
                break;
            case "(":
                this.tipoDato =Terminal.ABRE_PARENTESIS;
                break;
            case ")":
                this.tipoDato =Terminal.CIERRA_PARENTESIS;
                break;
            case "'":
                this.tipoDato=Terminal.CADENA_LITERAL;
                break;
            case ":=":
                this.tipoDato =Terminal.ASIGNACION;
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
