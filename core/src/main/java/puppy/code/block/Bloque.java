package puppy.code.block;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import puppy.code.interfaces.Dañable;

import java.util.Random;

import static com.badlogic.gdx.math.MathUtils.random;

public abstract class Bloque implements Dañable {
    protected int x, y, width, height;
    protected Texture texture;
    protected boolean destroyed;
    protected static final Random random = new Random();

    public Bloque(int x, int y, int width, int height, String rutaBloque) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.texture = new Texture(rutaBloque);
        this.texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        destroyed = false;
    }

    public void draw(SpriteBatch batch) {
        if (!destroyed) {
            batch.draw(texture, x, y, width, height);
        }
    }

    public boolean debeEliminarse() {return destroyed;}

    @Override
    public boolean estaActivo() {
        return !destroyed;
    }

    public abstract void comportamiento(float delta);

    public boolean isDestroyed() {return destroyed;}
    //public Color getColor() {return cc;}
    public int getX(){return x;}
    public int getY(){return y;}
    public int getWidth(){return width;}
    public int getHeight(){return height;}

    public void setX(int x) {this.x = x;}
    public void setY(int y) {this.y = y;}
    //public void setColor (Color cc) {this.cc = cc;}
    public void setDestroyed (boolean destroyed) {this.destroyed = destroyed;}
    public void setTexture(String ruta) {
        if (texture != null) texture.dispose();
        texture = new Texture(Gdx.files.internal(ruta));
    }
}
