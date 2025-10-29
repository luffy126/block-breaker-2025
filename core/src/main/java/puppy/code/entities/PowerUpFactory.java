
package puppy.code.entities;

import java.util.Random;

public class PowerUpFactory {
    private static final Random random = new Random();
    private static final float PROBABILIDAD_POWERUP = 0.50f;

    public static PowerUp intentarGenerar(int x, int y) {
        if (random.nextFloat() >= PROBABILIDAD_POWERUP) {
            return null;
        }

        int tipo = random.nextInt(4);

        switch (tipo) {
            case 0:
                return new PowerUpVida(x, y);
            case 1:
                return new PowerUpPaddleGrande(x, y);
            case 2:
                return new PowerUpBolaRapida(x, y);
            case 3:
                return new PowerUpMultibola(x, y);
            default:
                return null;
        }
    }
}
