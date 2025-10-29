package puppy.code.entities;

import com.badlogic.gdx.graphics.Color;
import puppy.code.game.BlockBreakerGame;

public class PowerUpPaddleGrande extends PowerUp {

    public PowerUpPaddleGrande(int x, int y) {
        super(x, y, Color.BLUE);
    }

    @Override
    public void aplicarEfecto(BlockBreakerGame game) {
        game.agrandarPaddle(20);
    }
}
