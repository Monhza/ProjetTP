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
        super(monde, p);
        this.ptVie = pV;
        this.degAtt = dA;
        this.ptPar = pPar;
        this.pageAtt = paAtt;
        this.pagePar = paPar;
    }

    /**
     * Constructeur de recopie
     *
     * @param c : Objet du type Creature qui sera copié
     */
    public Creature(Creature c){
        super(c.monde, c.pos);
        this.monde = c.monde;
        this.ptVie = c.ptVie;
        this.degAtt = c.degAtt;
        this.ptPar = c.ptPar;
        this.pageAtt = c.pageAtt;
        this.pagePar = c.pagePar;
    }

    /**
     * Constructeur sans paramètres
     */
    public Creature(){
        super();
    }

    //Méthodes
    public abstract void affiche();

    /**
     * Cette méthode permet le déplacement aléatoire de la créature sur une position parmi
     * une liste de positions établie à l'avance
     *
     * C'est le mode de déplacement par défaut des créatures
     *
     */
    public void deplace() {
        Random rand = new Random();
        List<int[]> listePossible = Positions.see_possibilities(this.pos);

        int[] randomPoint = listePossible.get(rand.nextInt(listePossible.size()));

        this.pos.X = randomPoint[0];
        this.pos.Y = randomPoint[1];
    }


    /**
     * Méthode qui est appelée lorsque c'est à la créature de jouer son tour
     * Par défaut, à chaque tour, la créature se déplace sur une case aléatoirement (probabilité d'un sur 5)
     */
    public void joueTour(){

        // On vérifie si le personnage est en vie
        this.enVie();

        // On fait un tirage pour déterminer le comportement du personnage pendant ce tour
        int choix = tirageAlea.nextInt(5);

        switch (choix) {
            // Si le tirage tombe sur 0, le personnage se déplace
            case 0:
                this.deplace();
        }
    }

    /**
     * Méthode qui vérifie si le personnage est en vie
     */
    public void enVie(){
        if (this.ptVie <=0){
            this.mort();
        }
    }

    /**
     * Méthode appelée pour tuer le personnage et le supprimer de la carte
     */
    public void mort(){
        this.monde.mort(this);
    }


    public void modifierPV(int deltaPV){
        this.ptVie += deltaPV;
        if (this.ptVie < 0){
            this.ptVie = 0;
        }

        System.out.println("Points de vie du " + this.getClass().getSimpleName() + " : " + this.ptVie);
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

    public void setPtDeg(int degAtt){
        this.degAtt = degAtt;
    }


}
