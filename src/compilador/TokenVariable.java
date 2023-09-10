package compilador;

public class TokenVariable implements Token {

    private String variable;

	public TokenVariable(String simbolo) {
		setValor(simbolo);
	}



    public String getValor() {
        return variable;
    }

    public String getTipo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setValor(String valor) {
            if(valor!=null){
                this.variable=valor;
            }
    }
}
