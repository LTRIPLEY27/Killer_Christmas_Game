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


    public ScrollHandler() {

        // Creem els dos fons
        bg = new Background(0, 0, Settings.GAME_WIDTH * 2, Settings.GAME_HEIGHT, Settings.BG_SPEED);
        bg_back = new Background(bg.getTailX(), 0, Settings.GAME_WIDTH * 2, Settings.GAME_HEIGHT, Settings.BG_SPEED);

        // Afegim els fons al grup
        addActor(bg);
        addActor(bg_back);

        // Creem l'objecte random
        r = new Random();

        // Comencem amb 3 zombies
        numAsteroids = 3;
        bonusACount = 3;
        bonusBCount = 2;

        // Creem l'ArrayList
        zombies = new ArrayList<Zombie>();
        bonusA = new ArrayList<BonusA>();
        bonusB = new ArrayList<BonusB>();

        // Definim una mida aleatòria entre el mínim i el màxim
        float newSize = Methods.randomFloat(Settings.MIN_ASTEROID, Settings.MAX_ASTEROID) * 34;

        float newSizeCoin = Methods.randomFloat(Settings.MIN_COIN, Settings.MAX_COIN) * 34;

        // Afegim el primer Zombie a l'Array i al grup
        Zombie zombie = new Zombie(Settings.GAME_WIDTH, r.nextInt(Settings.GAME_HEIGHT - (int) newSize), newSize, newSize, Settings.ASTEROID_SPEED);
        BonusA bonus1 = new BonusA(Settings.GAME_WIDTH, r.nextInt(Settings.GAME_HEIGHT - (int) newSizeCoin), newSizeCoin, newSizeCoin, Settings.ASTEROID_SPEED);
        BonusB bonus2 = new BonusB(Settings.GAME_WIDTH, r.nextInt(Settings.GAME_HEIGHT - (int) newSizeCoin), newSizeCoin, newSizeCoin, Settings.ASTEROID_SPEED);

        zombies.add(zombie);
        bonusA.add(bonus1);
        bonusB.add(bonus2);

        addActor(zombie);
        addActor(bonus1);
        addActor(bonus2);

        // Des del segon fins l'últim asteroide
        for (int i = 1; i < numAsteroids; i++) {
            // Creem la mida al·leatòria
            newSize = Methods.randomFloat(Settings.MIN_ASTEROID, Settings.MAX_ASTEROID) * 34;
            // Afegim l'zombie.
            zombie = new Zombie(zombies.get(zombies.size() - 1).getTailX() + Settings.ASTEROID_GAP, r.nextInt(Settings.GAME_HEIGHT - (int) newSize), newSize, newSize, Settings.ASTEROID_SPEED);
            // Afegim l'asteroide a l'ArrayList
            zombies.add(zombie);
            // Afegim l'asteroide al grup d'actors
            addActor(zombie);
        }


        // TODAS LAS COINS
        for (int i = 1; i < bonusACount; i++) {
            // Creem la mida al·leatòria
            newSizeCoin = Methods.randomFloat(Settings.MIN_COIN, Settings.MAX_COIN) * 34;
            // Afegim l'zombie.
            bonus1 = new BonusA(bonusA.get(bonusA.size() - 1).getTailX() + Settings.ASTEROID_GAP, r.nextInt(Settings.GAME_HEIGHT - (int) newSizeCoin), newSizeCoin, newSizeCoin, Settings.ASTEROID_SPEED);
            // Afegim l'asteroide a l'ArrayList
            bonusA.add(bonus1);
            // Afegim l'asteroide al grup d'actors
            addActor(bonus1);
        }

        // TODAS LAS COINS
        for (int i = 1; i < bonusBCount; i++) {
            // Creem la mida al·leatòria
            newSizeCoin = Methods.randomFloat(Settings.MIN_COIN, Settings.MAX_COIN) * 34;
            // Afegim l'zombie.
            bonus2 = new BonusB(bonusB.get(bonusB.size() - 1).getTailX() + Settings.ASTEROID_GAP, r.nextInt(Settings.GAME_HEIGHT - (int) newSizeCoin), newSizeCoin, newSizeCoin, Settings.ASTEROID_SPEED);
            // Afegim l'asteroide a l'ArrayList
            bonusB.add(bonus2);
            // Afegim l'asteroide al grup d'actors
            addActor(bonus2);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        // Si algun element està fora de la pantalla, fem un reset de l'element.
        if (bg.isLeftOfScreen()) {
            bg.reset(bg_back.getTailX());

        } else if (bg_back.isLeftOfScreen()) {
            bg_back.reset(bg.getTailX());

        }

        for (int i = 0; i < zombies.size(); i++) {

            Zombie zombie = zombies.get(i);
            if (zombie.isLeftOfScreen() ) {
                if (i == 0) {
                    zombie.reset(zombies.get(zombies.size() - 1).getTailX() + Settings.ASTEROID_GAP);
                } else {
                    zombie.reset(zombies.get(i - 1).getTailX() + Settings.ASTEROID_GAP);
                }
            }
        }


        for (int i = 0; i < bonusA.size(); i++) {

            BonusA bonus1 = bonusA.get(i);
            if (bonus1.isLeftOfScreen()) {
                if (i == 0) {
                    bonus1.reset(bonusA.get(bonusA.size() - 1).getTailX() + Settings.ASTEROID_GAP);
                } else {
                    bonus1.reset(bonusA.get(i - 1).getTailX() + Settings.ASTEROID_GAP);
                }
            }
        }

        for (int i = 0; i < bonusB.size(); i++) {

            BonusB bonus2 = bonusB.get(i);
            if (bonus2.isLeftOfScreen()) {
                if (i == 0) {
                    bonus2.reset(bonusA.get(bonusA.size() - 1).getTailX() + Settings.ASTEROID_GAP);
                } else {
                    bonus2.reset(bonusA.get(i - 1).getTailX() + Settings.ASTEROID_GAP);
                }
            }
        }
    }

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
    /*
    public boolean collidesOk(Fire fire){

        for(BonusA  i : bonusA){
            if(i.collides(fire)){
                return true;
            }
        }

        return false;
    }*/


    // CHEQUEAR PUES ACÁ PUEDE REVENTAR EL ZOMBIE
    public void reset() {

        ///***************************************************************** coentado hoy marroc gano
        // Posem el primer asteroid fora de la pantalla per la dreta
        //zombies.get(0).reset(Settings.GAME_WIDTH);
        //bonusA.get(0).reset(Settings.GAME_WIDTH);
        //bonusB.get(0).reset(Settings.GAME_WIDTH);
        // Calculem les noves posicions de la resta d'zombies.
        for (int i = 1; i < zombies.size(); i++) {

            zombies.get(i).reset(zombies.get(i - 1).getTailX() + Settings.ASTEROID_GAP);

        }

        // Calculem les noves posicions de la resta d'zombies.
        for (int i = 1; i < bonusA.size(); i++) {

            bonusA.get(i).reset(bonusA.get(i - 1).getTailX() + Settings.ASTEROID_GAP);

        }

        for (int i = 1; i < bonusB.size(); i++) {

            bonusB.get(i).reset(bonusB.get(i - 1).getTailX() + Settings.ASTEROID_GAP);

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

    public  void addNewBonus(BonusA bonus){
        bonusA.add(bonus);
    }

    public  void addNewBonusB(BonusB bonus){
        bonusB.add(bonus);
    }

    public  void addNewZombie(Zombie zombie){
        zombies.add(zombie);
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
}