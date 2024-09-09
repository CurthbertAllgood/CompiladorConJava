package compilador.tokens;

import static java.lang.Integer.parseInt;

public class TNumero implements Token {

    private int numero;
    private int contador;

    private ETerminal tipoDato;
	public TNumero(String valor, int contador) {
		setValor(valor);
        setContador(contador);
        setTipoDato(valor);
	}



    public String getValor() {
            return String.valueOf(numero);
        }

        public ETerminal getTipo() {
            return this.tipoDato;
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


    @Override
    public void setTipoDato(String valor){
        this.tipoDato = ETerminal.NUMERO;

    }

    public String toString() {
        return "TokenVariable{" +
                "variable='" + numero + '\'' +
                ", contador=" + contador +
                ", tipoDato=" + tipoDato +
                '}';
    }

}
