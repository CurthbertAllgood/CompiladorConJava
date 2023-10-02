package compilador;

public class TokenPalabraReservada implements Token {

    private String palabraReservada;
    private int contador;
	TokenPalabraReservada(String valorToken) {
		throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
	}

    public String getValor() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getTipo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    public void setValor(String variable){

    }
    @Override
    public int getContador() {
        return contador;
    }
    @Override
    public void setContador(int contador){
        if(contador!=0){
            this.contador=contador;
        }
    }
}
