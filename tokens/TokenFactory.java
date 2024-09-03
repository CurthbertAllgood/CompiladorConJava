package compilador.tokens;


public class TokenFactory {

    public Token crearToken(int tipo, String valor, int contador) {
        Token tipoToken;
        switch (tipo) {
            case 1 -> {
                tipoToken = new TVariable(valor, contador);
            }
            case 2 -> {
                tipoToken = new TNumero(valor, contador);
            }
            case 3 -> {
                tipoToken = new TSimbolo(valor, contador);
            }
            case 4 -> {
                tipoToken = new TCadena(valor, contador); // Asumiendo que tienes una clase para cadenas literales
            }

            default -> throw new IllegalStateException("Unexpected value: " + tipo);
        }
        return tipoToken;
    }
}
