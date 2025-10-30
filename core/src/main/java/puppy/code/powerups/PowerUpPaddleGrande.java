package puppy.code.powerups;

import com.badlogic.gdx.graphics.Color;
import puppy.code.game.BlockBreakerGame;
import puppy.code.interfaces.ConCaida;

public class PowerUpPaddleGrande extends PowerUp implements ConCaida {

    public PowerUpPaddleGrande(int x, int y) {
        super(x, y, Color.BLUE);
    }

    @Override
    public void aplicarEfecto(BlockBreakerGame game) {
        game.getPaddle().agrandar(20);
    }
}
