package cat.xtec.ioc.objects;

import com.badlogic.gdx.scenes.scene2d.Group;

import java.util.ArrayList;
import java.util.Random;

import cat.xtec.ioc.utils.Methods;
import cat.xtec.ioc.utils.Settings;

public class ScrollHandler extends Group {

    // Fons de pantalla
    Background bg, bg_back;

    // Asteroides
    int numAsteroids;
    int bonusACount;
    int bonusBCount;
    private ArrayList<Zombie> zombies;
    private ArrayList<BonusA> bonusA;
    private ArrayList<BonusB> bonusB;

    // Objecte Random
    Random r;

    // DEFINIMOS LAS VARIABLES PARA MANEJAR EL ESTADO PAUSED
    private boolean paused;

    private float creation;
    public ScrollHandler() {

        // Creem els dos fons
        bg = new Background(0, 0, Settings.GAME_WIDTH * 2, Settings.GAME_HEIGHT, Settings.BG_SPEED);
        bg_back = new Background(bg.getTailX(), 0, Settings.GAME_WIDTH * 2, Settings.GAME_HEIGHT, Settings.BG_SPEED);

        // Afegim els fons al grup
        addActor(bg);
        addActor(bg_back);

        // Creem l'objecte random
        r = new Random();

        // array de elementos

        //DECLARAMOS LA PROPORCIONALIDAD DE SALIDA DE LOS BONUS CON UN RANDOM, Y CON MAYOR PUNTUACIÓN EL BONUS B CON MENOR PROPORCION DE SALIDA
        bonusACount = r.nextInt(10);
        bonusBCount = r.nextInt(5);

        // definición de los tiempos para salir elementos per pantalla
        creation = 0;

        // INICIALIZAREMOS EL ESTADO PAUSE EN FALSE
        paused = false;

        // Creem l'ArrayList
        zombies = new ArrayList<Zombie>();
        bonusA = new ArrayList<BonusA>();
        bonusB = new ArrayList<BonusB>();

        float newSizeCoin = Methods.randomFloat(Settings.MIN_COIN, Settings.MAX_COIN) * 42;
        // MODIFICACIÓN DE LAS SETTINGS PARA INDICAR LA VELOCIDAD Y MANEJAR LA PROBABILIDAD DE APARICION
        BonusA bonus1 = new BonusA(Settings.GAME_WIDTH, r.nextInt(Settings.GAME_HEIGHT - (int) newSizeCoin), newSizeCoin, newSizeCoin, Settings.SCOREA_SPEED);
        BonusB bonus2 = new BonusB(Settings.GAME_WIDTH, r.nextInt(Settings.GAME_HEIGHT - (int) newSizeCoin), newSizeCoin, newSizeCoin, Settings.SCOREB_SPEED);

        // DEFINICIÓN DE LOS BONUS A LA PANTALLA
        bonusA.add(bonus1);
        bonusB.add(bonus2);

        addActor(bonus1);
        addActor(bonus2);

        // TODAS LAS COINS
        for (int i = 1; i < bonusACount; i++) {
            newSizeCoin = Methods.randomFloat(Settings.MIN_COIN, Settings.MAX_COIN) * 34;

            bonus1 = new BonusA(bonusA.get(bonusA.size() - 1).getTailX() + Settings.ASTEROID_GAP, r.nextInt(Settings.GAME_HEIGHT - (int) newSizeCoin), newSizeCoin, newSizeCoin, Settings.SCOREA_SPEED);
            bonusA.add(bonus1);
            addActor(bonus1);
        }

        // TODAS LAS COINS
        for (int i = 1; i < bonusBCount; i++) {
            newSizeCoin = Methods.randomFloat(Settings.MIN_COIN, Settings.MAX_COIN) * 34;
            bonus2 = new BonusB(bonusB.get(bonusB.size() - 1).getTailX() + Settings.ASTEROID_GAP, r.nextInt(Settings.GAME_HEIGHT - (int) newSizeCoin), newSizeCoin, newSizeCoin, Settings.SCOREB_SPEED);
            bonusB.add(bonus2);
            addActor(bonus2);
        }


        addNewZombie();
    }

    //public  void addNewBonus(BonusA bonus){
    public  void addNewBonus(){
        float newSizeCoin = Methods.randomFloat(Settings.MIN_COIN, Settings.MAX_COIN) * 42;
        BonusA bonus1 = new BonusA(Settings.GAME_WIDTH, r.nextInt(Settings.GAME_HEIGHT - (int) newSizeCoin), newSizeCoin, newSizeCoin, Settings.SCOREA_SPEED);
        bonusA.add(bonus1);
        addActor(bonus1);
    }

    //public  void addNewBonusB(BonusB bonus){
    public  void addNewBonusB(){
        float newSizeCoin = Methods.randomFloat(Settings.MIN_COIN, Settings.MAX_COIN) * 42;
        BonusB bonus2 = new BonusB(Settings.GAME_WIDTH, r.nextInt(Settings.GAME_HEIGHT - (int) newSizeCoin), newSizeCoin, newSizeCoin, Settings.SCOREB_SPEED);
        bonusB.add(bonus2);
        addActor(bonus2);
    }

    //public  void addNewZombie(Zombie zombie){
    public  void addNewZombie(){
        // Definim una mida aleatòria entre el mínim i el màxim
        float newSize = Methods.randomFloat(Settings.MIN_ASTEROID, Settings.MAX_ASTEROID) * 34;
        // Afegim el primer Zombie a l'Array i al grup
        Zombie zombie = new Zombie(Settings.GAME_WIDTH, r.nextInt(Settings.GAME_HEIGHT - (int) newSize), newSize, newSize, Settings.ASTEROID_SPEED);
        zombies.add(zombie);
        addActor(zombie);
    }


