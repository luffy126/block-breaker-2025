package puppy.code.entities;

import com.badlogic.gdx.graphics.Color;
import puppy.code.game.BlockBreakerGame;

public class PowerUpMultibola extends PowerUp {

    public PowerUpMultibola(int x, int y) {
        super(x, y, Color.PURPLE);
    }

    @Override
    public void aplicarEfecto(BlockBreakerGame game) {
        game.addPuntos(10); // Esto esta tentativamente aqui, despues cambiar por verdaderas bolas extras, y que las nuevas, no cuenten para perder vida al escapar
    }
}
