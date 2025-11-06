package puppy.code.block;

import com.badlogic.gdx.graphics.Color;
import puppy.code.interfaces.Dañable;

public class BloqueDuro extends Bloque implements Dañable {

    private int health = 3;
    private static final String TEX_3 = "bloques/duro3.png";
    private static final String TEX_2 = "bloques/duro2.png";
    private static final String TEX_1 = "bloques/duro.png";

    public BloqueDuro (int x, int y, int width, int height, String rutaTexturaDuro) {
        super(x, y, width, height, rutaTexturaDuro);
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
                case 3: /* textura 3*/
                    setTexture(TEX_1);
                    break;
                case 2:
                    setTexture(TEX_2);
                    break;
                case 1:
                    setTexture(TEX_3);
                    break;
            }
        }
    }
}
