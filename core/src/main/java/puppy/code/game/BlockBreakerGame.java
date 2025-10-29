package puppy.code.game;

import java.util.ArrayList;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import puppy.code.block.*;
import puppy.code.entities.*;

public class BlockBreakerGame extends ApplicationAdapter {
    private OrthographicCamera camera;
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

    @Override
    public void create () {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().setScale(3, 2);
        nivel = 1;
        crearBloques(2+nivel);

        shape = new ShapeRenderer();
        ball = new PingBall(Gdx.graphics.getWidth()/2-10, 41, 10, 5, 7, true);
        pad = new Paddle(Gdx.graphics.getWidth()/2-50,40,100,10);
        vidas = 3;
        puntaje = 0;
    }

    public void crearBloques(int filas) {
        blocks.clear();
        int blockWidth = 70;
        int blockHeight = 26;
        int y = Gdx.graphics.getHeight();

        java.util.Random random = new java.util.Random();

        for (int cont = 0; cont < filas; cont++) {
            y -= blockHeight + 10;

            for (int x = 5; x < Gdx.graphics.getWidth(); x += blockWidth + 10) {
                int tipoBloque = random.nextInt(3);
                Bloque bloque;

                switch (tipoBloque) {
                    case 0:
                        bloque = new BloqueDuro(x, y, blockWidth, blockHeight);
                        break;

                    case 1:
                        bloque = new BloqueRegen(x, y, blockWidth, blockHeight);
                        break;

                    default:
                        bloque = new BloqueNormal(x, y, blockWidth, blockHeight);
                        break;
                }
                blocks.add(bloque);
            }
        }
    }

    public void dibujaTextos() {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        font.draw(batch, "Puntos: " + puntaje, 10, 25);
        font.draw(batch, "Vidas : " + vidas, Gdx.graphics.getWidth()-20, 25);
        batch.end();
    }

    @Override
    public void render () {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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

        if (blocks.size() == 0) {
            nivel++;
            crearBloques(2 + nivel);
            powerUps.clear();
            ball = new PingBall(pad.getX() + pad.getWidth() / 2 - 5, pad.getY() + pad.getHeight() + 11, 10, 5, 7, true);
        }

        for (Bloque b : blocks) {
            if (!b.isDestroyed()) {
                ball.checkCollision(b);
            }
            b.comportamiento(Gdx.graphics.getDeltaTime());
            b.draw(shape);
        }

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
    }

    public void addVida() {
        vidas++;
    }

    public void agrandarPaddle(int cantidad) {
        pad.setWidth(pad.getWidth() + cantidad);
    }

    public void acelerarBola(float multiplicador) {
        ball.setXSpeed(ball.getXSpeed() * multiplicador);
        ball.setYSpeed(ball.getYSpeed() * multiplicador);
    }

    public void addPuntos(int puntos) {
        puntaje += puntos;
    }

    @Override
    public void dispose() {

    }
}
