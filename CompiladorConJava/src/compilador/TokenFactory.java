package compilador;


public class TokenFactory {



    public Token crearToken(int tipo, String valor, int contador) {
        Token tipoToken;
        switch (tipo) {
            case 1 -> {
                tipoToken = new TokenVariable(valor, contador);
            }
            case 2 -> {
                tipoToken = new TokenNumero(valor, contador);

            }
            case 3 -> {
                tipoToken = new TokenSimbolo(valor, contador);

            }
            default -> throw new IllegalStateException("Unexpected value: " + tipo);
        }
        return tipoToken;
    }


}