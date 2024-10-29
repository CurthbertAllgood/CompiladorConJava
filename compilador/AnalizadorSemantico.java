package compilador.compilador;

import compilador.compilador.tokens.Token;
import java.util.HashMap;
import java.util.Map;

public class AnalizadorSemantico {

    private static final int MAX_IDENTIFIERS = 100;
    private final Map<String, Token> tablaSimbolos;  // Usaremos un HashMap para manejar variables con sus valores
    private int memoria = 0;
    private int base = 0;

    public AnalizadorSemantico() {
        tablaSimbolos = new HashMap<>();
    }

    public void agregarToken(Token identificador, int base) throws Exception {
        identificador.setScope(base);

        if (memoria >= MAX_IDENTIFIERS) {
            throw new Exception("Error semántico: Memoria agotada para identificadores.");
        }

        // Verificar si el identificador ya ha sido declarado en el ámbito actual
        if (tablaSimbolos.containsKey(identificador.getValor()) && tablaSimbolos.get(identificador.getValor()).getScope() == base) {
            throw new Exception("Error semántico: El identificador '" + identificador.getValor() + "' ya está declarado en este ámbito.");
        }

        // Agregar el identificador a la tabla de símbolos
        tablaSimbolos.put(identificador.getValor(), identificador);
        memoria++;
    }

    public Token obtenerToken(String valor) throws Exception {
        // Buscar el token en la tabla de símbolos
        if (tablaSimbolos.containsKey(valor)) {
            return tablaSimbolos.get(valor);
        } else {
            throw new Exception("Error semántico: Identificador '" + valor + "' no ha sido declarado.");
        }
    }

    public void verificarVariableDeclarada(String valor) throws Exception {
        if (!tablaSimbolos.containsKey(valor)) {
            throw new Exception("Error semántico: La variable '" + valor + "' no ha sido declarada.");
        }
    }

    public void imprimirTablaSimbolos() {
        System.out.println("Tabla de Símbolos:");
        for (Map.Entry<String, Token> entry : tablaSimbolos.entrySet()) {
            System.out.println("Variable: " + entry.getKey() + ", Token: " + entry.getValue());
        }
    }
}
