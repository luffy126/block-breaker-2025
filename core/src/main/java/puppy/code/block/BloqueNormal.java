package puppy.code.block;

import com.badlogic.gdx.graphics.Color;
import puppy.code.interfaces.Dañable;

public class BloqueNormal extends Bloque implements Dañable {

    public BloqueNormal(int x, int y, int width, int height) {
        super(x, y, width, height, Color.CYAN);
    }

    @Override
    public void daño(){
        destroyed = true;
    }

    @Override
    public void comportamiento(float delta){
        // pov no haces nada solo eres un bloque sin ningun comportamiento especial... que loco no?
    }
}
