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
    public static Texture coins;
    public static Texture paused;

    // CAMBIO DE VARIABLES SEGÚN EL ENUNCIADO

    // addhiriendo los valores para que el assetmanager ejecute el ataque 'FIRE'
    public static TextureRegion santaD, santaU, background, fire, pause;

    // Zombie
    public static TextureRegion[] zombies, bonus1, bonus2, santa;
    public static Animation zombie, bonu1, bonu2, santaRun, santaUp, santaDown;

    // Explosió
    public static TextureRegion[] explosion;
    public static Animation explosionAnim;

    // Sons
    public static Sound explosionSound, bonusSound;
    public static Music music;


    // Font
    public static BitmapFont font;




    public static void load() {
        // Carreguem les textures i li apliquem el mètode d'escalat 'nearest'
        //sheet = new Texture(Gdx.files.internal("sheet.png"));
        sheet = new Texture(Gdx.files.internal("image_sheet.png"));
        sheet.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        coins = new Texture(Gdx.files.internal("sheet.png"));
        coins.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        paused = new Texture(Gdx.files.internal("paused.png"));
        paused.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        santaU = new TextureRegion(paused, 604, 604,  200, 200); // -- ok
        santaU.flip(false, true);

        santaD = new TextureRegion(paused, 2, 204, 200, 200); // ok
        santaD.flip(false, true);

        // ADICIÓN DEL PAUSE
        pause = new TextureRegion(paused, 2, 406, 600, 600);
        pause.flip(false, true);
        //****************  ejercicio 2 fire

        // BOLA DE ZOMBIES
        fire = new TextureRegion(sheet, 1285, 1298, 35, 32);
        fire.flip(false, true);


         //********************************************************************

            // FIRE
         //********************************************************************


        santa = new TextureRegion[6];

        santa[0] = new TextureRegion(paused, 204, 2, 200, 200);
        santa[1] = new TextureRegion(paused, 204, 204, 200, 200);
        santa[2] = new TextureRegion(paused, 604, 806, 200, 200);
        santa[3] = new TextureRegion(paused, 806, 806, 200, 200);
        santa[4] = new TextureRegion(paused, 2, 2, 200, 200);
        santa[5] = new TextureRegion(paused, 806, 604, 200, 200);

        for (int i = 0; i < santa.length; i++){
            santa[i].flip(false,true);
        }

        santaRun = new Animation(0.4f, santa);
        santaRun.setPlayMode(Animation.PlayMode.LOOP);

        santaUp = new Animation(0.2f, santaU);
        santaUp.setPlayMode(Animation.PlayMode.LOOP);

        santaDown = new Animation(0.2f, santaD);
        santaDown.setPlayMode(Animation.PlayMode.LOOP);

        // Carreguem els 16 estats de l'asteroid
        //asteroid = new TextureRegion[16];
        zombies = new TextureRegion[8];

        zombies[0] = new TextureRegion(sheet, 2, 187, 272, 283);
        zombies[1] = new TextureRegion(sheet, 2, 472, 273, 290);
        zombies[2] = new TextureRegion(sheet, 277, 470, 256, 292);
        zombies[3] = new TextureRegion(sheet, 1030, 738, 234, 292);
        zombies[4] = new TextureRegion(sheet, 1026, 147, 221, 293);
        zombies[5] = new TextureRegion(sheet, 785, 475, 199, 298);
        zombies[6] = new TextureRegion(sheet, 791, 176, 233, 297);
        zombies[7] = new TextureRegion(sheet, 1026, 442, 231, 294);

        for(int i = 0; i < zombies.length; i++){
            zombies[i].flip(false, true);
        }


        //****************************************************

                // ARRAY DE MONEDAS PARA EL BONUS
        //****************************************************
        bonus1 = new TextureRegion[7];
        bonus2 = new TextureRegion[7];

        bonus1[0] = new TextureRegion(coins, 2, 486, 300, 300);
        bonus1[1] = new TextureRegion(coins, 304, 486, 300, 300);
        bonus1[2] = new TextureRegion(coins, 1074, 1356, 300, 300);
        bonus1[3] = new TextureRegion(coins, 606, 752, 300, 300);
        bonus1[4] = new TextureRegion(coins,  908, 752, 300, 300);
        bonus1[5] = new TextureRegion(coins, 1512, 1054, 300, 300);
        bonus1[6] = new TextureRegion(coins, 2, 486, 300, 300);


        bonus2[0] = new TextureRegion(coins, 2, 184, 300, 300);
        bonus2[1] = new TextureRegion(coins, 1512, 450, 300, 300);
        bonus2[2] = new TextureRegion(coins, 606, 1054, 300, 300);
        bonus2[3] = new TextureRegion(coins, 2, 1090, 300, 300);
        bonus2[4] = new TextureRegion(coins, 908, 450, 300, 300);
        bonus2[5] = new TextureRegion(coins, 1678, 1658, 300, 300);
        bonus2[6] = new TextureRegion(coins, 908, 148, 300, 300);


        for(int i = 0; i < bonus1.length; i++){
            bonus1[i].flip(false, true);
            bonus2[i].flip(false, true);
        }

        bonu1 = new Animation(0.4f, bonus1);
        bonu1.setPlayMode(Animation.PlayMode.LOOP);

        bonu2 = new Animation(0.4f, bonus2);
        bonu2.setPlayMode(Animation.PlayMode.LOOP);

        // Creem l'animació de l'asteroid i fem que s'executi contínuament en sentit anti-horari

       /* for(int i = 0; i < zombies.length; i++){
            zombie = new Animation(0.04f, zombies);
            zombie.setPlayMode(Animation.PlayMode.LOOP_REVERSED);
        }*/
        zombie = new Animation(0.04f, zombies);
        zombie.setPlayMode(Animation.PlayMode.LOOP_REVERSED);
        // Creem els 16 estats de l'explosió
        //explosion = new TextureRegion[4];
        explosion = new TextureRegion[16];

        // Carreguem els 16 estats de l'explosió
        int index = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 8; j++) {
                explosion[index++] = new TextureRegion(sheet, j * 678,  i * 63 + 40, 105, 105);
            }
        }

        // Finalment creem l'animació
        explosionAnim = new Animation(0.04f, explosion);

        // Fons de pantalla
        background = new TextureRegion(sheet, 2, 764, 768, 566);
        background.flip(false, true);  // da la vuelta al fondo

        /******************************* Sounds *************************************/
        // Explosió
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));

        bonusSound = Gdx.audio.newSound(Gdx.files.internal("sounds/coin.wav"));
        // Música del joc
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
        coins.dispose();
        explosionSound.dispose();
        music.dispose();

    }
}
