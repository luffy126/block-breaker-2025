package puppy.code.powerups;

import com.badlogic.gdx.graphics.Color;
import puppy.code.game.BlockBreakerGame;

public class PowerUpBolaRapida extends PowerUp {

    private float ACELERADOR_BOLA = 1.35f;
    public PowerUpBolaRapida(int x, int y) {
        super(x, y, Color.YELLOW);
    }

    @Override
    public void aplicarEfecto(BlockBreakerGame game) {
        game.acelerarBola(ACELERADOR_BOLA);
    }
}
