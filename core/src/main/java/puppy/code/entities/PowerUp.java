package puppy.code.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import puppy.code.interfaces.ConCaida;


// El PowerUp esta diseñado pensando que cae desde donde se destruyó un bloque.

public class PowerUp implements ConCaida {

    private int x, y;
    private int width, height;
    private Color color;
    private float velocidadCaida;
    private boolean cayendo;
    private TipoPowerUp tipo;

    private static final float VELOCIDAD_CAIDA_INICIAL = -150f;
    private static final float ACELERACION_GRAVEDAD = -200f;
    private static final float VELOCIDAD_MAXIMA = -400f;

    public enum TipoPowerUp {
        VIDA_EXTRA(Color.RED),
        PADDLE_GRANDE(Color.BLUE),
        BOLA_RAPIDA(Color.YELLOW),
        MULTI_BOLA(Color.PURPLE);

        private final Color color;

        TipoPowerUp(Color color) {
            this.color = color;
        }

        public Color getColor() {
            return color;
        }
    }

    public PowerUp(int x, int y, TipoPowerUp tipo) {
        this.x = x;
        this.y = y;
        this.width = 20;
        this.height = 20;
        this.tipo = tipo;
        this.color = tipo.getColor();
        this.velocidadCaida = VELOCIDAD_CAIDA_INICIAL;
        this.cayendo = false;
    }

    @Override
    public void iniciarCaida() {
        this.cayendo = true;
        this.velocidadCaida = VELOCIDAD_CAIDA_INICIAL;
    }

    @Override
    public void actualizarCaida(float delta) {
        if (!cayendo) {
            return;
        }

        velocidadCaida += ACELERACION_GRAVEDAD * delta;

        if (velocidadCaida < VELOCIDAD_MAXIMA) {
            velocidadCaida = VELOCIDAD_MAXIMA;
        }

        y += (int)(velocidadCaida * delta);
    }

    @Override
    public boolean escapoDeLaPantalla() {
        return y + height < 0;
    }

    @Override
    public boolean estaCayendo() {
        return cayendo;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public float getVelocidadCaida() {
        return velocidadCaida;
    }

    @Override
    public void setVelocidadCaida(float velocidad) {
        this.velocidadCaida = velocidad;
    }

    @Override
    public void detenerCaida() {
        this.cayendo = false;
        this.velocidadCaida = 0;
    }

    public void draw(ShapeRenderer shape) {
        if (cayendo) {
            shape.setColor(color);
            shape.rect(x, y, width, height);
        }
    }

    public boolean colisionaCon(int paddleX, int paddleY, int paddleWidth, int paddleHeight) {
        return cayendo &&
            x < paddleX + paddleWidth &&
            x + width > paddleX &&
            y < paddleY + paddleHeight &&
            y + height > paddleY;
    }

    public TipoPowerUp getTipo() {
        return tipo;
    }

    public int getX() {
        return x;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
