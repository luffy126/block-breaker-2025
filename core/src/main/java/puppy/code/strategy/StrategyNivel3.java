package puppy.code.strategy;

import puppy.code.block.*;
import java.util.ArrayList;
import java.util.Random;
import static puppy.code.game.BlockBreakerGame.*;

public class StrategyNivel3 implements StrategyBloques{
    @Override
    public ArrayList<Bloque> generarBloques(int anchoVentana, int altoVentana, ArrayList<Bloque> bloquesExistentes) {
        ArrayList<Bloque> bloques = new ArrayList<>();

        int filas = 5;
        int columnas = 8;

        int blockWidth = 117;
        int blockHeight = 45;
        int espacio = 10;

        int margen = (anchoVentana - (blockWidth * columnas + espacio * (columnas - 1))) / 2;
        int y = altoVentana;

        for (int fila = 0; fila < filas; fila++) {
            y -= blockHeight + 10;

            for (int col = 0; col < columnas; col++) {
                int x = margen + col * (blockWidth + espacio);
                int selector = (fila + col) % 3;
                Bloque bloque;

                switch (selector) {
                    case 0:
                        bloque = new BloqueNormal(x, y, blockWidth, blockHeight, RUTA_BLOQUE_DEFAULT);
                        break;
                    case 1:
                        bloque = new BloqueDuro(x, y, blockWidth, blockHeight, RUTA_BLOQUE_DURO);
                        break;
                    case 2:
                    default:
                        bloque = new BloqueExplosion(x, y, blockWidth, blockHeight, RUTA_BLOQUE_EXPLOSIVO, bloques);
                        break;
                }
                bloques.add(bloque);
            }
        }
        return bloques;
    }
}
