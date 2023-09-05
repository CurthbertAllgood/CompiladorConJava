package compilador;

public class TokenNumero implements Token {

    private int numero;

	public TokenNumero() {
		this.numero = numero;
	}


    public String getValor() {
            return String.valueOf(numero);
        }

    public String getTipo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setValor(int cadena) {
        this.numero = cadena;
    }

}
