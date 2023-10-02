package compilador;

public class TokenSimbolo implements Token {

    private String simbolo;
    private int contador;
	public TokenSimbolo(String valor, int contador) {
		setValor(valor);
        setContador(contador);
	}



    public String getValor() {
        return simbolo;
    }

    public String getTipo() {
		return simbolo;
	}

    public void setValor(String simbolo) {
        if(simbolo!=" ") {
            this.simbolo = simbolo;
        }else{
            System.out.println("el dato ingresado es incorrecto");

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