    @Override
    public void act(float delta) {
        super.act(delta);
        // Si algun element està fora de la pantalla, fem un reset de l'element.
        if (bg.isLeftOfScreen()) {
            bg.reset(bg_back.getTailX());

        }
        else if (bg_back.isLeftOfScreen()) {
            bg_back.reset(bg.getTailX());
        }

        for (int i = 0; i < zombies.size(); i++) {

            Zombie zombie = zombies.get(i);
            if (zombie.isLeftOfScreen() ) {
                reset();
            }
        }


        for (int i = 0; i < bonusA.size(); i++) {

            BonusA bonus1 = bonusA.get(i);
            if (bonus1.isLeftOfScreen()) {
                if (i == 0) {
                    bonus1.reset(bonusA.get(bonusA.size() - 1).getTailX() + Settings.COIN_GAP);
                } else {
                   bonus1.reset(bonusA.get(i - 1).getTailX() + Settings.COIN_GAP);
                }
            }
        }

        for (int i = 0; i < bonusB.size(); i++) {

            BonusB bonus2 = bonusB.get(i);
            if (bonus2.isLeftOfScreen()) {
                if (i == 0) {

                    bonus2.reset(bonusA.get(bonusA.size() - 1).getTailX() + Settings.COIN_GAP);
                } else {
                    bonus2.reset(bonusA.get(i - 1).getTailX() + Settings.COIN_GAP);

                }
            }
        }


        // paused
        if(paused) creation = 0;

        // VERIFICA PARA QUE MIESNTRAS TRANSCURRA EL JUEGO VAYAN APARECIENDO LOS CARACTERES DEMANDADOS
        if( creation > 1f && zombies.size() < 3 || bonusA.size() < 1 || bonusB.size() < 1){
            addNewZombie();
            addNewBonus();
            addNewBonusB();
            creation = 0;
        } else {
            creation += delta;
        }

    }

    // MANEJO DE LAS COLISIONES CON SANTA
    public boolean collides(KillerSanta nau) {

        // Comprovem les col·lisions entre cada asteroid i la nau
        for (Zombie zombie : zombies) {
            if (zombie.collides(nau)) {
                return true;
            }
        }
        return false;
    }

    public boolean collidesBonus(Fire fire) {

        // Comprovem les col·lisions entre cada asteroid i la nau
        for (BonusA i : bonusA) {
            if (i.collides(fire)) {
                getBonus(i);
                return true;
            }
        }
        return false;
    }

    public boolean collidesBonusB(Fire fire) {

        // Comprovem les col·lisions entre cada asteroid i la nau
        for (BonusB i : bonusB) {
            if (i.collides(fire)) {
                getBonusB(i);
                return true;
            }
        }
        return false;
    }
    // colisión del zombie y el fuego
    //**********
    public Zombie collides(Fire fire){

        for(Zombie i : zombies){
            if(i.collides(fire)){
                return i;
            }
        }

        return null;
    }


    public BonusA collidesOk(Fire fire){

        for(BonusA i : bonusA){
            if(i.collides(fire)){
                return i;
            }
        }

        return null;
    }

    public BonusB collidesOkB(Fire fire){

        for(BonusB i : bonusB){
            if(i.collides(fire)){
                return i;
            }
        }

        return null;
    }


    public void reset() {

        ///***************************************************************** coentado hoy marroc gano
        // Posem el primer asteroid fora de la pantalla per la dreta
        zombies.get(0).reset(Settings.GAME_WIDTH);
        bonusA.get(0).reset(Settings.GAME_WIDTH);
        bonusB.get(0).reset(Settings.GAME_WIDTH);
        // Calculem les noves posicions de la resta d'zombies.
        for (int i = 1; i < zombies.size(); i++) {

            zombies.get(i).reset(zombies.get(i - 1).getTailX() + Settings.ASTEROID_GAP);

        }

        // Calculem les noves posicions de la resta d'zombies.
        for (int i = 1; i < bonusA.size(); i++) {

            bonusA.get(i).reset(bonusA.get(i - 1).getTailX() + Settings.COIN_GAP);

        }

        for (int i = 1; i < bonusB.size(); i++) {

            bonusB.get(i).reset(bonusB.get(i - 1).getTailX() + Settings.COIN_GAP);

        }
    }

    public void killerZombie(Zombie zombie){
        zombies.remove(zombie);
        zombie.remove();
    }

    public void getBonus(BonusA bonus){
        bonusA.remove(bonus);
        bonus.remove();
    }

    public void getBonusB(BonusB bonus){
        bonusB.remove(bonus);
        bonus.remove();
    }



    public ArrayList<Zombie> getAsteroids() {
        return zombies;
    }
    public ArrayList<BonusA> getCoins() {
        return bonusA;
    }

    public ArrayList<BonusB> getCoinsB() {
        return bonusB;
    }

    public void setPaused(){
        paused = true;
        bg.startPaused();
        bg_back.startPaused();

        // ponemos en pause a cada elemento contenido del array
        for(Zombie i : zombies){
            i.startPause();
        }

        for(BonusA i : bonusA){
            i.startPause();
        }

        for(BonusB i : bonusB){
            i.startPause();
        }
    }

    public void stopPaused(){
        paused = false;

        bg.stopPause();
        bg_back.stopPause();

        for(Zombie i : zombies){
            i.stopPause();
        }

        for(BonusA i : bonusA){
            i.stopPause();
        }

        for(BonusB i : bonusB){
            i.stopPause();
        }
    }
}