package cat.xtec.ioc.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;

import cat.xtec.ioc.helpers.AssetManager;
import cat.xtec.ioc.utils.Settings;


// CLASE RENOMBRADA A NUESTRO ACTOR PRINCIPAL
public class KillerSanta extends Actor {

    // Distintes posicions de la spacecraft, recta, pujant i baixant
    public static final int SANTA_STRAIGHT = 0;
    public static final int SANTA_UP = 1;
    public static final int SANTA_DOWN = 2;

    // Paràmetres de la spacecraft
    private Vector2 position;
    private int width, height;
    private int direction;

    private Rectangle collisionRect;

    // DEFINICIÓN DE LOS ESTADOS DEL JUEGO EN LOS PERSONAJES
    private Action pausedActor;
    private boolean paused;

    public KillerSanta(float x, float y, int width, int height) {

        // Inicialitzem els arguments segons la crida del constructor
        this.width = width;
        this.height = height;
        position = new Vector2(x, y);

        // Inicialitzem la spacecraft a l'estat normal
        direction = SANTA_STRAIGHT;

        // Creem el rectangle de col·lisions
        collisionRect = new Rectangle();

        // Per a la gestio de hit
        setBounds(position.x, position.y, width, height);
        setTouchable(Touchable.enabled);

        // LA PAUSE LA INICIALIZAMOS EN FALSE Y RETORNARÁ TRUE CADA VEZ SE INVOQUE
        paused = false;
    }



    // MODIFICAMOS EL MÉTODO ACT PARA CONDICIONAR LAS PAUSED
    public void act(float delta) {
        super.act(delta);

        if(!paused){
            // Movem la spacecraft depenent de la direcció controlant que no surti de la pantalla
            switch (direction) {
                case SANTA_UP:
                    if (this.position.y - Settings.SPACECRAFT_VELOCITY * delta >= 0) {
                        this.position.y -= Settings.SPACECRAFT_VELOCITY * delta;
                    }
                    break;
                case SANTA_DOWN:
                    if (this.position.y + height + Settings.SPACECRAFT_VELOCITY * delta <= Settings.GAME_HEIGHT) {
                        this.position.y += Settings.SPACECRAFT_VELOCITY * delta;
                    }
                    break;
                case SANTA_STRAIGHT:
                    break;
            }

            collisionRect.set(position.x, position.y + 3, width, 10);
            setBounds(position.x, position.y, width, height);
        }

    }

    // DEFINICIÓN DE MÉTODOS PARA ACCIONAR LOS ESTADOS PAUSE Y PLAY
    public void startPause(){
        paused = true;
        pausedActor = Actions.repeat(RepeatAction.FOREVER, Actions.sequence(Actions.alpha(0.5f, 0.2f), Actions.alpha(1.0f, 0.2f)));
        this.addAction(pausedActor);
    }

    // CUANDO INVOCAMOS LA SALIDA DE PAUSE REMOVEMOS LA ACTION
    public void stopPause(){
        paused = false;
        this.removeAction(pausedActor);
    }


    // Getters dels atributs principals
    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    // Canviem la direcció de la spacecraft: Puja
    public void goUp() {
        direction = SANTA_UP;
    }

    // Canviem la direcció de la spacecraft: Baixa
    public void goDown() {
        direction = SANTA_DOWN;
    }

    // Posem la spacecraft al seu estat original
    public void goStraight() {
        direction = SANTA_STRAIGHT;
    }

    // Obtenim el TextureRegion depenent de la posició de la spacecraft
    public TextureRegion getSpacecraftTexture() {

        switch (direction) {

            case SANTA_STRAIGHT:
                return AssetManager.santa;
            case SANTA_UP:
                return AssetManager.santaUp;
            case SANTA_DOWN:
                return AssetManager.santaDown;
            default:
                return AssetManager.santa;
        }
    }

    public void reset() {

        // La posem a la posició inicial i a l'estat normal
        position.x = Settings.SPACECRAFT_STARTX;
        position.y = Settings.SPACECRAFT_STARTY;
        direction = SANTA_STRAIGHT;
        collisionRect = new Rectangle();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(getSpacecraftTexture(), position.x, position.y, width, height);
    }

    public Rectangle getCollisionRect() {
        return collisionRect;
    }
}
