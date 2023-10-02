package compilador;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class AnalizadorLexico {
	private FileReader fr;

	private List<Token> tokens = new ArrayList<Token>();

	public TokenFactory tf= new TokenFactory();
	public AnalizadorLexico(String archivo) {
		try {
			this.fr = new FileReader(archivo);
		} catch (FileNotFoundException ex) {
			System.out.println("no se encontro el archivo " + ex);
		}
	}


	public void scanner() throws IOException {
		int caracter = fr.read(),opcion=0, contador=1;
		while (caracter != -1) {
			Token tipoToken;
			StringBuilder cadena= new StringBuilder();
			if (Character.isLetter(caracter)) {
				//INICIA GUARDAR VARIABLES/PALABRAS RESERVADAS
				while (Character.isLetterOrDigit(caracter)) {
					cadena.append((char) caracter);
					caracter = fr.read();
				}
				opcion=1;
				//FINALIZA GUARDAR VARIABLES/PALABRAS RESERVADAS
			} else if(Character.isDigit(caracter) ) {
				//INICIA VERIFICA ENTERO
				while (Character.isDigit(caracter)) {
					cadena.append((char) caracter);
					caracter = fr.read();
					}
				opcion=2;
				//FINALIZA EL VERIFICA ENTERO

			}
			else if(!Character.isLetterOrDigit((char)caracter)&&(caracter != 32)){
				//Inicia el verificador de ;

				if(caracter!=13) {
					cadena.append((char) caracter);
				}else{
					contador++;
				}
				caracter = fr.read();
				opcion=3;
				//Finaliza el verificador de ;

			}else {
				caracter= fr.read();
			}
			if(!cadena.isEmpty()) {
				tipoToken= tf.crearToken(opcion, cadena.toString(),contador);
				tokens.add(tipoToken);
			}
		}

		getTokens();
		fr.close();
	}

	public void getTokens() {
		for (Token valor : tokens) {

			System.out.println("valor del token --> "+valor.getValor()+" --> tipo de token: "+valor+" --> linea: "+valor.getContador());

		}
	}

}




