package cat.xtec.ioc.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.Viewport;


import java.util.ArrayList;

import cat.xtec.ioc.helpers.AssetManager;
import cat.xtec.ioc.helpers.InputHandler;

import cat.xtec.ioc.objects.BonusA;
import cat.xtec.ioc.objects.BonusB;
import cat.xtec.ioc.objects.Fire;
import cat.xtec.ioc.objects.ScrollHandler;
import cat.xtec.ioc.objects.KillerSanta;
import cat.xtec.ioc.objects.Zombie;
import cat.xtec.ioc.utils.Settings;



public class GameScreen implements Screen {

    // Els estats del joc

    public enum GameState {

        READY, RUNNING, GAMEOVER, PAUSED

    }



    private GameState currentState;

    // Objectes necessaris
    private Stage stage;
    private KillerSanta santa;
    private ScrollHandler scrollHandler;

    // Encarregats de dibuixar elements per pantalla
    private ShapeRenderer shapeRenderer;
    private Batch batch;

    // Per controlar l'animació de l'explosió
    private float explosionTime = 0;

    // Preparem el textLayout per escriure text
    private GlyphLayout textLayout;
    private GlyphLayout pounts;

    private ArrayList <Fire> fires;

    private ArrayList<Zombie> zombies;
    private ArrayList<BonusA> bonus1;
    private ArrayList<BonusB> bonus2;

    ////////

    private Label.LabelStyle textStyle;
    private Label text;

    private Image paused;

    private int score = 0;

    // VARIABLE PREFERENCES PARA LA PERSISTENCIA DEL SCORE

    public static Preferences maxScore = Gdx.app.getPreferences("scoreMax");

    public GameScreen(Batch prevBatch, Viewport prevViewport) {

        // Iniciem la música
        AssetManager.music.play();

        // Creem el ShapeRenderer
        shapeRenderer = new ShapeRenderer();

        // Creem l'stage i assginem el viewport
        stage = new Stage(prevViewport, prevBatch);

        batch = stage.getBatch();

        // Creem la nau i la resta d'objectes
        santa = new KillerSanta(Settings.SPACECRAFT_STARTX, Settings.SPACECRAFT_STARTY, Settings.SPACECRAFT_WIDTH, Settings.SPACECRAFT_HEIGHT);
        scrollHandler = new ScrollHandler();

        // Afegim els actors a l'stage
        stage.addActor(scrollHandler);
        stage.addActor(santa);
        // Donem nom a l'Actor
        santa.setName("santa");

        fires = new ArrayList<Fire>();

        // Iniciem el GlyphLayout
        textLayout = new GlyphLayout();
        textLayout.setText(AssetManager.font, "Are you\nready to run for your life?");


        // CARGA DE LA IMAGEN PAUSE DEL ASSETMANAGER
        paused = new Image(AssetManager.pause);
        paused.setName("pause");
        paused.setWidth(40);
        paused.setHeight(30);

        // INDICACIÓN DE LA POSICIÓN DEL PAUSE
        paused.setPosition(200, -10);

        // PUNTAJE TEXT
        textStyle = new Label.LabelStyle(AssetManager.font, null);
        text = new Label(("Points :") + score, textStyle);

        //POSICION DE ACTORES EN PANTALLA
        stage.addActor(paused);
        stage.addActor(text);
        currentState = GameState.READY;
        // Assignem com a gestor d'entrada la classe InputHandler
        Gdx.input.setInputProcessor(new InputHandler(this));

    }

    private void drawElements() {

        // Recollim les propietats del Batch de l'Stage
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());

        // Inicialitzem el shaperenderer
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        // Definim el color (verd)
        shapeRenderer.setColor(new Color(0, 1, 0, 1));

        // Pintem la nau
        shapeRenderer.rect(santa.getX(), santa.getY(), santa.getWidth(), santa.getHeight());

        // LOS ACTORES
        zombies = scrollHandler.getAsteroids();

        bonus1 = scrollHandler.getCoins();
        bonus2 = scrollHandler.getCoinsB();

        Zombie zombie;
        BonusA bonusA;
        BonusB bonusB;


        for (int i = 0; i < zombies.size(); i++) {

            zombie= zombies.get(i);
            switch (i) {
                case 0:
                    shapeRenderer.setColor(1, 0, 0, 1);
                    break;
                case 1:
                    shapeRenderer.setColor(0, 0, 1, 1);
                    break;
                case 2:
                    shapeRenderer.setColor(1, 1, 0, 1);
                    break;
                default:
                    shapeRenderer.setColor(1, 1, 1, 1);
                    break;
            }
            shapeRenderer.circle(zombie.getX() + zombie.getWidth() / 2, zombie.getY() + zombie.getWidth() / 2, zombie.getWidth() / 2);
        }

        //***************

