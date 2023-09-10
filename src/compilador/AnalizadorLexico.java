package compilador;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class AnalizadorLexico {
	private FileReader fr;

	public TokenFactory tf= new TokenFactory();
	public AnalizadorLexico(String archivo) {
		try {
			this.fr = new FileReader(archivo);
		} catch (FileNotFoundException ex) {
			System.out.println("no se encontro el archivo " + ex);
		}
	}


	public void scanner() throws IOException {
		int caracter = fr.read(),opcion=0;
		while (caracter != -1) {
			String cadena="";
			if (Character.isLetter(caracter)) {
				//INICIA GUARDAR VARIABLES/PALABRAS RESERVADAS
				while (Character.isLetterOrDigit(caracter)) {
					cadena += (char)caracter;
					caracter = fr.read();
				}
				opcion=1;
				//FINALIZA GUARDAR VARIABLES/PALABRAS RESERVADAS
			} else if(Character.isDigit(caracter) ) {
				//INICIA VERIFICA ENTERO
				while (Character.isDigit(caracter)) {
					cadena += (char)caracter;
					caracter = fr.read();
					}
				opcion=2;
				//FINALIZA EL VERIFICA ENTERO

			}
			else if((char)caracter== ';'){
				//Inicia el verificador de ;

				cadena+=(char)caracter;
				caracter = fr.read();
				opcion=3;
				//Finaliza el verificador de ;
			}
			else{
				caracter= fr.read();
			}
			if(!cadena.isEmpty()) {
				tf.crearToken(opcion, cadena);
			}
		}

		tf.getTokens();
		fr.close();
	}

}




