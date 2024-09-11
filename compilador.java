package compilador;

import java.io.IOException;

public class compilador {

    public static void main(String[] args) {
        String archivo = "/home/carlos-ortiz/Documentos/Sistemas De Computacion 1/compilador/BIEN-00.PL0";
        AnalizadorLexico a = new AnalizadorLexico(archivo);
        AnalizadorSintactico sintactico = new AnalizadorSintactico(a);

        try {
            // Iniciar el análisis sintáctico llamando a `programa`
            sintactico.programa();

            System.out.println("Análisis sintáctico completado exitosamente.");
            a.cerrar();
        } catch (IOException e) {
            System.out.println("Error durante el análisis: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error sintáctico: " + e.getMessage());
        }
    }
}