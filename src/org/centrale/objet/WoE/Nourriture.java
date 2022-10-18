package org.centrale.objet.WoE;

/**
 *
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

    public void mangerNourriture(Creature c){

        if(this.pos.equals(c.pos)){
            c.degAtt += this.modDegAtt;
            c.ptPar += this.modPtPar;
            c.pageAtt += this.modPageAtt;
            c.pagePar += this.modPagePar;
        }
    }

    public String getImage() {
        return this.idGraphique;
    }


    public void utiliser(Joueur joueur){
        super.utiliser(joueur);
        joueur.modifierDegAtt(this.modDegAtt);
        joueur.modifierPtPar(this.modPtPar);
        joueur.modifierPageAtt(this.modPageAtt);
        joueur.modifierPagePar(this.modPagePar);
    }

    @Override
    public void interagit(Joueur joueur){
        // Invoquer le super permet de faire disparaitre l'élément de la carte (pas du jeu)
        super.interagit(joueur);

        joueur.ajoutInventaire(this);
    }

}
