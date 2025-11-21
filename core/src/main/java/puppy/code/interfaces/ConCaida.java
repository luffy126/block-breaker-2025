package puppy.code.interfaces;

public interface ConCaida {
    void iniciarCaida();
    boolean escapoDeLaPantalla();
    boolean estaCayendo();
    int getY();
    float getVelocidadCaida();
    void setVelocidadCaida(float velocidad);
    void detenerCaida();
}
