package puppy.code.block;

import com.badlogic.gdx.graphics.Color;
import puppy.code.interfaces.Dañable;

public class BloqueExplosion extends Bloque implements Dañable {

    public BloqueExplosion (int x, int y, int width, int height) {
        super(x, y, width, height, Color.GOLD);
    }

    @Override
    public void daño() {
        destroyed = true;
    }

    @Override
    public void comportamiento(float delta) {

    }
}
