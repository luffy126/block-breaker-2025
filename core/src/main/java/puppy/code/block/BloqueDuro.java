package puppy.code.block;

import com.badlogic.gdx.graphics.Color;
import puppy.code.interfaces.Dañable;

public class BloqueDuro extends Bloque implements Dañable {

    private int health = 3;

    public BloqueDuro (int x, int y, int width, int height) {
        super(x, y, width, height, Color.GOLD);
    }

    @Override
    public void daño() {
        health--;
        if (health <= 0) {
            destroyed = true;
        }
    }

    @Override
    public void comportamiento(float delta) {
        if (!destroyed) {
            switch (health) {
                case 3: cc = Color.DARK_GRAY; break;
                case 2: cc = Color.GRAY; break;
                case 1: cc = Color.LIGHT_GRAY; break;
            }
        }
    }
}
