package compilador;

public class TokenSimbolo implements Token {

    private String simbolo;

	public TokenSimbolo(String valor) {
		setValor(valor);
	}




    public String getValor() {
        return simbolo;
    }

    public String getTipo() {
		return simbolo;
	}

    public void setValor(String simbolo) {
        if(simbolo!=null) {
            this.simbolo = simbolo;
        }
    }
}
