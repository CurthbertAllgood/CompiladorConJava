package compilador.tokens;

public class TEOF implements Token {

    private int contador;
    private String variable;

    public TEOF(String variable,int contador ){
        this.variable = variable;
        this.contador = contador;

    }

    @Override
    public String getValor() {
        return "EOF";
    }

    @Override
    public ETerminal getTipo() {
        return ETerminal.EOF;
    }

    @Override
    public void setValor(String x) {

    }

    @Override
    public int getContador() {
        return contador;
    }

    @Override
    public void setContador(int contador) {
        this.contador = contador;
    }

    @Override
    public void setTipoDato(String valor) {
        variable = String.valueOf(ETerminal.EOF);
    }

    @Override
    public String toString() {
        return "TEOF{ contador=" + contador + " }";
    }
}
