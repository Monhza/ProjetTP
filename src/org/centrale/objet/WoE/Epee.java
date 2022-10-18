package org.centrale.objet.WoE;

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


    public void utiliser(Joueur joueur){
        super.utiliser(joueur);
        joueur.setPtDeg(this.ptDeg);
    }

    @Override
    public void interagit(Joueur joueur){
        // Invoquer le super permet de faire disparaitre l'élément de la carte (pas du jeu)
        super.interagit(joueur);

        joueur.ajoutInventaire(this);
    }

}
