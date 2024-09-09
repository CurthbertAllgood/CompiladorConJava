package compilador;

import compilador.tokens.Token;
import compilador.tokens.TokenFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

class AnalizadorLexico {
	private FileReader fr;
	private TokenFactory tf = new TokenFactory();
	private int contador = 1;

	public AnalizadorLexico(String archivo) {
		try {
			this.fr = new FileReader(archivo);
		} catch (FileNotFoundException ex) {
			System.out.println("No se encontró el archivo: " + ex);
		}
	}

	public Token scanear() throws IOException {
		int caracter;
		int opcion = 0;
		StringBuilder cadena = new StringBuilder();

		caracter = fr.read();
		if (caracter == -1) {
			System.out.println("Fin del archivo alcanzado.");
			return null; // Fin del archivo
		}

		// Ignorar los espacio en blanco y los saltos de linea
		while (caracter == ' ' || caracter == '\n' || caracter == '\r') {
			if (caracter == '\n') {
				contador++;
			}
			caracter = fr.read();
		}

		// Cadena literal
		if (caracter == '\'') {
			cadena.setLength(0);
			caracter = fr.read();
			while (caracter != -1 && caracter != '\'') {
				cadena.append((char) caracter);
				caracter = fr.read();
			}
			if (caracter == '\'') {
				opcion = 4;
			}
			// Identificadores y palabras reservadas
		} else if (Character.isLetter(caracter)) {
			cadena.setLength(0);
			while (Character.isLetterOrDigit(caracter)) {
				cadena.append((char) caracter);
				caracter = fr.read();
			}
			opcion = 1;
			// Números
		} else if (Character.isDigit(caracter)) {
			cadena.setLength(0);
			while (Character.isDigit(caracter)) {
				cadena.append((char) caracter);
				caracter = fr.read();
			}
			opcion = 2;
			// Símbolos
		} else if (!Character.isLetterOrDigit(caracter)) {
			cadena.setLength(0);
			opcion = 3;
			cadena.append((char) caracter);
			if (caracter == ':') {
				caracter = fr.read();
				if (caracter == '=') {
					cadena.append((char) caracter);
				}
			} else if (caracter == '<' || caracter == '>') {
				caracter = fr.read();
				if (caracter == '=' || (cadena.charAt(0) == '<' && caracter == '>')) {
					cadena.append((char) caracter);
				}
			}
		}

		if (opcion == 0) {
			throw new IllegalStateException("No se pudo determinar el tipo de token");
		}

		Token token = tf.crearToken(opcion, cadena.toString(), contador);
		System.out.println("Token creado: " + token.getTipo() + " con valor: " + token.getValor());
		return token;
	}


	public void cerrar() throws IOException {
		if (fr != null) {
			fr.close();
		}
	}
}
