package org.centrale.objet.WoE;

import java.util.List;
import java.util.Random;

/**
 * Classe mère de toutes les créatures
 */
public abstract class Creature extends ElementDeJeu implements Deplacable, ElementGraphique {
    //Attributs
    public int ptVie;
    public int degAtt;
    public int ptPar;
    public int pageAtt;
    public int pagePar;
    public int ptDeg;
    public Point2D pos;
    public World monde;

    public Random tirageAlea = new Random();

    //Constructeurs
    /**
     * Constructeur avec paramètres
     *
     * @param pV : Points de vie
     * @param dA : Dégâts attaque
     * @param pPar : Points de parade
     * @param paAtt : Percentage d'attaque
     * @param paPar : Percentage de parade
     * @param p : Position de la créature
     *
     */
    public Creature(World monde, int pV, int dA, int pPar, int paAtt, int paPar, Point2D p) {
        this.monde = monde;
        this.ptVie = pV;
        this.degAtt = dA;
        this.ptPar = pPar;
        this.pageAtt = paAtt;
        this.pagePar = paPar;
        this.pos = new Point2D(p);
    }

    /**
     * Constructeur de recopie
     *
     * @param c : Objet du type Creature qui sera copié
     */
    public Creature(Creature c){
        this.monde = c.monde;
        this.ptVie = c.ptVie;
        this.degAtt = c.degAtt;
        this.ptPar = c.ptPar;
        this.pageAtt = c.pageAtt;
        this.pagePar = c.pagePar;
        this.pos = new Point2D(c.pos);
    }

    /**
     * Constructeur sans paramètres
     */
    public Creature(){

    }

    //Méthodes
    public abstract void affiche();

    /**
     * Cette méthode permet le déplacement de la créature sur une position parmi
     * une liste de positions établie à l'avance
     *
     */
    public void deplace() {
        Random rand = new Random();
        List<int[]> listePossible = Positions.see_possibilities(this.pos);

        int[] randomPoint = listePossible.get(rand.nextInt(listePossible.size()));

        this.pos.X = randomPoint[0];
        this.pos.Y = randomPoint[1];
    }

    public boolean deplace(int dX,int dY) {
        this.pos.X += dX;
        this.pos.Y += dY;

        return true;
    }

    /**
     * Méthode qui est appelée lorsque c'est à la créature de jouer son tour
     * Par défault, à chaque tour, la créature se déplace sur une case aléatoirement
     */
    public void joueTour(){
        this.deplace();
    }


    public void modifierPV(int deltaPV){
        this.ptVie += deltaPV;
    }

    public void modifierDegAtt(int modDegAtt){
        this.degAtt += modDegAtt;
    }

    public void modifierPtPar(int modPtPar){
        this.ptPar += modPtPar;
    }

    public void modifierPageAtt(int modPageAtt){
        this.pageAtt += modPageAtt;
    }

    public void modifierPagePar(int modPagePar){
        this.pageAtt += modPagePar;
    }

    public void setPtDeg(int ptDeg){
        this.ptDeg = ptDeg;
    }


}
