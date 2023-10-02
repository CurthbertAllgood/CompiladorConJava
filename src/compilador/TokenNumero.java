package compilador;

import static java.lang.Integer.parseInt;

public class TokenNumero implements Token {

    private int numero;
    private int contador;
	public TokenNumero(String valor, int contador) {
		setValor(valor);
        setContador(contador);
	}



    public String getValor() {
            return String.valueOf(numero);
        }

    public String getTipo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getNumero() {
        return numero;
    }

    @Override
    public void setValor(String x) {
        if(x!=null){
            numero= parseInt(x);
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

}
