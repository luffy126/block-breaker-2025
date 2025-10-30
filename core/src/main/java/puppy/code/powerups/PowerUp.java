package puppy.code.powerups;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import puppy.code.game.BlockBreakerGame;
import puppy.code.interfaces.ConCaida;

public abstract class PowerUp implements ConCaida {

    protected int x, y;
    protected int width, height;
    protected Color color;
    protected float velocidadCaida;
    protected boolean cayendo;

    private static final float VELOCIDAD_CAIDA_INICIAL = -150f;
    private static final float ACELERACION_GRAVEDAD = -200f;
    private static final float VELOCIDAD_MAXIMA = -350f;

    public PowerUp(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.width = 20;
        this.height = 20;
        this.color = color;
        this.velocidadCaida = VELOCIDAD_CAIDA_INICIAL;
        this.cayendo = false;
    }
    public abstract void aplicarEfecto(BlockBreakerGame game); //Aqui ya si, esta la herencia y la implicancia de usar abstract para los tipos.

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

    public int getX() {
        return x;
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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
