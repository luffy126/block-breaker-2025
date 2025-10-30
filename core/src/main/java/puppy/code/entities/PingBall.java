package puppy.code.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import puppy.code.block.Bloque;
import puppy.code.factories.SonidoFactory;

public class PingBall extends SonidoFactory {
	    private int x;
	    private int y;
	    private int size;
	    private int xSpeed;
	    private int ySpeed;
	    private Color color = Color.WHITE;
	    private boolean estaQuieto;
        private SonidoFactory gestorSonido;

	    public PingBall(int x, int y, int size, int xSpeed, int ySpeed, boolean iniciaQuieto) {
	        this.x = x;
	        this.y = y;
	        this.size = size;
	        this.xSpeed = xSpeed;
	        this.ySpeed = ySpeed;
	        estaQuieto = iniciaQuieto;
            gestorSonido = new SonidoFactory();
	    }

	    public boolean estaQuieto() {
	    	return estaQuieto;
	    }
	    public void setEstaQuieto(boolean bb) {
	    	estaQuieto=bb;
	    }
	    public void setXY(int x, int y) {
	    	this.x = x;
	        this.y = y;
	    }
	    public int getY() {return y;}

	    public void draw(ShapeRenderer shape){
	        shape.setColor(color);
	        shape.circle(x, y, size);
	    }

	    public void update() {
	    	if (estaQuieto) return;
	        x += xSpeed;
	        y += ySpeed;
	        if (x-size < 0 || x+size > Gdx.graphics.getWidth()) {
	            xSpeed = -xSpeed;
	        }
	        if (y+size > Gdx.graphics.getHeight()) {
	            ySpeed = -ySpeed;
	        }
	    }

	    public void checkCollision(Paddle paddle) {
	        if(collidesWith(paddle)){
	            color = Color.RED;
	            ySpeed = -ySpeed;
                gestorSonido.reproducirGolpeBola();
	        }
	        else{
	            color = Color.WHITE;
	        }
	    }
	    private boolean collidesWith(Paddle pp) {

	    	boolean intersectaX = (pp.getX() + pp.getWidth() >= x-size) && (pp.getX() <= x+size);
	        boolean intersectaY = (pp.getY() + pp.getHeight() >= y-size) && (pp.getY() <= y+size);
	    	return intersectaX && intersectaY;
	    }

	    public void checkCollision(Bloque bloque) {
	        if(collidesWith(bloque)){
	            ySpeed = - ySpeed;
	            bloque.daño();
                gestorSonido.reproducirGolpeBola();
	        }
	    }
	    private boolean collidesWith(Bloque bb) {

	    	boolean intersectaX = (bb.getX() + bb.getWidth() >= x-size) && (bb.getX() <= x+size);
	        boolean intersectaY = (bb.getY() + bb.getHeight() >= y-size) && (bb.getY() <= y+size);
	    	return intersectaX && intersectaY;
	    }

    public int getXSpeed() {
        return xSpeed;
    }
    public int getYSpeed() {
            return ySpeed;
    }
    public void setXSpeed(float nuevaXSpeed) {
            xSpeed = (int)nuevaXSpeed;
    }
    public void setYSpeed(float nuevaYSpeed){
            ySpeed = (int)nuevaYSpeed;
    }
}
