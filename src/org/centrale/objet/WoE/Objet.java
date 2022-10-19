package org.centrale.objet.WoE;

public abstract class Objet extends ElementDeJeu implements ElementGraphique {

    public World monde;

    public Objet(World monde, Point2D p){
        super(p);
        this.monde = monde;
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

    public void interagit(Joueur joueur) {
        this.disparaitCarte();
    }

    // Méthode appelée lorsqu'un objet disparait de la carte
    public void disparaitCarte(){
        this.monde.disparaitreCarte(this);
    }

    public void joueTour(){

    }
}
