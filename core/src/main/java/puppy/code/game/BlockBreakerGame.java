package puppy.code.game;

import java.util.ArrayList;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import puppy.code.block.*;
import puppy.code.entities.*;
import puppy.code.gestores.GestorAudio;
import puppy.code.gestores.GestorBloques;
import puppy.code.gestores.GestorPowerUps;
import puppy.code.powerups.PowerUp;
import puppy.code.factories.*;

public class BlockBreakerGame extends ApplicationAdapter {
    public static final String RUTA_BLOQUE_DEFAULT = "bloques/default.png";
    public static final String RUTA_BLOQUE_DURO = "bloques/duro.png";
    public static final String RUTA_BLOQUE_REGEN = "bloques/regen.png";
    public static final String RUTA_BLOQUE_EXPLOSIVO = "bloques/explosivo.png";
    public static final int ANCHO_VENTANA = 1024;
    public static final int ALTO_VENTANA = 768;
    private OrthographicCamera camera;
    private Viewport viewport;
    private SpriteBatch batch;
    private BitmapFont font;
    private ShapeRenderer shape;
    private ArrayList<PingBall> balls = new ArrayList<>();
    private Paddle pad;
    private ArrayList<Bloque> blocks = new ArrayList<>();
    private int vidas;
    private int puntaje;
    private int nivel;
    private GestorPowerUps gestorPowerUps;
    // private ArrayList<PowerUp> powerUps = new ArrayList<>();
    private GestorAudio gestorAudio;
    private GestorBloques gestorBloques;
    private Texture fondo;
    private float fondoScrollX = 0f;
    private float fondoScrollY = 0f;
    private final float FONDO_VELOCIDAD = 1f; // píxeles por segundo

    @Override

    public void create () {
        gestorAudio = GestorAudio.getInstancia(); // sonido

        camera = new OrthographicCamera(); // camara

        viewport = new FitViewport(ANCHO_VENTANA, ALTO_VENTANA, camera);
        viewport.apply();

        batch = new SpriteBatch(); // texturas

        font = new BitmapFont(); // texto
        font.getData().setScale(3, 2);

        gestorBloques = new GestorBloques();
        gestorBloques.generarBloques();
        blocks = gestorBloques.getBloques();

        gestorAudio.reproducirMusicaDeFondo();

        gestorPowerUps = new GestorPowerUps();

        fondo = new Texture(Gdx.files.internal("fondos/bg_tile1.png"));
        fondo.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        shape = new ShapeRenderer();
        balls.add(new PingBall(ANCHO_VENTANA/2-10, 41, 10, 5, 7, true));
        pad = new Paddle(ANCHO_VENTANA/2-50,40,160,10);
        vidas = 3;
        puntaje = 0;
        nivel = gestorBloques.getNivelActual();
        camera.position.set(400, 240, 0);
        camera.update();
    }

    public void dibujaTextos() {
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        float left = viewport.getCamera().position.x - viewport.getWorldWidth() / 2f;
        float bottom = viewport.getCamera().position.y - viewport.getWorldHeight() / 2f;
        float right = left + viewport.getWorldWidth();

        batch.begin();
        font.draw(batch, "Puntos: " + puntaje, left + 10, bottom + 30);
        font.draw(batch, "Vidas : " + vidas, right - 200, bottom + 30);
        batch.end();
    }

    public void dibujaBloques() {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for (Bloque b : blocks) {
            if (!b.isDestroyed()) {
                for (PingBall bola : balls) {
                    bola.checkCollision(b);
                }
            }
            b.comportamiento(Gdx.graphics.getDeltaTime());
            b.draw(batch); // ahora con SpriteBatch
        }
        batch.end();
    }

    public void dibujaFondo() {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(
            fondo,
            0, 0,
            ANCHO_VENTANA, ALTO_VENTANA,
            fondoScrollX, fondoScrollY,
            fondoScrollX + ANCHO_VENTANA / fondo.getWidth(),
            fondoScrollY + ALTO_VENTANA / fondo.getHeight()
        );
        batch.end();
    }

