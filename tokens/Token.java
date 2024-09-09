package compilador.tokens;

public interface Token {

    public String getValor();

    public ETerminal getTipo();

    public void setValor(String x);

    public int getContador();

    public void setContador(int x);

    public void setTipoDato(String x);

    @Override
    String toString();

}
