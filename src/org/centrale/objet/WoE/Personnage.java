package org.centrale.objet.WoE;

/**
 * Classe de gestion d'un personnage
 * chaque instance correspond à un personnage qui a des caractéristiques propres
 * tels que les points de vie, la position...
 * @author Nicolas Thevenot
 * @version 1.0
 */
public abstract class Personnage extends Creature{

    //Attributs
    public String nom;
    public int distAttMax;

    //constructeurs
    /**
     * Constructeur avec paramètres
     *
     * @param n : Nom du personnage
     * @param pV : Points de vie
     * @param dA : Dégâts attaque
     * @param pPar : Points de parade
     * @param paAtt : Percentage d'attaque
     * @param paPar : Percentage de parade
     * @param dMax : Distance maximal d'attaque
     * @param p : Position du personnage
     *
     */
    public Personnage(World monde, String n, int pV, int dA, int pPar, int paAtt, int paPar, int dMax, Point2D p) {
        super(monde, pV, dA, pPar, paAtt, paPar, p);
        this.nom = n;
        this.distAttMax = dMax;
    }

    /**
     * Constructeur de recopie
     *
     * @param perso : Objet du type Personnage qui sera copié
     */
    public Personnage(Personnage perso) {
        super(perso);
        this.nom = perso.nom;
        this.distAttMax = perso.distAttMax;
    }

    /**
     * Constructeur sans paramètres
     */
    public Personnage(){
        super();
    }

    //Getters et Setters
    /**
     * @return le nom du personnage
     */
    public String getNom(){
        return this.nom;
    }

    /**
     * Set le nom du personnage
     */
    public void setNom(String n) {
        this.nom = n;
    }

    //Methodes
    /**
     * Cette methode permet d'afficher les paramètres du personnage et ses coordonnées
     */
    public void affiche(){

        System.out.println("Parametres du personnage :" +
                "\nptVie = " + this.ptVie +
                "\ndegAtt = " + this.degAtt +
                "\nptPar = " +this.ptPar +
                "\npageAtt  = " + this.pageAtt +
                "\npagePar  = " + this.pagePar);


        System.out.println("\nLes coordonnees du personnage sont :");
        pos.affiche();
    }

    /**
     * Cette methode permet d'afficher les coordonnées du personnage
     */
    public void affiche_position(){

        System.out.println("\nLes coordonnees du personnage sont :");
        pos.affiche();
    }

    public void joueTour(){
        super.joueTour();
    }

    public boolean combattre(Creature c){
        return false;
    }
}