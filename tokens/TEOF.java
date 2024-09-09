package compilador.tokens;

public class TEOF implements Token {

    private int contador;

    public TEOF(int contador) {
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
        // No se necesita implementar esto para EOF
    }

    @Override
    public String toString() {
        return "TEOF{ contador=" + contador + " }";
    }
}
