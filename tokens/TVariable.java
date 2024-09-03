package compilador.tokens;

public class TVariable implements Token {

    private String variable;
    private int contador;

    private Terminal tipoDato;
	public TVariable(String variable, int contador) {
		setValor(variable);
        setContador(contador);
        setTipoDato(variable);
	}



    public String getValor() {
        return variable;
    }

    public Terminal getTipo() {
        return this.tipoDato;
    }

    @Override
    public void setValor(String variable) {
            if(variable!=null){
                this.variable=variable;
            }
    }


    @Override
    public int getContador() {
        return contador;
    }

    @Override
    public void setContador(int contador){
        if(contador!=0){
            this.contador=contador;
        }
    }


    @Override
    public void setTipoDato(String variable){
        switch (variable.toUpperCase()){
            case "IF":
                this.tipoDato =Terminal.IF;
                break;
            case "CALL":
                this.tipoDato =Terminal.CALL;
                break;
            case "ODD":
                this.tipoDato =Terminal.ODD;
                break;
            case "THEN":
                this.tipoDato =Terminal.THEN;
                break;
            case "READLN":
                this.tipoDato =Terminal.READLN;
                break;
            case "WRITELN":
                this.tipoDato =Terminal.WRITELN;
                break;
            case "WRITE":
                this.tipoDato =Terminal.WRITE;
                break;
            case "VAR":
                this.tipoDato =Terminal.VAR;
                break;
            case "NULO":
                this.tipoDato =Terminal.NULO;
                break;
            case "PROCEDURE":
                this.tipoDato =Terminal.PROCEDURE;
                break;
            case "WHILE":
                this.tipoDato =Terminal.WHILE;
                break;
            case "DO":
                this.tipoDato =Terminal.DO;
                break;
            case "CONST":
                this.tipoDato =Terminal.CONST;
                break;
            case "BEGIN":
                this.tipoDato =Terminal.BEGIN;
                break;
            case "END":
                this.tipoDato =Terminal.END;
                break;
            default:
                this.tipoDato=Terminal.IDENTIFICADOR;
        }

    }

    public String toString() {
        return "TokenVariable{" +
                "variable='" + variable + '\'' +
                ", contador=" + contador +
                ", tipoDato=" + tipoDato +
                '}';
    }
}