                //  renderrización de las coins
        //////////////////////////////////////////////////////
        for (int i = 0; i < bonus1.size(); i++) {

            bonusA = bonus1.get(i);
            switch (i) {
                case 0:
                    shapeRenderer.setColor(1, 0, 0, 1);
                    break;
                case 1:
                    shapeRenderer.setColor(0, 0, 1, 1);
                    break;
                case 2:
                    shapeRenderer.setColor(1, 1, 0, 1);
                    break;
                default:
                    shapeRenderer.setColor(1, 1, 1, 1);
                    break;
            }
            shapeRenderer.circle(bonusA.getX() + bonusA.getWidth() / 2, bonusA.getY() + bonusA.getWidth() / 2, bonusA.getWidth() / 2);
        }

        //////////////////////////////////////////////////////
        for (int i = 0; i < bonus2.size(); i++) {

            bonusB = bonus2.get(i);
            switch (i) {
                case 0:
                    shapeRenderer.setColor(1, 0, 0, 1);
                    break;
                case 1:
                    shapeRenderer.setColor(0, 0, 1, 1);
                    break;
                case 2:
                    shapeRenderer.setColor(1, 1, 0, 1);
                    break;
                default:
                    shapeRenderer.setColor(1, 1, 1, 1);
                    break;
            }
            shapeRenderer.circle(bonusB.getX() + bonusB.getWidth() / 2, bonusB.getY() + bonusB.getWidth() / 2, bonusB.getWidth() / 2);
        }
        shapeRenderer.end();
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        // Dibuixem tots els actors de l'stage
        stage.draw();

