package puppy.code.block;

import com.badlogic.gdx.graphics.Color;
import puppy.code.interfaces.Dañable;

import java.util.List;

public class BloqueExplosion extends Bloque implements Dañable {

    List<Bloque> listaBloques;
    boolean yaExploto = false;

    public BloqueExplosion (int x, int y, int width, int height, String rutaTexturaExp, List listaBloques) {
        super(x, y, width, height, rutaTexturaExp);
        this.listaBloques = listaBloques;
    }

    @Override
    public void daño() {
        destroyed = true;
    }

    @Override
    public void comportamiento(float delta) {
        if (destroyed && !yaExploto) {
            yaExploto = true;
            for (Bloque b : listaBloques) {
                if(!b.isDestroyed() && estaCerca(b)) {
                    b.setDestroyed(true);
                }
            }
        }
    }

    private boolean estaCerca(Bloque b) {
        int rangoExplosion = 80;
        int espacio = 10;

        // calcula el tamaño total ocupado por bloque + espacio
        int distanciaX = width + espacio;
        int distanciaY = height + espacio;

        // diferencia entre posiciones
        float dx = Math.abs(b.getX() - this.getX());
        float dy = Math.abs(b.getY() - this.getY());

        // es vecino directo o diagonal
        boolean mismoBloque = (b == this);
        boolean adyacenteX = dx <= distanciaX && dy <= distanciaY;

        return adyacenteX && !mismoBloque;
    }
}
