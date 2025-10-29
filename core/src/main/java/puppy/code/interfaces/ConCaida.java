package puppy.code.interfaces;

public interface ConCaida {
    void iniciarCaida();
    boolean escapoDeLaPantalla();
    void actualizarCaida(float delta);
    boolean estaCayendo();
    int getY();
    float getVelocidadCaida();
    void setVelocidadCaida(float velocidad);
    void detenerCaida();
}
