package puppy.code.block;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import puppy.code.entities.PowerUp;
import puppy.code.interfaces.Dañable;

import java.util.Random;

import static com.badlogic.gdx.math.MathUtils.random;

public abstract class Bloque implements Dañable {
    protected int x, y, width, height;
    protected Color cc;
    protected boolean destroyed;
    protected static final Random random = new Random();
    protected static final float PROBABILIDAD_POWERUP = 0.5f; // ESTA PARTE PERMITE EL PRINCIPIO ABIERTO CERRADO, SE MODIFICA AQUI Y NO LA LOGICA DE ABAJO.

    public Bloque(int x, int y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.cc = color;
        destroyed = false;
    }

    public void draw(ShapeRenderer shape){
        if (!destroyed) {
            shape.setColor(cc);
            shape.rect(x, y, width, height);
        }
    }

    public boolean debeEliminarse() {return destroyed;}

    public PowerUp generarPowerUp() {
        if (random.nextFloat() < PROBABILIDAD_POWERUP) {
            PowerUp.TipoPowerUp[] tipos = PowerUp.TipoPowerUp.values();
            PowerUp.TipoPowerUp tipoAleatorio = tipos[random.nextInt(tipos.length)];
            PowerUp powerUp = new PowerUp(x + width/2 - 10, y, tipoAleatorio);
            powerUp.iniciarCaida();
            return powerUp;
        }
        return null;
    }

    public abstract void comportamiento(float delta);

    public boolean isDestroyed() {return destroyed;}
    public Color getColor() {return cc;}
    public int getX(){return x;}
    public int getY(){return y;}
    public int getWidth(){return width;}
    public int getHeight(){return height;}

    public void setX(int x) {this.x = x;}
    public void setY(int y) {this.y = y;}
    public void setColor (Color cc) {this.cc = cc;}
    public void setDestroyed (boolean destroyed) {this.destroyed = destroyed;}
}
