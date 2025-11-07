package puppy.code.powerups;

import com.badlogic.gdx.graphics.Color;
import puppy.code.entities.PingBall;
import puppy.code.game.BlockBreakerGame;
import puppy.code.interfaces.ConCaida;

public class PowerUpMultibola extends PowerUp implements ConCaida {

    public PowerUpMultibola(int x, int y) {
        super(x, y, Color.PURPLE);
    }

    @Override
    public void aplicarEfecto(BlockBreakerGame game) {
        PingBall original = game.getBalls().get(0);
        PingBall extra1 = new PingBall((int) original.getX(), (int) original.getY(), 10, 5, 7, false);
        PingBall extra2 = new PingBall((int) original.getX(), (int) original.getY(), 10, -5, 7, false);

        game.getBalls().add(extra1);
        game.getBalls().add(extra2);
    }

}
