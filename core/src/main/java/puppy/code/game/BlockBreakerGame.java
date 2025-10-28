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
import puppy.code.block.Bloque;
import puppy.code.block.BloqueDuro;
import puppy.code.block.BloqueNormal;
import puppy.code.block.BloqueRegen;
import puppy.code.entities.Paddle;
import puppy.code.entities.PingBall;
import puppy.code.entities.PowerUp;


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

                        case 1: bloque = new BloqueRegen(x, y, blockWidth, blockHeight);
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
			//actualizar matrices de la cámara
			camera.update();
			//actualizar
			batch.setProjectionMatrix(camera.combined);
			batch.begin();
			//dibujar textos
			font.draw(batch, "Puntos: " + puntaje, 10, 25);
			font.draw(batch, "Vidas : " + vidas, Gdx.graphics.getWidth()-20, 25);
			batch.end();
		}

		@Override
		public void render () {
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	        shape.begin(ShapeRenderer.ShapeType.Filled);
	        pad.draw(shape);
	        // monitorear inicio del juego
	        if (ball.estaQuieto()) {
	        	ball.setXY(pad.getX()+pad.getWidth()/2-5, pad.getY()+pad.getHeight()+11);
	        	if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) ball.setEstaQuieto(false);
	        }else ball.update();
	        //verificar si se fue la bola x abajo
	        if (ball.getY()<0) {
	        	vidas--;
	        	//nivel = 1;
	        	ball = new PingBall(pad.getX()+pad.getWidth()/2-5, pad.getY()+pad.getHeight()+11, 10, 5, 7, true);
	        }



	        // verificar game over
	        if (vidas<=0) {
	        	vidas = 3;
	        	nivel = 1;
	        	crearBloques(2+nivel);
                powerUps.clear();
	        	//ball = new PingBall(pad.getX()+pad.getWidth()/2-5, pad.getY()+pad.getHeight()+11, 10, 5, 7, true);
	        }
	        // verificar si el nivel se terminó
	        if (blocks.size()==0) {
	        	nivel++;
	        	crearBloques(2+nivel);
                powerUps.clear();
	        	ball = new PingBall(pad.getX()+pad.getWidth()/2-5, pad.getY()+pad.getHeight()+11, 10, 5, 7, true);
	        }
	        //dibujar bloques
	        for (Bloque b : blocks) {
                if (!b.isDestroyed()) {
                    ball.checkCollision(b);
                }
                b.comportamiento(Gdx.graphics.getDeltaTime());
                b.draw(shape);
	        }
	        // actualizar estado de los bloques
	        for (int i = 0; i < blocks.size(); i++) {
	            Bloque b = blocks.get(i);
	            if (b.debeEliminarse()) {
	            	puntaje++;
	                blocks.remove(b);
	                i--; //para no saltarse 1 tras eliminar del arraylist
	            }
	        }

	        ball.checkCollision(pad);
	        ball.draw(shape);
	        shape.end();
	        dibujaTextos();
		}

    private void aplicarEfectoPowerUp(PowerUp.TipoPowerUp tipo) {
        switch (tipo) {
            case VIDA_EXTRA:
                vidas++;
                break;
            case PADDLE_GRANDE:
                pad.setWidth(pad.getWidth() + 20);
                break;
            case BOLA_RAPIDA:
                ball.setXSpeed(ball.getXSpeed() * 1.5f);
                ball.setYSpeed(ball.getYSpeed() * 1.5f);
                break;
            case MULTI_BOLA:
                puntaje += 10;
                break;
        }
    }



		public void dispose () {

		}
	}
