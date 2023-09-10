package compilador;

import java.util.ArrayList;
import java.util.List;

public class TokenFactory {

    private List<Token> tokens = new ArrayList<Token>();
    public Token crearToken(int tipo, String valor){
            Token tipoToken = init();
        switch (tipo) {
            case 1 -> {
                tipoToken = new TokenVariable(valor);
            }
            case 2 -> {
                tipoToken = new TokenNumero(valor);

            }
            case 3 -> {
                tipoToken = new TokenSimbolo(valor);


            }
            default -> throw new IllegalStateException("Unexpected value: " + tipo);
        }
        tokens.add(tipoToken);
        return tipoToken;
    }

    public void getTokens(){
        for(Token valor : tokens) {
            System.out.println(valor.getValor());
        }
    }
    private static Token init(){
        return null;
    }

}
