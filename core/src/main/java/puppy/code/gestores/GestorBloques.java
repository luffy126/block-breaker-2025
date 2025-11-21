package puppy.code.gestores;

import com.badlogic.gdx.Gdx;
import puppy.code.block.*;
import java.util.ArrayList;
import java.util.Random;

import puppy.code.strategy.*;
import static puppy.code.game.BlockBreakerGame.*;

public class GestorBloques {

    private ArrayList<Bloque> bloques = new ArrayList<>();
    private int nivelActual = 1;
    private boolean juegoCompletado = false;

    private final StrategyBloques[] estrategias = {
        new StrategyNivel1(),
        new StrategyNivel2(),
        new StrategyNivel3(),
        new StrategyNivel4(),
        new StrategyNivel5()
    };

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
        bloques = estrategias[nivelActual - 1].generarBloques(ANCHO_VENTANA, ALTO_VENTANA, bloques);
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
