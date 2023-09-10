package compilador;

import static java.lang.Integer.parseInt;

public class TokenNumero implements Token {

    private int numero;

	public TokenNumero(String valor) {
		setValor(valor);
	}

    public TokenNumero() {

    }


    public String getValor() {
            return String.valueOf(numero);
        }

    public String getTipo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setValor(String x) {
        if(x!=null){
            numero= parseInt(x);
        }
    }


}
