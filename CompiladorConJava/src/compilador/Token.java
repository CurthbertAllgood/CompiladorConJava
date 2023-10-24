package compilador;

public interface Token {

    public String getValor();

    public Terminal getTipo();

    public void setValor(String x);

    public int getContador();

    public void setContador(int x);

    public void setTipoDato(String x);

}