        // Depenent de l'estat del joc farem unes accions o unes altres
        switch (currentState) {

            case GAMEOVER:
                updateGameOver(delta);
                break;
            case RUNNING:
                updateRunning(delta);
                break;
            case READY:
                updateReady();
                break;
                // NUEVO ESTADO 'PAUSE'
            case PAUSED:
                textLayout.setText(AssetManager.font, "Pause");
                //LLAMADO AL MÉTODO PAUSE PARA PARAR
                updatePaused(delta);
                break;
        }
    }


    // MÉTODO PARA INVOCAR LA PAUSE
    private void updatePaused(float delta){
        stage.act(delta);
        batch.begin();
        //DIBUJAMOS LA ETIQUETA
        AssetManager.font.draw(batch, textLayout, (Settings.GAME_WIDTH / 2) - textLayout.width / 2, (Settings.GAME_HEIGHT / 2) - textLayout.height / 2);
        batch.end();
    }

    private void updateReady() {

        // Dibuixem el text al centre de la pantalla
        batch.begin();
        AssetManager.font.draw(batch, textLayout, (Settings.GAME_WIDTH / 2) - textLayout.width / 2, (Settings.GAME_HEIGHT / 2) - textLayout.height / 2);
        batch.end();

    }


    private void updateRunning(float delta) {
        stage.act(delta);

        // DECLARACIÓN DEL MÁXIMO VALOR INICIAL A ROMPER DE SCORE
        maxScore.putInteger("score", 2500);
        maxScore.flush();

        if (scrollHandler.collides(santa)) {
            // Si hi ha hagut col·lisió: Reproduïm l'explosió i posem l'estat a GameOver
            AssetManager.explosionSound.play();
            stage.getRoot().findActor("santa").remove();

            // VERIFICACON Y MENSAJE DEL SCORE SEGÚN CADA CASO
            if(score < 1000){
                textLayout.setText(AssetManager.font, "Game Over :'(\nPrinciant\nyou Score is!!!" + score);
            } if (score >= 1000 && score < 1500){
                textLayout.setText(AssetManager.font, "Game Over :'(\nNot too bad\nyou Score is!!!" + score);
            }if(score >= 1500){
                textLayout.setText(AssetManager.font, "Game Over :'(\nGREAT!!!\nYou score is!!!" + score);
            }

            // ÚNICAMENTE DE ROMPER EL RECORD SETEAREMOS EL VALOR DE LA PERSISTENCIA
            if(score > maxScore.getInteger("score")){
                maxScore.putInteger("score", score);
                maxScore.flush();
            }

            currentState = GameState.GAMEOVER;
        }

        // VERIFICA COLISIONES DEL FUEGO
        if(fires.size() > 0) {
            // RECORREMOS EL ARRAY DE DISPAROS EJECUTADOS Y POR CADA UNO VALORIZAMOS SI HA ACERTADO CON UNO DE LOS SIGUEINTES PERSONAJES
            for(Fire i : fires){
                BonusA bonn = scrollHandler.collidesOk(i);
                BonusB bonni = scrollHandler.collidesOkB(i);

                // SI EL FUEGO HA COLISIONADO CON UN BONUS ENTRAMOS Y ACTUALIZAMOS
                if (bonn != null){
                    AssetManager.bonusSound.play();
                    score += 30;
                    text.setText("Score + Bonus : " + score);
                    scrollHandler.getBonus(bonn);
                }
                // SI EL FUEGO HA COLISIONADO CON UN BONUS ENTRAMOS Y ACTUALIZAMOS
                if (bonni != null){
                    AssetManager.bonusSound.play();
                    score += 50;
                    text.setText("Score + Bonus: " + score );
                    scrollHandler.getBonusB(bonni);
                }

                // SI EL FUEGO HA COLISIONADO CON UN ZOMBIE LLAMAMOS AL MÉTODO DE SCROLLHANDLER Y REMOVEMOS DE LA PANTALLA
                Zombie zombie = scrollHandler.collides(i);
                if(zombie != null){
                    AssetManager.explosionSound.play();
                    score += 10;
                    text.setText("Score : " + score);
                    fires.remove(i);
                    i.remove();
                    scrollHandler.killerZombie(zombie);
                    break;
                }

            }

        }

    }




    private void updateGameOver(float delta) {
        stage.act(delta);

        batch.begin();
        AssetManager.font.draw(batch, textLayout, (Settings.GAME_WIDTH - textLayout.width) / 2, (Settings.GAME_HEIGHT - textLayout.height) / 2);
        // Si hi ha hagut col·lisió: Reproduïm l'explosió i posem l'estat a GameOver
        batch.draw((TextureRegion) AssetManager.explosionAnim.getKeyFrame(explosionTime, false), (santa.getX() + santa.getWidth() / 2) - 32, santa.getY() + santa.getHeight() / 2 - 32, 64, 64);
        batch.end();

        explosionTime += delta;

    }



    public void reset() {

        // COLOCAMOS EL SCORE EN 0 POR CADA RESET
        score = 0;
        text.setText("Score " + score );
        // Posem el text d'inici
        textLayout.setText(AssetManager.font, "Are you\nready to run for your life?");
        // Cridem als restart dels elements.
        santa.reset();
        scrollHandler.reset();

        // Posem l'estat a 'Ready'
        currentState = GameState.READY;

        // Afegim la nau a l'stage
        stage.addActor(santa);

        // Posem a 0 les variables per controlar el temps jugat i l'animació de l'explosió
        explosionTime = 0.0f;

    }


    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

        /// LLAMAMOS A PAUSAR LA MÍCA Y SETTEAR EL ESTADO DEL JUEGO
        AssetManager.music.pause();

        if(getCurrentState() == GameState.RUNNING) {
            setCurrentState(GameState.PAUSED);
        }
    }

    @Override
    public void resume() {
        AssetManager.music.play();
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public KillerSanta getSpacecraft() {
        return santa;
    }

    public Stage getStage() {
        return stage;
    }

    public ScrollHandler getScrollHandler() {
        return scrollHandler;
    }

    public GameState getCurrentState() {
        return currentState;
    }


    // VALIDAMOS LOS CURRENTSTATE SEGÚN CADA ESTADO DEL JUEGO

    /*
    * PAUSE : LLAMA A LOS MÉTODOS 'SETPAUSED' Y 'HIDEPOSITIONS'
    * RUNNING : QUITAMOS LA PAUSA, EN CASO DE HACER SIDO PRESONADA, Y VAMOS VALORIZANDO SEGUN EL COMPORTAMIENTO
    * */
    public void setCurrentState(GameState currentState) {

        switch (currentState) {
            case PAUSED:
                // CONDICIONAMOS EL VOLUMEN A LA PAUSA
                //stage.getRoot().findActor("pause");

                AssetManager.music.setVolume(0.01f);
                hidePositions();
                santa.startPause();

                scrollHandler.setPaused();
                break;
            case RUNNING:

                if(this.currentState != GameState.PAUSED){
                   // stage.getRoot().findActor("pause");
                    AssetManager.music.setVolume(0.3f);
                    showPositions();

                    santa.stopPause();
                    scrollHandler.stopPaused();
                    Fire fire = new Fire(santa.getX() + santa.getWidth(), santa.getY() + santa.getHeight()/2, 9, 2, Settings.SPACECRAFT_VELOCITY * 2);
                    stage.getRoot().addActor(fire);
                    fires.add(fire);
                    AssetManager.explosionSound.play();

                    currentState = GameState.RUNNING;
                }

                break;
            case READY :
                break;
        }
        this.currentState = currentState;

    }


    // MÉTODOS PARA ESCONDER LOS ELEMENTOS : 'PAUSED' Y SCORE
    private void hidePositions() {
        stage.getRoot().findActor("pause").setVisible(false);
        text.setText("");
    }

    // MÉTODOS PARA MOSTRAR  LOS ELEMENTOS : 'PAUSED' Y SCORE
    private void showPositions() {
        stage.getRoot().findActor("pause").setVisible(true);
        text.setText("Score : " + score);
    }

}
