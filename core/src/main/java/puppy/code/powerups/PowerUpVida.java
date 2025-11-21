package puppy.code.powerups;

import com.badlogic.gdx.graphics.Color;
import puppy.code.game.BlockBreakerGame;
import puppy.code.interfaces.ConCaida;

public class PowerUpVida extends PowerUp implements ConCaida {

    public PowerUpVida(int x, int y) {
        super(x, y, Color.RED);
    }
    float multiplicadorCaida = 0.5f;

    @Override
    public void iniciarCaida() {
        this.cayendo = true;
        this.velocidadCaida = VELOCIDAD_CAIDA_INICIAL * multiplicadorCaida;
    }

    @Override
    public void aplicarEfecto(BlockBreakerGame game) {
        game.addVida();
    }

}
