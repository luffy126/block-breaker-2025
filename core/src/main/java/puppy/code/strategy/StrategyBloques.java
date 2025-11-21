package puppy.code.strategy;

import puppy.code.block.Bloque;
import java.util.ArrayList;

public interface StrategyBloques {
    ArrayList<Bloque> generarBloques(int anchoVentana, int altoVentana, ArrayList<Bloque> bloquesExistentes);
}
