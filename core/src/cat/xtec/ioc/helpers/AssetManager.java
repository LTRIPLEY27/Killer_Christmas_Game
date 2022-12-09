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

    //public  static TextureAtlas atlas;

    //public static Animation <Sprite> run;

    // CAMBIO DE VARIABLES SEGÚN EL ENUNCIADO
    // Nau i fons

    // addhiriendo los valores para que el assetmanager ejecute el ataque 'FIRE'
    public static TextureRegion santa, santaDown, santaUp, background, fire;

    // Zombie
    public static TextureRegion[] zombies;
    public static Animation zombie;

    // Zombie
    /*public static TextureRegion[] fires;
    public static Animation fire;*/

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
        santa = new TextureRegion(sheet, 785, 48, 90, 120); // -- ok
        santa.flip(false, true);

        santaUp = new TextureRegion(sheet, 375, 44, 104, 136); // -- ok
        santaUp.flip(false, true);

        santaDown = new TextureRegion(sheet, 478, 43, 90, 125); // ok
        santaDown.flip(false, true);

        //****************  ejercicio 2 fire

        // BOLA DE ZOMBIES
        fire = new TextureRegion(sheet, 1285, 1298, 35, 32);
        fire.flip(false, true);
        //fireButton = new TextureRegion(sheet, 772, 775,  32, 32);
        //fireButton.flip(false, true);

        // with atlas
        //atlas = new TextureAtlas(Gdx.files.internal("SHEET_IMAGE.atlas"));

         //********************************************************************

            // FIRE
         //********************************************************************



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

        /*fires = new TextureRegion[6];

        fires[0] = new TextureRegion(sheet, 1266, 1014, 16, 16);
        fires[1] = new TextureRegion(sheet, 968, 1008, 22, 21);
        fires[2] = new TextureRegion(sheet, 1285, 1298, 35, 32);
        fires[3] = new TextureRegion(sheet, 1266, 1014, 16, 16);
        fires[4] = new TextureRegion(sheet, 1285, 1298, 35, 32);
        fires[5] = new TextureRegion(sheet, 877, 4, 73, 74);

        //fire = new Animation(0.04f, fires);

        for(int i = 0; i < fires.length; i++){
            fire = new Animation(0.04f, fires);
            fire.setPlayMode(Animation.PlayMode.LOOP_REVERSED);
        }
*/
        // Creem l'animació de l'asteroid i fem que s'executi contínuament en sentit anti-horari

        for(int i = 0; i < zombies.length; i++){
            zombie = new Animation(0.04f, zombies);
            zombie.setPlayMode(Animation.PlayMode.LOOP_REVERSED);
        }
        //zombie = new Animation(0.04f, zombies);
        //zombie.setPlayMode(Animation.PlayMode.LOOP);

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
        //background = new TextureRegion(sheet1, 0, 177, 480, 135);
        background = new TextureRegion(sheet, 2, 764, 768, 566);
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
        //atlas.dispose();

    }
}