    @Override
    public void render () {
        viewport.apply();
        camera.update();

        fondoScrollX += Gdx.graphics.getDeltaTime() * FONDO_VELOCIDAD;
        fondoScrollY += Gdx.graphics.getDeltaTime() * FONDO_VELOCIDAD * 0.5f;

        batch.setProjectionMatrix(camera.combined);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        dibujaFondo();

        shape.begin(ShapeRenderer.ShapeType.Filled);
        pad.draw(shape);

        for (PingBall b : balls) {
            if (b.estaQuieto()) {
                b.setXY(pad.getX() + pad.getWidth() / 2 - 5, pad.getY() + pad.getHeight() + 11);
                if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) b.setEstaQuieto(false);
            } else {
                b.update();
            }

            b.checkCollision(pad);
            b.draw(shape);
        }

        for (int i = 0; i < balls.size(); i++) {
            PingBall b = balls.get(i);
            if (b.getY() < 0) {
                if (balls.size() > 1) {
                    balls.remove(i);
                    i--;
                } else {
                    vidas--;
                    b.setXY(pad.getX() + pad.getWidth() / 2 - 5, pad.getY() + pad.getHeight() + 11);
                    b.setEstaQuieto(true);
                }
            }
        }

        if (vidas <= 0) {
            gestorBloques.reiniciar();
            reiniciarNivel();
            vidas = 3;
            puntaje = 0;
        }

        gestorBloques.verificarProgreso();
        blocks = gestorBloques.getBloques();

        if (gestorBloques.getNivelActual() > nivel) {
            nivel = gestorBloques.getNivelActual();
            reiniciarNivel();
        }

        if (gestorBloques.isJuegoCompletado()) {
            shape.end();
            dibujaFinDelJuego();
            return;
        }

        for (int i = 0; i < blocks.size(); i++) {
            Bloque b = blocks.get(i);
            if (b.debeEliminarse()) {
                puntaje++;
                gestorPowerUps.generarPowerUp(b.getX() + b.getWidth() / 2 - 10, b.getY());
                blocks.remove(b);
                i--;
            }
        }

        gestorPowerUps.actualizarPowerUps(this, pad);
        gestorPowerUps.dibujarPowerUps(shape);

        shape.end();
        dibujaTextos();
        dibujaBloques();
    }

    private void reiniciarNivel() {
        gestorPowerUps.limpiar();
        balls.clear();
        balls.add(new PingBall(ANCHO_VENTANA / 2-10, 41, 10, 5, 7, true));
        pad = new Paddle(ANCHO_VENTANA / 2 - 80, 40, 160, 10);
        blocks = gestorBloques.getBloques();
    }

    private void dibujaFinDelJuego() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        font.draw(batch, "Felicidades!", ANCHO_VENTANA / 2f - 120, ALTO_VENTANA / 2f + 80);
        font.draw(batch, "Has completado el juego.", ANCHO_VENTANA / 2f - 170, ALTO_VENTANA / 2f + 40);
        font.draw(batch, "Presiona R para reiniciar", ANCHO_VENTANA / 2f - 200, ALTO_VENTANA / 2f - 20);
        font.draw(batch, "o ESC para salir", ANCHO_VENTANA / 2f - 130, ALTO_VENTANA / 2f - 60);

        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            gestorBloques.reiniciar();
            reiniciarNivel();
            vidas = 3;
            puntaje = 0;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }

    public void addVida() {
        vidas++;
    }

    public void addPuntos(int puntos) {
        puntaje += puntos;
    }

    public Paddle getPaddle() {
        return pad;
    }

    public PingBall getPingBall() {
        return balls.get(0);
    }

    public ArrayList<PingBall> getBalls() {
        return balls;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {

    }
}
