package compilador;

public class TokenSimbolo implements Token {

    private String simbolo="";

	public TokenSimbolo() {
		this.simbolo = simbolo;
	}



    public String getValor() {
        return simbolo;
    }

    public String getTipo() {
		return simbolo;
	}

    public void setValor(String simbolo) {
        this.simbolo= simbolo;
    }
}
