package org.centrale.objet.WoE;

/**
 * Classe nourriture
 *
 * Modifie les statistiques du joueur quand consommé
 */
public class Nourriture extends Objet {

    public int modDegAtt;
    public int modPtPar;
    public int modPageAtt;
    public int modPagePar;

    public String idGraphique = "pomme";

    public Nourriture(World monde, int dA, int pPar, int paAtt, int paPar, Point2D p){
        super(monde, p);
        this.modDegAtt = dA;
        this.modPtPar = pPar;
        this.modPageAtt = paAtt;
        this.modPagePar = paPar;
    }


    public String getImage() {
        return this.idGraphique;
    }


    /**
     * Appelée lorsqu'un joueur consomme la nourriture
     * @param joueur
     */
    public void utiliser(Joueur joueur){
        super.utiliser(joueur);
        joueur.modifierDegAtt(this.modDegAtt);
        joueur.modifierPtPar(this.modPtPar);
        joueur.modifierPageAtt(this.modPageAtt);
        joueur.modifierPagePar(this.modPagePar);
    }

    /**
     * Appelé lorsque le joueur marche sur la nourriture
     * @param joueur
     */
    @Override
    public void interagit(Joueur joueur){
        // Invoquer le super permet de faire disparaitre l'élément de la carte (pas du jeu)
        super.interagit(joueur);

        joueur.ajoutInventaire(this);
    }
}
