package compilador;

public class TokenVariable implements Token {

    private String variable;
    private int contador;

	public TokenVariable(String simbolo, int contador) {
		setValor(simbolo);
        setContador(contador);
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
}
