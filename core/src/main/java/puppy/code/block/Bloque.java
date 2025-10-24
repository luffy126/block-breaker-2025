package puppy.code.block;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import puppy.code.interfaces.Dañable;

import java.util.Random;

public abstract class Bloque implements Dañable {
    protected int x,y,width,height;
    protected Color cc;
    protected boolean destroyed;

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

    public boolean debeEliminarse() {
        return destroyed;
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
