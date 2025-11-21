package puppy.code.powerups;

import com.badlogic.gdx.graphics.Color;
import puppy.code.game.BlockBreakerGame;
import puppy.code.interfaces.ConCaida;

public class PowerUpBolaRapida extends PowerUp implements ConCaida {

    private float ACELERADOR_BOLA = 1.35f;
    public PowerUpBolaRapida(int x, int y) {
        super(x, y, Color.YELLOW);
    }

    @Override
    public void iniciarCaida() {
        this.cayendo = true;
        this.velocidadCaida = VELOCIDAD_CAIDA_INICIAL * 20;
    }

    @Override
    public void aplicarEfecto(BlockBreakerGame game) {
        game.getPingBall().acelerar(ACELERADOR_BOLA);
    }
}
