package compilador.compilador;

import java.io.File;
import java.io.IOException;

public class compilador {

    public static void main(String[] args) {
        // Ruta del archivo de entrada .PL0
        String archivoEntrada = "C:\\Users\\Karina Ortiz\\Documents\\Facu 2024\\Sistemas de Computacion 1\\PL0\\BIEN-09.PL0";

        // Crear el analizador léxico
        AnalizadorLexico a = new AnalizadorLexico(archivoEntrada);
        // Crear el analizador sintáctico
        AnalizadorSintactico sintactico = new AnalizadorSintactico(a);

        try {
            // Ejecutar el análisis sintáctico
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
    }
}
