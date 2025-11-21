package puppy.code.strategy;

import puppy.code.block.*;
import java.util.ArrayList;
import java.util.Random;
import static puppy.code.game.BlockBreakerGame.*;

public class StrategyNivel1 implements StrategyBloques {

    @Override
    public ArrayList<Bloque> generarBloques(int anchoVentana, int altoVentana, ArrayList<Bloque> bloquesExistentes) {
        ArrayList<Bloque> bloques = new ArrayList<>();

        int filas = 2 + 1;
        int blockWidth = 117;
        int blockHeight = 45;
        int espacio = 10;
        int margen = (anchoVentana - (blockWidth * 8 + espacio * 7)) / 2;
        int y = altoVentana;

        Random random = new Random();

        for (int cont = 0; cont < filas; cont++) {
            y -= blockHeight + 10;
            for (int x = margen; x < anchoVentana - margen; x += blockWidth + espacio) {

                int chance = random.nextInt(100);
                int tipoBloque;
                if (chance < 10) tipoBloque = 1;       // Regen
                else if (chance < 30) tipoBloque = 0;  // Duro
                else if (chance < 40) tipoBloque = 2;  // Explosivo
                else tipoBloque = 3;                   // Normal

                Bloque bloque;

                switch (tipoBloque) {
                    case 0:
                        bloque = new BloqueDuro(x, y, blockWidth, blockHeight, RUTA_BLOQUE_DURO);
                        break;
                    case 1:
                        bloque = new BloqueRegen(x, y, blockWidth, blockHeight, RUTA_BLOQUE_REGEN);
                        break;
                    case 2:
                        bloque = new BloqueExplosion(x, y, blockWidth, blockHeight, RUTA_BLOQUE_EXPLOSIVO, bloques);
                        break;
                    default:
                        bloque = new BloqueNormal(x, y, blockWidth, blockHeight, RUTA_BLOQUE_DEFAULT);
                        break;
                }
                bloques.add(bloque);
            }
        }

        return bloques;
    }
}
