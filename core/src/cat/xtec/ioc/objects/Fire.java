package cat.xtec.ioc.objects;


import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;

import cat.xtec.ioc.helpers.AssetManager;

// NUEVA CLASE PARA GESTIONAR EL DISPARO
public class Fire extends Scrollable{

    private Rectangle attack;// clase de lib

    private boolean onPause;
    private Action pauseAction;

    // constructor con atributosd de scrollable para hacer uso de las posicioness
    public Fire(float x, float y, float width, float height, float velocity) {
        super(x, y, width, height, velocity);

        // INICIALIZACIÓN DE ATRIBUTOS
        attack = new Rectangle();

        onPause = false;
    }

    // método para llamar al pause ejercicio 3
    void getPause(){

        onPause = true;
        pauseAction = Actions.repeat(RepeatAction.FOREVER, Actions.sequence(Actions.alpha(0.5f, 0.2f), Actions.alpha(1.0f, 0.2f)));
        this.addAction(pauseAction);

    }

    // método para reiniciar el juego y salir del pause
    void getStart(){
        onPause = false;
        this.removeAction(pauseAction);
    }

    @Override
    public void act(float delta){
        //if(!onPause){
            super.act(delta);
            attack.set(position.x, position.y, width, 2);
        //}
    }

    @Override
    public void draw(Batch batch, float alfa){
        super.draw(batch, alfa);
        batch.draw(AssetManager.fire, position.x, position.y, this.getOriginX(), this.getOriginY(), width, height, this.getScaleX(), this.getScaleY(), this.getRotation());
    }

    public Rectangle getAttack(){
        return  attack;
    }

}

