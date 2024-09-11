package compilador.tokens;

public class TVariable implements Token {

    private String variable;
    private int contador;

    private ETerminal tipoDato = ETerminal.IDENTIFICADOR;
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
    public void setTipoDato(String variable) {
        if (this.variable != null) {
            this.variable =  variable.toUpperCase();
            switch (this.variable) {
                case "IF" -> this.tipoDato = ETerminal.IF;
                case "CALL" -> this.tipoDato = ETerminal.CALL;
                case "ODD" -> this.tipoDato = ETerminal.ODD;
                case "THEN" -> this.tipoDato = ETerminal.THEN;
                case "READLN" -> this.tipoDato = ETerminal.READLN;
                case "WRITELN" -> this.tipoDato = ETerminal.WRITELN;
                case "WRITE" -> this.tipoDato = ETerminal.WRITE;
                case "VAR" -> this.tipoDato = ETerminal.VAR;
                case "NULO" -> this.tipoDato = ETerminal.NULO;
                case "PROCEDURE" -> this.tipoDato = ETerminal.PROCEDURE;
                case "WHILE" -> this.tipoDato = ETerminal.WHILE;
                case "DO" -> this.tipoDato = ETerminal.DO;
                case "CONST" -> this.tipoDato = ETerminal.CONST;
                case "BEGIN" -> this.tipoDato = ETerminal.BEGIN;
                case "END" -> this.tipoDato = ETerminal.END;
            }
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
