package compilador.compilador;// GeneradorDeCodigo.java
import compilador.compilador.tokens.ETerminal;
import compilador.compilador.tokens.Token;

import java.io.FileOutputStream;
import java.io.IOException;

public class GeneradorDeCodigo {

    private byte[] memoria;
    private int topeMemoria;
    private final int TAMANO_HEADER = 512;

    public GeneradorDeCodigo() {
        memoria = new byte[8192];
        cargarMemoria();
        topeMemoria = 1792;
    }

    // Método para procesar tokens de manera genérica usando solo ETerminal
    public void procesarToken(Token token) {
        ETerminal tipo = token.getTipo();  // Obtenemos el tipo general del token (ETerminal)

        switch (tipo) {
            case MAS:
            case MENOS:
            case POR:
            case DIVIDIDO:
                cargarOperacion(tipo);
                break;
            case NUMERO:
                cargarInt(Integer.parseInt(token.getValor()));
                break;
            case IDENTIFICADOR:
                cargarIdentificador(token);
                break;
            default:
                System.out.println("Token no reconocido: " + token.getValor());
        }
    }

    private void cargarOperacion(ETerminal tipo) {
        switch (tipo) {
            case MAS:
                cargarByte(0x01); // Operación para suma
                break;
            case MENOS:
                cargarByte(0x02); // Operación para resta
                break;
            case POR:
                cargarByte(0x03); // Operación para multiplicación
                break;
            case DIVIDIDO:
                cargarByte(0x04); // Operación para división
                break;
            default:
                System.out.println("Operador no soportado: " + tipo);
        }
    }

    private void cargarIdentificador(Token token) {
        int direccion = obtenerDireccionVariable(token.getValor());
        cargarInt(direccion);
    }

    public void finalizarMemoria(int cantidadDeVariables, String nombreArchivo) throws IOException {
        int distancia = 1416 - (topeMemoria + 5);
        cargarByte(0xE9);
        cargarInt(distancia);

        int direccionInicioDeVariables = descargarIntDe(204) + descargarIntDe(212) + topeMemoria - TAMANO_HEADER;
        cargarIntEn(direccionInicioDeVariables, 1793);

        for (int i = 0; i < cantidadDeVariables; i++) {
            cargarInt(0);
        }

        cargarIntEn(topeMemoria - TAMANO_HEADER, 416);
        int fileAlignment = descargarIntDe(220);
        while (topeMemoria % fileAlignment != 0) {
            cargarByte(0x00);
        }

        int tamanoSeccionText = topeMemoria - TAMANO_HEADER;
        cargarIntEn(tamanoSeccionText, 188);
        cargarIntEn(tamanoSeccionText, 424);

        int sectionAlignment = descargarIntDe(216);
        cargarIntEn(((2 + tamanoSeccionText / sectionAlignment) * sectionAlignment), 240);
        cargarIntEn(((2 + tamanoSeccionText / sectionAlignment) * sectionAlignment), 208);

        generarArchivo(nombreArchivo);
    }

    private void generarArchivo(String nombreArchivo) throws IOException {
        String nombreArchivoExe = nombreArchivo.substring(0, nombreArchivo.length() - 4) + ".exe";
        try (FileOutputStream fos = new FileOutputStream(nombreArchivoExe)) {
            fos.write(memoria, 0, topeMemoria);
        }
    }

    public void mostrarMemoria() {
        for (int i = 0; i < topeMemoria; i++) {
            String aux = Integer.toHexString(memoria[i] & 0xFF);
            System.out.println("[" + i + "]: " + aux);
        }
    }

    public int getTopeMemoria() {
        return topeMemoria;
    }

    public void cargarByte(int nuevoByte) {
        memoria[topeMemoria++] = (byte) nuevoByte;
    }

    public void cargarByteEn(int nuevoByte, int posicion) {
        memoria[posicion] = (byte) nuevoByte;
    }

    public void cargarInt(int numero) {
        memoria[topeMemoria++] = (byte) (numero & 0xFF);
        memoria[topeMemoria++] = (byte) ((numero >> 8) & 0xFF);
        memoria[topeMemoria++] = (byte) ((numero >> 16) & 0xFF);
        memoria[topeMemoria++] = (byte) ((numero >> 24) & 0xFF);
    }

    public void cargarIntEn(int numero, int posicion) {
        memoria[posicion] = (byte) (numero & 0xFF);
        memoria[posicion + 1] = (byte) ((numero >> 8) & 0xFF);
        memoria[posicion + 2] = (byte) ((numero >> 16) & 0xFF);
        memoria[posicion + 3] = (byte) ((numero >> 24) & 0xFF);
    }

    public int descargarIntDe(int posicion) {
        return (memoria[posicion] & 0xFF)
                | ((memoria[posicion + 1] & 0xFF) << 8)
                | ((memoria[posicion + 2] & 0xFF) << 16)
                | ((memoria[posicion + 3] & 0xFF) << 24);
    }

    private void cargarMemoria() {
        memoria[0] = 0x4D;
        memoria[1] = 0x5A;
        memoria[60] = (byte) 0xA0;
    }

    private int obtenerDireccionVariable(String nombreVariable) {
        return 0; // Asumimos una dirección fija por simplicidad
    }
}
