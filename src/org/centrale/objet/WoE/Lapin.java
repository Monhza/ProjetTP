package org.centrale.objet.WoE;

/**
 * La classe Lapin permet de définir les attibuts et les méthodes de ce monstre.
 */
public class Lapin extends Monstre{

    public String idGraphique = "lapin";


    //Constructeurs
    /**
     * Constructeur avec paramètres
     *
     * @param pV : Points de vie
     * @param dA : Dégâts attaque
     * @param pPar : Points de parade
     * @param paAtt : Percentage d'attaque
     * @param paPar : Percentage de parade
     * @param p : Position du monstre
     *
     */
    public Lapin(World monde, int pV, int dA, int pPar, int paAtt, int paPar, Point2D p) {
        super(monde, pV,dA,pPar, paAtt, paPar, p);

    }

    /**
     * Constructeur de recopie
     *
     * @param l : Objet du type Lapin qui sera copié
     */
    public Lapin(Lapin l){
        super(l);
    }

    /**
     * Constructeur sans paramètres
     */
    public Lapin(){
        super();
    }

    public String getImage() {
        return this.idGraphique;
    }



}
