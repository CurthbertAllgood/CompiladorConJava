package compilador.compilador;

import compilador.compilador.tokens.Token;
import compilador.compilador.tokens.TEOF;
import compilador.compilador.tokens.TokenFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

class AnalizadorLexico {
	private FileReader fr;
	private TokenFactory tf = new TokenFactory();
	private int contador = 1;
	private int siguienteCaracter = -1; // Variable para guardar el próximo carácter

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

		cadena.setLength(0);

		// Usa el siguiente carácter si ya está disponible
		if (siguienteCaracter != -1) {
			caracter = siguienteCaracter;
			siguienteCaracter = -1; // Reinicia la variable
		} else {
			caracter = fr.read();
		}

		// Fin del archivo
		if (caracter == -1) {
			return new TEOF("EOF", contador);
		}

		// Ignorar los espacios en blanco, tabulaciones y los saltos de línea
		while (caracter == ' ' || caracter == '\n' || caracter == '\r' || caracter == '\t') {
			if (caracter == '\n') {
				contador++;
			}
			caracter = fr.read();
		}

		// Filtra el carácter ￿ (0xFFFF) que representa el fin de archivo
		if (caracter == 0xFFFF) {
			return new TEOF("EOF", contador);
		}

		// Cadena literal con comillas simples
		if (caracter == '\'') {
			cadena.append((char) caracter);
			caracter = fr.read();
			while (caracter != -1 && caracter != '\'') {
				cadena.append((char) caracter);
				caracter = fr.read();
			}
			if (caracter == '\'') {
				cadena.append((char) caracter);
				opcion = 4;
			}
		}
		// Cadena literal con comillas dobles
		else if (caracter == '"') {
			cadena.append((char) caracter);
			caracter = fr.read();
			while (caracter != -1 && caracter != '"') {
				cadena.append((char) caracter);
				caracter = fr.read();
			}
			if (caracter == '"') {
				cadena.append((char) caracter);
				opcion = 4;
			}
		} else if (Character.isLetter(caracter)) { // Identificadores y palabras reservadas
			while (Character.isLetterOrDigit(caracter)) {
				cadena.append((char) caracter);
				caracter = fr.read();
			}
			siguienteCaracter = caracter;
			opcion = 1;
		} else if (Character.isDigit(caracter)) { // Números
			while (Character.isDigit(caracter)) {
				cadena.append((char) caracter);
				caracter = fr.read();
			}
			siguienteCaracter = caracter;
			opcion = 2;
		} else { // Símbolos y otros caracteres
			cadena.append((char) caracter);

			// Verificación para caracteres especiales con más de un símbolo
			if (caracter == ':' || caracter == '<' || caracter == '>') {
				caracter = fr.read();
				if ((caracter == '=' && (cadena.charAt(0) == ':' || cadena.charAt(0) == '<' || cadena.charAt(0) == '>'))
						|| (caracter == '>' && cadena.charAt(0) == '<')) {
					cadena.append((char) caracter);
				} else {
					siguienteCaracter = caracter; // Guarda el carácter no consumido
				}
			}

			opcion = 3; // Asume que es un símbolo
		}

		if (opcion == 0) {
			//throw new IllegalStateException("No se pudo determinar el tipo de token");
		}

		return tf.crearToken(opcion, cadena.toString(), contador);
	}

	public void cerrar() throws IOException {
		if (fr != null) {
			fr.close();
		}
	}
}
