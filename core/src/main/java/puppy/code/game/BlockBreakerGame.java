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
import puppy.code.powerups.PowerUp;
import puppy.code.factories.*;

public class BlockBreakerGame extends ApplicationAdapter {
    static final String RUTA_BLOQUE_DEFAULT = "bloques/default.png";
    static final String RUTA_BLOQUE_DURO = "bloques/duro.png";
    static final String RUTA_BLOQUE_REGEN = "bloques/regen.png";
    public static final int ANCHO_VENTANA = 1024;
    public static final int ALTO_VENTANA = 768;
    private OrthographicCamera camera;
    private Viewport viewport;
    private SpriteBatch batch;
    private BitmapFont font;
    private ShapeRenderer shape;
    private PingBall ball;
    private Paddle pad;
    private ArrayList<Bloque> blocks = new ArrayList<>();
    private int vidas;
    private int puntaje;
    private int nivel;
    private ArrayList<PowerUp> powerUps = new ArrayList<>();
    private SonidoFactory gestorAudio;
    private Texture fondo;
    private float fondoScrollX = 0f;
    private float fondoScrollY = 0f;
    private final float FONDO_VELOCIDAD = 1f; // píxeles por segundo

    @Override

    public void create () {
        gestorAudio = new SonidoFactory(); // sonido

        camera = new OrthographicCamera(); // camara

        viewport = new FitViewport(ANCHO_VENTANA, ALTO_VENTANA, camera);
        viewport.apply();

        batch = new SpriteBatch(); // texturas

        font = new BitmapFont(); // texto
        font.getData().setScale(3, 2);

        nivel = 1; // nivel

        crearBloques(2+nivel); // bloques

        gestorAudio.reproducirMusicaDeFondo();

        fondo = new Texture(Gdx.files.internal("fondos/bg_tile1.png"));
        fondo.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        shape = new ShapeRenderer();
        ball = new PingBall(ANCHO_VENTANA/2-10, 41, 10, 5, 7, true);
        pad = new Paddle(ANCHO_VENTANA/2-50,40,160,10);
        vidas = 3;
        puntaje = 0;
        camera.position.set(400, 240, 0);
        camera.update();
    }

    public void crearBloques(int filas) {
        blocks.clear();
        int blockWidth = 117;
        int blockHeight = 45;
        int espacio = 10;
        int margen = (ANCHO_VENTANA - (blockWidth * 8 + espacio * 7)) / 2;
        int y = ALTO_VENTANA;
        int tipoBloque;
        java.util.Random random = new java.util.Random();

        for (int cont = 0; cont < filas; cont++) {
            y -= blockHeight + 10;
            for (int x = margen; x < ANCHO_VENTANA - margen; x += blockWidth + espacio) {

                int chance = random.nextInt(100); // valor entre 0 y 99

                if (chance < 10) {          // 10% → Regen
                    tipoBloque = 1;
                } else if (chance < 40) {   // 30% → Duro
                    tipoBloque = 0;
                } else {                    // 60% → Normal
                    tipoBloque = 2;
                }

                Bloque bloque;

                switch (tipoBloque) {
                    case 0:
                        bloque = new BloqueDuro(x, y, blockWidth, blockHeight, RUTA_BLOQUE_DURO);
                        break;
                    case 1:
                        bloque = new BloqueRegen(x, y, blockWidth, blockHeight, RUTA_BLOQUE_REGEN);
                        break;
                    default:
                        bloque = new BloqueNormal(x, y, blockWidth, blockHeight, RUTA_BLOQUE_DEFAULT);
                        break;
                }
                blocks.add(bloque);
            }
        }
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
                ball.checkCollision(b);
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

        if (ball.estaQuieto()) {
            ball.setXY(pad.getX() + pad.getWidth() / 2 - 5, pad.getY() + pad.getHeight() + 11);
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) ball.setEstaQuieto(false);
        } else {
            ball.update();
        }

        if (ball.getY() < 0) {
            vidas--;
            ball = new PingBall(pad.getX() + pad.getWidth() / 2 - 5, pad.getY() + pad.getHeight() + 11, 10, 5, 7, true);
        }

        if (vidas <= 0) {
            vidas = 3;
            nivel = 1;
            crearBloques(2 + nivel);
            powerUps.clear();
        }

        boolean todosDestruidos = blocks.stream().noneMatch(Bloque::estaActivo);
        if (todosDestruidos) {
            nivel++;
            crearBloques(2 + nivel);
            powerUps.clear();
            ball = new PingBall(pad.getX() + pad.getWidth() / 2 - 5, pad.getY() + pad.getHeight() + 11, 10, 5, 7, true);
        }

        /* for (Bloque b : blocks) {
            if (!b.isDestroyed()) {
                ball.checkCollision(b);
            }
            b.comportamiento(Gdx.graphics.getDeltaTime());
            b.draw(shape);
        } */

        for (int i = 0; i < blocks.size(); i++) {
            Bloque b = blocks.get(i);
            if (b.debeEliminarse()) {
                puntaje++;
                PowerUp powerUp = PowerUpFactory.intentarGenerar(
                    b.getX() + b.getWidth() / 2 - 10,
                    b.getY()
                );
                if (powerUp != null) {
                    powerUp.iniciarCaida();
                    powerUps.add(powerUp);
                }

                blocks.remove(b);
                i--;
            }
        }

        for (int i = 0; i < powerUps.size(); i++) {
            PowerUp p = powerUps.get(i);
            p.actualizarCaida(Gdx.graphics.getDeltaTime());

            if (p.colisionaCon(pad.getX(), pad.getY(), pad.getWidth(), pad.getHeight())) {
                p.aplicarEfecto(this);
                gestorAudio.reproducirPowerUp();
                powerUps.remove(i);
                i--;
                continue;
            }

            if (p.escapoDeLaPantalla()) {
                powerUps.remove(i);
                i--;
                continue;
            }

            p.draw(shape);
        }

        ball.checkCollision(pad);
        ball.draw(shape);
        shape.end();
        dibujaTextos();
        dibujaBloques();
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
        return ball;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {

    }
}
