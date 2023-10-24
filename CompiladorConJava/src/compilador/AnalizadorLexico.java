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


	public Token scanner() throws IOException {
		int caracter = fr.read(),opcion=0, contador=1;
		Token tipoToken = null;
		while (caracter != -1) {
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
			else if(!Character.isLetterOrDigit((char)caracter) && (caracter != 32) && (caracter != 13) && (caracter != 10)){
				opcion=3;
					cadena.append((char) caracter);
					if((char)caracter==':'){
						caracter = fr.read();
						if((char)caracter=='=') {
							cadena.append((char) caracter);
							caracter = fr.read();
						}
					}else if((char)caracter =='<'){
						caracter = fr.read();
						if((char)caracter=='=' || (char)caracter=='>'){
							cadena.append((char)caracter);
							caracter = fr.read();
						}
					}else if((char)caracter=='>'){
						caracter =fr.read();
						if((char)caracter=='=') {
							cadena.append((char) caracter);
							caracter = fr.read();
						}
					}else{
						caracter = fr.read();
					}

			}else{
				if(caracter==13){
					contador++;
				}
				caracter = fr.read();
			}
			if(!cadena.isEmpty()) {
				tipoToken= tf.crearToken(opcion, cadena.toString(),contador);
			}

		}
		return tipoToken;
	}


}




