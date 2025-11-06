package puppy.code.block;

import com.badlogic.gdx.graphics.Color;
import puppy.code.interfaces.Dañable;

public class BloqueRegen extends Bloque implements Dañable {

    private float timerRegen = 0f;
    private final float TIEMPO_REGEN = 25f;

    public BloqueRegen (int x, int y, int width, int height, String rutaTexturaRegen) {
        super(x, y, width, height, rutaTexturaRegen);
    }

    @Override
    public boolean debeEliminarse() {
        return false;
    }

    @Override
    public void daño() {
        destroyed = true;
        timerRegen = 0;
    }

    @Override
    public void comportamiento(float delta) {
        if (destroyed) {
            timerRegen += delta;
            if (timerRegen >= TIEMPO_REGEN) {
                destroyed = false;
            }
        }
    }
}
