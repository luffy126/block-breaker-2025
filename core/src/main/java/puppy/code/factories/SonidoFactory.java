package puppy.code.factories;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.*;

public class SonidoFactory {
    Music musicaFondo = Gdx.audio.newMusic(Gdx.files.internal("audios/background-music.mp3"));
    Sound sonidoGolpe = Gdx.audio.newSound(Gdx.files.internal("audios/hit.wav"));
    Sound sonidoPowerUp = Gdx.audio.newSound(Gdx.files.internal("audios/power-up.wav"));

    public void reproducirMusicaDeFondo() {
        musicaFondo.setLooping(true);
        musicaFondo.setVolume(0.8f);
        musicaFondo.play();

    }

    public void reproducirGolpeBola(){
        sonidoGolpe.play();
    }
    public void reproducirPowerUp(){
        sonidoPowerUp.play();
    }

}
