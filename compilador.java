/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compilador;

import java.io.*;


public class compilador {

    public static void main(String[] args) throws IOException {


        AnalizadorLexico a = new AnalizadorLexico("C:\\Users\\ferna\\IdeaProjects\\CompiladorConJava\\src\\compilador\\archivo.txt");

        a.scanner();
    }
}

