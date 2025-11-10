package puppy.code.gestores;

import com.badlogic.gdx.Gdx;
import puppy.code.block.*;
import java.util.ArrayList;
import java.util.Random;

import static puppy.code.game.BlockBreakerGame.*;

public class GestorBloques {

    private ArrayList<Bloque> bloques = new ArrayList<>();
    private int nivelActual = 1;
    private boolean juegoCompletado = false;

    public ArrayList<Bloque> getBloques() {
        return bloques;
    }

    public int getNivelActual() {
        return nivelActual;
    }

    public boolean isJuegoCompletado() {
        return juegoCompletado;
    }

    public void generarBloques() {
        bloques.clear();

        if (nivelActual > 5) {
            juegoCompletado = true;
            return;
        }

        int filas = 2 + nivelActual;
        int blockWidth = 117;
        int blockHeight = 45;
        int espacio = 10;
        int margen = (ANCHO_VENTANA - (blockWidth * 8 + espacio * 7)) / 2;
        int y = ALTO_VENTANA;

        Random random = new Random();

        for (int cont = 0; cont < filas; cont++) {
            y -= blockHeight + 10;
            for (int x = margen; x < ANCHO_VENTANA - margen; x += blockWidth + espacio) {

                int chance = random.nextInt(100);
                int tipoBloque;

                if (chance < 10) {          // 10% Regen
                    tipoBloque = 1;
                } else if (chance < 30) {   // 20% Duro
                    tipoBloque = 0;
                } else if (chance < 40) {   // 10% Explosivo
                    tipoBloque = 2;
                } else {
                    tipoBloque = 3;
                }

                Bloque bloque = null;
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
                    case 3 :
                        bloque = new BloqueNormal(x, y, blockWidth, blockHeight, RUTA_BLOQUE_DEFAULT);
                        break;
                }

                bloques.add(bloque);
            }
        }
    }

    public void verificarProgreso() {
        boolean todosDestruidos = bloques.stream().noneMatch(Bloque::estaActivo);
        if (todosDestruidos) {
            nivelActual++;
            generarBloques();
        }
    }

    public void reiniciar() {
        nivelActual = 1;
        juegoCompletado = false;
        generarBloques();
    }
}
