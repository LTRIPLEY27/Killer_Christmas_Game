package cat.xtec.ioc.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateByAction;


import java.util.Random;

import cat.xtec.ioc.helpers.AssetManager;
import cat.xtec.ioc.utils.Methods;
import cat.xtec.ioc.utils.Settings;

public class Zombie extends Scrollable {

    private Circle collisionCircle;

    Random r;

    int assetAsteroid;

    //paused
    private boolean paused;
    private Action pauseAction;
    private RepeatAction repeat;

    private float runTime = 0;  // uso de la animation

    public Zombie(float x, float y, float width, float height, float velocity) {
        super(x, y, width, height, velocity);

        // Creem el cercle
        collisionCircle = new Circle();

        /* Accions */
        r = new Random();
        assetAsteroid = r.nextInt(3);

        setOrigin();

        // Equivalent:
        this.addAction(Actions.repeat(RepeatAction.FOREVER, Actions.rotateBy(-90f, 0.2f)));

        //this.addAction(repeat);

        paused = false;

    }

    public void setOrigin() {

        this.setOrigin(width/2 + 1, height/2);

    }

    // CONDICIONAMOS A QUE NO ESTÉ PAUSADO
    @Override
    public void act(float delta) {

        if(!paused){
            super.act(delta);

            // Actualitzem el cercle de col·lisions (punt central de l'asteroid i el radi.
            collisionCircle.set(position.x + width / 2.0f, position.y + width / 2.0f, width / 2.0f);
            runTime += delta; // aumenta el runtime
        }

    }

    @Override
    public void reset(float newX) {
        super.reset(newX);
        // Obtenim un número al·leatori entre MIN i MAX
        float newSize = Methods.randomFloat(Settings.MIN_ASTEROID, Settings.MAX_ASTEROID);
        // Modificarem l'alçada i l'amplada segons l'al·leatori anterior
        width = height = 34 * newSize;
        // La posició serà un valor aleatòri entre 0 i l'alçada de l'aplicació menys l'alçada
        position.y =  new Random().nextInt(Settings.GAME_HEIGHT - (int) height);

        //assetAsteroid = r.nextInt(15);
        assetAsteroid = r.nextInt(3);
        setOrigin();
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        // llamado a la animation con el runtime
        batch.draw((TextureRegion) AssetManager.zombie.getKeyFrame(runTime, true), this.position.x, this.position.y, width, height);
    }

    // Retorna true si hi ha col·lisió
    public boolean collides(KillerSanta nau) {

        if (position.x <= nau.getX() + nau.getWidth()) {
            // Comprovem si han col·lisionat sempre i quan l'asteroid estigui a la mateixa alçada que la spacecraft
            return (Intersector.overlaps(collisionCircle, nau.getCollisionRect()));
        }
        return false;
    }

    // SOBREESCRITURA DEL MÉTODO PARA HACER COLISIÓN CON EL FUEGO

    public Circle getCollisionCircle() {
        return collisionCircle;
    }

    public  boolean collides(Fire fire){

        if(position.x <= fire.getX() + fire.getWidth()){
            return (Intersector.overlaps(collisionCircle, fire.getAttack()));
        }

        return false;
    }


    public void startPause() {
        paused = true;
        pauseAction = Actions.repeat(RepeatAction.FOREVER, Actions.sequence(Actions.alpha(0.5f, 0.2f), Actions.alpha(1.0f, 0.2f)));
        this.addAction(pauseAction);
    }
    public void stopPause() {
        paused = false;
        this.clearActions();
        this.removeAction(repeat);
    }
}
