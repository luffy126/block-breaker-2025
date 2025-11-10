package puppy.code.gestores;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.Gdx;
import puppy.code.entities.Paddle;
import puppy.code.powerups.PowerUp;
import puppy.code.factories.PowerUpFactory;
import puppy.code.game.BlockBreakerGame;

import java.util.ArrayList;

public class GestorPowerUps {

    private final ArrayList<PowerUp> powerUps = new ArrayList<>();
    private final GestorAudio gestorAudio;

    public GestorPowerUps() {
        gestorAudio = GestorAudio.getInstancia();
    }

    public void generarPowerUp(int x, int y) {
        PowerUp powerUp = PowerUpFactory.intentarGenerar(x, y);
        if (powerUp != null) {
            powerUp.iniciarCaida();
            powerUps.add(powerUp);
        }
    }

    public void actualizarPowerUps(BlockBreakerGame game, Paddle pad) {
        for (int i = 0; i < powerUps.size(); i++) {
            PowerUp p = powerUps.get(i);
            p.actualizarCaida(Gdx.graphics.getDeltaTime());

            if (p.colisionaCon(pad.getX(), pad.getY(), pad.getWidth(), pad.getHeight())) {
                p.aplicarEfecto(game);
                gestorAudio.reproducirPowerUp();
                powerUps.remove(i);
                i--;
                continue;
            }

            if (p.escapoDeLaPantalla()) {
                powerUps.remove(i);
                i--;
            }
        }
    }

    public void dibujarPowerUps(ShapeRenderer shape) {
        for (PowerUp p : powerUps) {
            p.draw(shape);
        }
    }

    public void limpiar() {
        powerUps.clear();
    }
}
