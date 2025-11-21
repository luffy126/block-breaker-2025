package puppy.code.strategy;

import puppy.code.block.*;
import java.util.ArrayList;
import java.util.Random;
import static puppy.code.game.BlockBreakerGame.*;

public class StrategyNivel4 implements StrategyBloques{
    @Override
    public ArrayList<Bloque> generarBloques(int anchoVentana, int altoVentana, ArrayList<Bloque> bloquesExistentes) {

        ArrayList<Bloque> bloques = new ArrayList<>();

        int filas = 6;
        int columnas = 8;

        int blockWidth = 117;
        int blockHeight = 45;
        int espacio = 10;

        int margen = (anchoVentana - (blockWidth * columnas + espacio * (columnas - 1))) / 2;
        int y = altoVentana;

        // Patrón 6x8
        int[][] patron = {
            {0,0,1,2,2,1,0,0},
            {0,1,0,1,1,0,1,0},
            {1,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,1},
            {0,1,0,1,1,0,1,0},
            {0,0,1,2,2,1,0,0}
        };

        // 0 = normal
        // 1 = duro
        // 2 = explosivo

        for (int fila = 0; fila < filas; fila++) {
            y -= blockHeight + 10;
            for (int col = 0; col < columnas; col++) {
                int x = margen + col * (blockWidth + espacio);
                int tipo = patron[fila][col];

                Bloque bloque = null;

                switch (tipo) {
                    case 1:
                        bloque = new BloqueDuro(x, y, blockWidth, blockHeight, RUTA_BLOQUE_DURO);
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
