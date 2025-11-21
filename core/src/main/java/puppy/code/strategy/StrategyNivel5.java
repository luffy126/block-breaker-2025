package puppy.code.strategy;

import puppy.code.block.*;
import java.util.ArrayList;
import java.util.Random;
import static puppy.code.game.BlockBreakerGame.*;

public class StrategyNivel5 implements StrategyBloques{
    @Override
    public ArrayList<Bloque> generarBloques(int anchoVentana, int altoVentana, ArrayList<Bloque> bloquesExistentes) {
        ArrayList<Bloque> bloques = new ArrayList<>();

        int blockWidth = 117;
        int blockHeight = 45;
        int espacio = 10;

        int columnas = 8;
        int filas = 8;

        int margen = (anchoVentana - (blockWidth * columnas + espacio * (columnas - 1))) / 2;
        int y = altoVentana;

        int[][] patron = {
            // Fila 1
            {1,0,2,3,3,2,0,1},
            {1,0,2,3,3,2,0,1},

            // fila 3 y 4 (tDNRRNDt)
            {-1,1,0,2,2,0,1,-1},
            {-1,1,0,2,2,0,1,-1},

            // fila 5 y 6 (ttDNNDtt)
            {-1,-1,1,0,0,1,-1,-1},
            {-1,-1,1,0,0,1,-1,-1},

            // fila 7 y 8 (tttDDttt)
            {-1,-1,-1,1,1,-1,-1,-1},
            {-1,-1,-1,1,1,-1,-1,-1}
        };

        for (int fila = 0; fila < filas; fila++) {
            y -= blockHeight + 10;

            for (int col = 0; col < columnas; col++) {
                int tipo = patron[fila][col];
                if (tipo == -1) continue;
                int x = margen + col * (blockWidth + espacio);
                Bloque bloque;

                switch (tipo) {
                    case 1:
                        bloque = new BloqueDuro(x, y, blockWidth, blockHeight, RUTA_BLOQUE_DURO);
                        break;
                    case 2:
                        bloque = new BloqueRegen(x, y, blockWidth, blockHeight, RUTA_BLOQUE_REGEN);
                        break;
                    case 3:
                        bloque = new BloqueExplosion(x, y, blockWidth, blockHeight, RUTA_BLOQUE_EXPLOSIVO, bloques);
                        break;
                    default: // N = normal
                        bloque = new BloqueNormal(x, y, blockWidth, blockHeight, RUTA_BLOQUE_DEFAULT);
                        break;
                }
                bloques.add(bloque);
            }
        }

        return bloques;
    }
}
