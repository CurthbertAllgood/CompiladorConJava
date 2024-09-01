package compilador;

public class TCadena implements Token {

    private String valor;  // Valor de la cadena literal
    private int contador;  // Número de línea o contador asociado al token
    private Terminal tipoDato;  // Tipo del token

    public TCadena(String valor, int contador) {
        setValor(valor);
        setContador(contador);
        setTipoDato(valor);
    }

    @Override
    public String getValor() {
        return valor;
    }

    @Override
    public void setValor(String valor) {
        if (valor != null) {
            this.valor = valor;
        }
    }

    @Override
    public int getContador() {
        return contador;
    }

    @Override
    public void setContador(int contador) {
        if (contador > 0) { // Generalmente se espera que el contador sea positivo
            this.contador = contador;
        }
    }

    @Override
    public void setTipoDato(String valor) {
        this.tipoDato = Terminal.CADENA_LITERAL; // Asigna un tipo fijo para las cadenas literales
    }

    @Override
    public Terminal getTipo() {
        return tipoDato;
    }

    @Override
    public String toString() {
        return "TokenCadena{" +
                "valor='" + valor + '\'' +
                ", contador=" + contador +
                ", tipoDato=" + tipoDato +
                '}';
    }
}
