package org.centrale.objet.WoE;

/**
 * La classe Paysan permet de définir les attibuts et les méthodes de ce personnage.
 */
public class Paysan extends Personnage {

    public String idGraphique = "fermier";


    //Constructeurs

    /**
     * Constructeur avec paramètres
     *
     * @param n     : Nom du paysan
     * @param pV    : Points de vie
     * @param dA    : Dégâts attaque
     * @param pPar  : Points de parade
     * @param paAtt : Percentage d'attaque
     * @param paPar : Percentage de parade
     * @param dMax  : Distance maximal d'attaque
     * @param p     : Position du personnage
     */
    public Paysan(World monde, String n, int pV, int dA, int pPar,
                  int paAtt, int paPar, int dMax, Point2D p) {

        super(monde, n, pV, dA, pPar, paAtt, paPar, dMax, p);

    }

    /**
     * Constructeur de recopie
     *
     * @param a : Objet du type Paysan qui sera copié
     */
    public Paysan(Paysan a) {

        super(a);

    }

    /**
     * Constructeur sans paramètres
     */
    public Paysan() {
        super();
    }

    public String getImage() {
        return this.idGraphique;
    }


}
