package compilador;


import compilador.tokens.ETerminal;
import compilador.tokens.Token;

import java.io.IOException;


public class compilador {

    public static void main(String[] args) {
        // Ruta del archivo a analizar
        String archivo = "/home/carlos-ortiz/Documentos/Sistemas de Computacion 1/PL0/BIEN-05.PL0";
        AnalizadorLexico a = new AnalizadorLexico(archivo);
        AnalizadorSintactico sintactico = new AnalizadorSintactico(a);


        /*
        try {
            // Iniciar el análisis sintáctico llamando a `programa`
            sintactico.programa();
            System.out.println("Análisis sintáctico completado exitosamente.");
        } catch (IOException e) {
            System.out.println("Error durante el análisis: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error sintáctico: " + e.getMessage());
        } finally {
            try {
                a.cerrar();
            } catch (IOException e) {
                System.out.println("Error al cerrar el archivo: " + e.getMessage());
            }
        }

        // BUCLE ANALIZADOR LÉXICO (comentado)
        */
        try {
            Token token;
            do {
                token = a.scanear();
                System.out.println(token);
            } while (token.getTipo() != ETerminal.EOF);

            System.out.println("Análisis léxico completado exitosamente.");
        } catch (IOException e) {
            System.out.println("Error durante el análisis: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error sintáctico: " + e.getMessage());
        } finally {
            try {
                a.cerrar();
            } catch (IOException e) {
                System.out.println("Error al cerrar el archivo: " + e.getMessage());
            }
        }

    }
}
