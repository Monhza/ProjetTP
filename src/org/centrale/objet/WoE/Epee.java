package org.centrale.objet.WoE;

/**
 * Objet épée
 *
 * L'épée permet de modifier les points de dégâts infligés lors d'une attaque
 */
public class Epee extends Objet{

    public String nom;
    public int ptDeg;

    public String idGraphique = "epee";

    public Epee(World monde, String nom, int ptDeg, Point2D p) {
        super(monde, p);
        this.nom = nom;
        this.ptDeg = ptDeg;
    }


    public String getImage() {
        return this.idGraphique;
    }


    /**
     * Appelée lorsqu'un joueur utilise l'épée
     * @param joueur
     */
    public void utiliser(Joueur joueur){
        super.utiliser(joueur);
        joueur.setPtDeg(this.ptDeg);
    }

    /**
     * Appelé lorsque le joueur marche sur l'épée
     * @param joueur
     */
    @Override
    public void interagit(Joueur joueur){
        // Invoquer le super permet de faire disparaitre l'élément de la carte (pas du jeu)
        super.interagit(joueur);

        joueur.ajoutInventaire(this);
    }

}
