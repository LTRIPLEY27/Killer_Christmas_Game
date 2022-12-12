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

public class BonusB extends  Scrollable{
    private Circle isaBonus;

    //Random r;

    int bonusCounter;

    private float runTime = 0;

    // ATRIBUTOS DEL PAUSE
    private boolean paused;
    private Action pauseAction;

    private RepeatAction repeat;
    public BonusB(float x, float y, float width, float height, float velocity) {
        super(x, y, width, height, velocity);

        isaBonus = new Circle();

        bonusCounter = new Random().nextInt(2);

        setOrigin();

        paused = false;
        // Rotacio
       /* RotateByAction rotateAction = new RotateByAction();
        rotateAction.setAmount(-90f);
        rotateAction.setDuration(0.2f);

        // Accio de repetició
        RepeatAction repeat = new RepeatAction();
        repeat.setAction(rotateAction);
        repeat.setCount(RepeatAction.FOREVER);*/

        // Equivalent:
         this.addAction(Actions.repeat(RepeatAction.FOREVER, Actions.rotateBy(-90f, 0.2f)));

        //this.addAction(repeat);
    }

    public void setOrigin() {

        this.setOrigin(width/2 + 1, height/2);

    }

    @Override
    public void act(float delta) {
        if(!paused) {
            super.act(delta);

            // Actualitzem el cercle de col·lisions (punt central de l'asteroid i el radi.
            isaBonus.set(position.x + width / 2.0f, position.y + width / 2.0f, width / 2.0f);
            runTime += delta; // aumenta el runtime
        }
    }

    @Override
    public void reset(float newX) {
        super.reset(newX);
        // Obtenim un número al·leatori entre MIN i MAX
        float newSize = Methods.randomFloat(Settings.MIN_COIN, Settings.MAX_COIN);
        // Modificarem l'alçada i l'amplada segons l'al·leatori anterior
        width = height = 34 * newSize;
        // La posició serà un valor aleatòri entre 0 i l'alçada de l'aplicació menys l'alçada
        position.y =  new Random().nextInt(Settings.GAME_HEIGHT - (int) height);

        //assetAsteroid = r.nextInt(15);
        bonusCounter = new Random().nextInt(3);
        setOrigin();
    }

    // printamos las coins
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        // llamado a la animation con el runtime
        batch.draw((TextureRegion) AssetManager.bonu2.getKeyFrame(runTime, true), this.position.x, this.position.y, width, height);
    }

    // verifica si santa ha cogido o no el coin
    public boolean collides(Fire fire) {

        if(position.x <= fire.getX() + fire.getWidth()){
            return (Intersector.overlaps(isaBonus, fire.getAttack()));
        }

        return false;
    }

    public Circle getCollisionCircle() {
        return isaBonus;
    }

    public void startPause() {
        paused = true;
        pauseAction = Actions.repeat(RepeatAction.FOREVER, Actions.sequence(Actions.alpha(0.5f, 0.2f), Actions.alpha(1.0f, 0.2f)));
        this.addAction(pauseAction);
        //this.removeAction(repeat);
    }
    public void stopPause() {
        paused = false;
        this.clearActions();
        //this.addAction(repeat);
        this.removeAction(pauseAction);
    }
}
