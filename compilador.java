package compilador;

import compilador.tokens.Token;

import java.io.IOException;

public class compilador {

    public static void main(String[] args) {
        String archivo = "/home/carlos-ortiz/Documentos/Sistemas De Computacion 1/compilador/BIEN-00.PL0";
        AnalizadorLexico a = new AnalizadorLexico(archivo);

        Token token;

        try {
            do {
                token = a.scanear();

                //Corregir, generar un TokenEOF para darle fin al doWhile
                if (token != null) {
                    System.out.println(token);
                }
            } while (token != null);
            a.cerrar(); } catch (IOException e) {
            System.out.println("Error durante el análisis: " + e.getMessage());
        }
    }
}
