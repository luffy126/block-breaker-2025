package puppy.code.powerups;

import com.badlogic.gdx.graphics.Color;
import puppy.code.game.BlockBreakerGame;

public class PowerUpVida extends PowerUp {

    public PowerUpVida(int x, int y) {
        super(x, y, Color.RED);
    }

    @Override
    public void aplicarEfecto(BlockBreakerGame game) {
        game.addVida();
    }
}
