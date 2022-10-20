package org.centrale.objet.WoE;

/**
 * Classe de gestion d'un monstre
 * chaque instance correspond à un monstre qui a des caractéristiques propres
 * tels que les points de vie, la position...
 */
public abstract class Monstre extends Creature {

    //Constructeurs

    /**
     * Constructeur avec paramètres
     *
     * @param pV    : Points de vie
     * @param dA    : Dégâts attaque
     * @param pPar  : Points de parade
     * @param paAtt : Percentage d'attaque
     * @param paPar : Percentage de parade
     * @param p     : Position du monstre
     */
    public Monstre(World monde, int pV, int dA, int pPar, int paAtt, int paPar, Point2D p) {

        super(monde, pV, dA, pPar, paAtt, paPar, p);
    }

    /**
     * Constructeur de recopie
     *
     * @param m : Objet du type Monstre qui sera copié
     */
    public Monstre(Monstre m) {
        super(m);
    }

    /**
     * Constructeur sans paramètres
     */
    public Monstre() {
        super();
    }

    //Getters et Setters

    /**
     * @return les points de vie du Monstre
     */
    public int getPtVie() {
        return this.ptVie;
    }

    /**
     * Set des points de vie
     */
    public void setPtVie(int pv) {
        this.ptVie = pv;
    }

    //Methodes

    /**
     * Méthode qui permet d'afficher les paramètres du monstre et ses coordonnées
     */
    public void affiche() {

        System.out.println("Parametres du monstre :\n" +
                "\nptVie = " + this.ptVie +
                "\ndegAtt = " + this.degAtt +
                "\nptPar = " + this.ptPar +
                "\npageAtt  = " + this.pageAtt +
                "\npagePar  = " + this.pagePar);


        System.out.println("\nLes coordonnees du monstre sont :");
        pos.affiche();
    }

}
