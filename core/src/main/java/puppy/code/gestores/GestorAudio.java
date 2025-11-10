package puppy.code.gestores;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class GestorAudio {

    private static GestorAudio instancia;

    private Music musicaFondo;
    private Sound sonidoGolpe;
    private Sound sonidoPowerUp;

    private GestorAudio() {
        musicaFondo = Gdx.audio.newMusic(Gdx.files.internal("audios/background-music.mp3"));
        sonidoGolpe = Gdx.audio.newSound(Gdx.files.internal("audios/hit.wav"));
        sonidoPowerUp = Gdx.audio.newSound(Gdx.files.internal("audios/power-up.wav"));
    }

    public static GestorAudio getInstancia() {
        if (instancia == null)
            instancia = new GestorAudio();
        return instancia;
    }

    public void reproducirMusicaDeFondo() {
        if (!musicaFondo.isPlaying()) {
            musicaFondo.setLooping(true);
            musicaFondo.play();
        }
    }

    public void reproducirGolpeBola() {
        sonidoGolpe.play(0.6f);
    }

    public void reproducirPowerUp() {
        sonidoPowerUp.play(0.8f);
    }

    public void dispose() {
        musicaFondo.dispose();
        sonidoGolpe.dispose();
        sonidoPowerUp.dispose();
    }
}
