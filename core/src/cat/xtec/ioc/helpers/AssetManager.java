package cat.xtec.ioc.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetManager {

    // Sprite Sheet
    public static Texture sheet;

    // CAMBIO DE VARIABLES SEGÚN EL ENUNCIADO
    // Nau i fons

    // addhiriendo los valores para que el assetmanager ejecute el ataque 'FIRE'
    public static TextureRegion santa, santaDown, santaUp, background, fire, fireButton;

    // Asteroid
    public static TextureRegion[] zombies;
    public static Animation zombie;

    // Explosió
    public static TextureRegion[] explosion;
    public static Animation explosionAnim;

    // Sons
    public static Sound explosionSound;
    public static Music music;


    // Font
    public static BitmapFont font;




    public static void load() {
        // Carreguem les textures i li apliquem el mètode d'escalat 'nearest'
        //sheet = new Texture(Gdx.files.internal("sheet.png"));
        sheet = new Texture(Gdx.files.internal("image_sheet.png"));
        sheet.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        // Sprites de la nau
        //spacecraft = new TextureRegion(sheet, 0, 0, 36, 15);
        santa = new TextureRegion(sheet, 0, 566, 113, 120); // -- ok
        santa.flip(false, true);

        //spacecraftUp = new TextureRegion(sheet, 36, 0, 36, 15);
        santaUp = new TextureRegion(sheet, 217, 566, 180, 180); // -- ok
        santaUp.flip(false, true);

        //spacecraftDown = new TextureRegion(sheet, 72, 0, 36, 15);
        santaDown = new TextureRegion(sheet, 397, 566, 180, 180); // ok
        santaDown.flip(false, true);


        // BOLA DE ZOMBIES
        fire = new TextureRegion(sheet, 768, 0, 180, 180);
        fire.flip(false, true);

        // Carreguem els 16 estats de l'asteroid
        //asteroid = new TextureRegion[16];
        zombies = new TextureRegion[4];
        for (int i = 0; i < zombies.length; i++) {

            zombies[i] = new TextureRegion(sheet, i * 577, 566, 300, 300);

        }



        // Creem l'animació de l'asteroid i fem que s'executi contínuament en sentit anti-horari
        zombie = new Animation(0.08f, zombies);
        zombie.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        // Creem els 16 estats de l'explosió
        //explosion = new TextureRegion[4];
        explosion = new TextureRegion[16];

        // Carreguem els 16 estats de l'explosió
        int index = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 8; j++) {
                //explosion[index++] = new TextureRegion(sheet, j * 64,  i * 64 + 49, 64, 64);
                explosion[index++] = new TextureRegion(sheet, j * 768,  i * 35 + 40, 150, 150);
            }
        }

        // Finalment creem l'animació
        explosionAnim = new Animation(0.04f, explosion);

        // Fons de pantalla
        //background = new TextureRegion(sheet1, 0, 177, 480, 135);
        background = new TextureRegion(sheet, 0, 0, 768, 566);
        background.flip(false, true);  // da la vuelta al fondo

        /******************************* Sounds *************************************/
        // Explosió
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));

        // Música del joc
        //music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Afterburner.ogg"));  --> original
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/brain.ogg"));
        music.setVolume(0.2f);
        music.setLooping(true);



        /******************************* Text *************************************/
        // Font space
        FileHandle fontFile = Gdx.files.internal("fonts/space.fnt");
        font = new BitmapFont(fontFile, true);
        font.getData().setScale(0.4f);


    }



    public static void dispose() {

        // Alliberem els recursos gràfics i de audio
        sheet.dispose();
        explosionSound.dispose();
        music.dispose();


    }
}
