package compilador;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class AnalizadorLexico {

	private FileReader fr;
	private List<Token> tokens = new ArrayList<Token>();

	public AnalizadorLexico(String archivo) {
		try {
			this.fr = new FileReader(archivo);
		} catch (FileNotFoundException ex) {
			System.out.println("no se encontro el archivo " + ex);
		}
	}

	public void scanner() throws IOException {
		int caracter = fr.read();
		String cadena = "";
		while (caracter != -1) {
			boolean flag= true;
			if (Character.isLetter(caracter)) {
				//INICIA GUARDAR VARIABLES/PALABRAS RESERVADAS
				while (Character.isLetterOrDigit(caracter)) {
					cadena += String.valueOf((char) caracter);
					caracter = fr.read();
				}
				TokenVariable token = new TokenVariable();
				token.setValor(cadena);
				tokens.add(token);
				cadena = "";
				//FINALIZA GUARDAR VARIABLES/PALABRAS RESERVADAS
			} else if (Character.isDigit(caracter)) {
				//INICIA EL GUARDAR ENTERO
				while (Character.isDigit(caracter)) {
					cadena +=String.valueOf((char)caracter);
					caracter = fr.read();
				}
				TokenNumero token = new TokenNumero();
				token.setValor(Integer.parseInt(cadena));
				tokens.add(token);
				cadena="";
				//FINALIZA EL GUARDAR ENTERO
			}else if(caracter == ';'){
				cadena+=caracter;
				TokenSimbolo token = new TokenSimbolo();
				token.setValor(String.valueOf(cadena));
				tokens.add(token);
				cadena="";
				caracter= fr.read();

			}else{
				//INICIO FLAG
				flag=false;
				//FINAL FLAG
			}

			if(!flag) {
				caracter = fr.read();
				}


		}
		for (Token valor : tokens) {
			System.out.println(valor.getValor());
		}

	}
}

