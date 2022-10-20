package org.centrale.objet.WoE;

/**
 * La classe PotionSoin permet de générer des potions qui restaurent les point de vie des protagonistes.
 *
 */

public class PotionSoin extends Objet{

    //Attributs
    public int soin; //Le nombre de points de vie qui sont restaurés par la potion
    public int quantite; //La quantité de potion que contient la fiole

    public String idGraphique = "potion";

    /**
     * Constructeur avec paramètres
     *
     * @param  soin : Nombre de points de vie restaurés par la potion
     * @param quantite : Nombre de potions
     * @param p : Position de la position
     */
    public PotionSoin(World monde, int soin, int quantite, Point2D p) {
        super(monde, p);
        this.soin = soin;
        this.quantite = quantite;
    }

    /**
     * Contructeur sans paramètres
     *
     * @return Position du parit
     */
    public Point2D getPos() {
        return pos;
    }

    public String getImage() {
        return this.idGraphique;
    }


    /**
     * défini le comportement de la potion s'il est utilisé par un joueur
     * @param joueur
     */
    public void utiliser(Joueur joueur){
        super.utiliser(joueur);
        joueur.modifierPV(this.soin);
    }

    /**
     * Méthode appelée lorsque le joueur passe sur le point de la carte associé à l'objet
     * @param joueur
     */
    @Override
    public void interagit(Joueur joueur){
        // Invoquer le super permet de faire disparaitre l'élément de la carte (pas du jeu)
        super.interagit(joueur);

        joueur.ajoutInventaire(this);
    }
}
