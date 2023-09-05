package compilador;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class LectorDeArchivo {

    private FileReader reader;

    public LectorDeArchivo(String archivo) {
        try {
            this.reader = new FileReader(archivo);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("no se encuentra el archivo" + e);
        }
    }


    public char leerProxCaracter() throws IOException {
        try {
            int caracter = reader.read();

            return (char) caracter;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return '\0';
    }
}