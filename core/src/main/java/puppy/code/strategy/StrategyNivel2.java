package puppy.code.strategy;

import puppy.code.block.*;
import java.util.ArrayList;
import java.util.Random;
import static puppy.code.game.BlockBreakerGame.*;

public class StrategyNivel2 implements StrategyBloques {

    @Override
    public ArrayList<Bloque> generarBloques(int anchoVentana, int altoVentana, ArrayList<Bloque> bloquesExistentes) {
        ArrayList<Bloque> bloques = new ArrayList<>();

        int filas = 4;
        int blockWidth = 117;
        int blockHeight = 45;
        int espacio = 10;

        int margen = (anchoVentana - (blockWidth * 8 + espacio * 7)) / 2;
        int y = altoVentana;

        Random random = new Random();

        for (int fila = 0; fila < filas; fila++) {
            y -= blockHeight + 10;
            for (int x = margen; x < anchoVentana - margen; x += blockWidth + espacio) {
                Bloque bloque;

                switch (fila) {
                    case 0:
                        // fila normales
                        bloque = new BloqueNormal(x, y, blockWidth, blockHeight, RUTA_BLOQUE_DEFAULT);
                        break;
                    case 1:
                        // fila duros
                        bloque = new BloqueDuro(x, y, blockWidth, blockHeight, RUTA_BLOQUE_DURO);
                        break;
                    case 2:
                        // mezcla normal y explosivos
                        int chance = random.nextInt(100);
                        if (chance < 20) { // 20% explosivo
                            bloque = new BloqueExplosion(x, y, blockWidth, blockHeight, RUTA_BLOQUE_EXPLOSIVO, bloques);
                        } else {
                            bloque = new BloqueNormal(x, y, blockWidth, blockHeight, RUTA_BLOQUE_DEFAULT);
                        }
                        break;
                    case 3: // fila duros
                        bloque = new BloqueDuro(x, y, blockWidth, blockHeight, RUTA_BLOQUE_DURO);
                        break;
                    default:
                        bloque = new BloqueNormal(x, y, blockWidth, blockHeight, RUTA_BLOQUE_DEFAULT);
                }

                bloques.add(bloque);
            }
        }

        return bloques;
    }
}
