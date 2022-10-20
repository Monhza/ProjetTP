package org.centrale.objet.WoE;

/**
 * Classe mère de tous les objets
 */
public abstract class Objet extends ElementDeJeu implements ElementGraphique {


    public Objet(World monde, Point2D p){
        super(monde, p);
    }
    public Objet() {
        super();
    }

    /**
     * défini le comportement de l'objet s'il est utilisé par un joueur
     * @param joueur
     */
    public void utiliser(Joueur joueur){

    }

    /**
     * Par défaut, lorsque le joueur passe sur un objet, celui ci disparait de la carte
     * @param joueur
     */
    public void interagit(Joueur joueur) {
        this.disparaitCarte();
    }

    /**
     * Méthode appelée lorsqu'un objet disparait de la carte
     */
    public void disparaitCarte(){
        this.monde.disparaitreCarte(this);
    }

    public void joueTour(){

    }
}
