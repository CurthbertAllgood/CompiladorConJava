package compilador.tokens;

public class TVariable implements Token {

    private String variable;
    private int contador;

    private ETerminal tipoDato;
	public TVariable(String variable, int contador) {
		setValor(variable);
        setContador(contador);
        setTipoDato(variable);
	}



    public String getValor() {
        return variable;
    }

    public ETerminal getTipo() {
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
                this.tipoDato = ETerminal.IF;
                break;
            case "CALL":
                this.tipoDato = ETerminal.CALL;
                break;
            case "ODD":
                this.tipoDato = ETerminal.ODD;
                break;
            case "THEN":
                this.tipoDato = ETerminal.THEN;
                break;
            case "READLN":
                this.tipoDato = ETerminal.READLN;
                break;
            case "WRITELN":
                this.tipoDato = ETerminal.WRITELN;
                break;
            case "WRITE":
                this.tipoDato = ETerminal.WRITE;
                break;
            case "VAR":
                this.tipoDato = ETerminal.VAR;
                break;
            case "NULO":
                this.tipoDato = ETerminal.NULO;
                break;
            case "PROCEDURE":
                this.tipoDato = ETerminal.PROCEDURE;
                break;
            case "WHILE":
                this.tipoDato = ETerminal.WHILE;
                break;
            case "DO":
                this.tipoDato = ETerminal.DO;
                break;
            case "CONST":
                this.tipoDato = ETerminal.CONST;
                break;
            case "BEGIN":
                this.tipoDato = ETerminal.BEGIN;
                break;
            case "END":
                this.tipoDato = ETerminal.END;
                break;
            default:
                this.tipoDato= ETerminal.IDENTIFICADOR;
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
