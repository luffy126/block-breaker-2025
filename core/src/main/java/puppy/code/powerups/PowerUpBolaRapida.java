package puppy.code.powerups;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Timer;
import puppy.code.game.BlockBreakerGame;

public class PowerUpBolaRapida extends PowerUp {
    private static final float MULTIPLICADOR_VELOCIDAD = 2.0f;
    private static final float DURACION_MINIMA = 10f;
    private static final float DURACION_MAXIMA = 15f;

    public PowerUpBolaRapida(int x, int y) {
        super(x, y, Color.YELLOW);
    }

    @Override
    public void aplicarEfecto(BlockBreakerGame game) {
        game.acelerarBola(MULTIPLICADOR_VELOCIDAD);

        float duracion = DURACION_MINIMA + (float)(Math.random() * (DURACION_MAXIMA - DURACION_MINIMA));

        Timer.schedule(new Timer.Task() {
            @Override
            public void run(){
                game.restaurarVelocidadBola();
            }
        }, duracion);
    }
}

