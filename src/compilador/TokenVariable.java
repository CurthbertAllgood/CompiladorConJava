package compilador;

public class TokenVariable implements Token {

    private String variable;

	public TokenVariable() {
		this.variable = variable;
	}



    public void setValor(String variable) {
        this.variable = variable;
    }

    public String getValor() {
        return variable;
    }

    public String getTipo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
