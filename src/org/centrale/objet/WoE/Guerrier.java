package org.centrale.objet.WoE;

/**
 * La classe Guerrier permet de définir les attributs et les méthodes de ce personnage.
 */

public class Guerrier extends Personnage implements Combattant {

    public String idGraphique = "guerrier";

    /**
     * Constructeur avec paramètres
     *
     * @param n : Nom du guerrier
     * @param pV : Points de vie
     * @param dA : Degats attaque
     * @param pPar : Points de parade
     * @param paAtt : Percentage d'attaque
     * @param paPar : Percentage de parade
     * @param dMax : Distance maximal d'attaque
     * @param p : Position du guerrier
     *
     */
    public Guerrier(World monde, String n, int pV, int dA, int pPar,
                  int paAtt, int paPar, int dMax,  Point2D p) {

        super(monde, n, pV, dA, pPar, paAtt, paPar, dMax, p);
    }

    /**
     * Constructeur de recopie
     *
     * @param g : Objet du type Guerrier qui sera copié
     */
    public Guerrier(Guerrier g) {

        super(g);
    }


    /**
     * Constructeur sans paramètres
     */
    public Guerrier(){
        super();
    }


    /**
     * Cette methode permet d'avoir un combat soit au corps à corps ou
     * à distance avec l'un des autres creatures dans le monde.
     *
     * Renvoie true si l'attaque est possible, false sinon
     *
     * @param c : Objet du type Creature avec laquelle le guerrier va se battre.
     */
    public boolean combattre(Creature c){

        // On empêche la créature de s'attaquer elle-même
        if (c == this){
            return false;
        }

        int Rand;
        int degats;

        //Combat au corps a corps
        if(this.pos.distance(c.pos)<=1 + Point2D.epsilon){
            System.out.println("Combat au corps a corps");

            Rand = tirageAlea.nextInt(100) + 1;

            if(Rand>this.pageAtt){
                System.out.println("Attaque ratee");
                degats = 0;
            }
            else{
                Rand = tirageAlea.nextInt(100)+1;
                System.out.println("Attaque reussie");

                if(Rand>c.pagePar){
                    System.out.println("Degats maximaux");
                    degats = -this.degAtt;
                }
                else{
                    System.out.println("Degats attenues");

                    degats = -this.degAtt+c.ptPar;
                }
            }
        } else{
            return false;
        }

        // On vérifie qu'on ne "soigne" pas l'ennemi en l'attaquant en cas de parade importante
        if (degats >=0){
            System.out.println("Le personnage ne subit aucun dégats");
            degats = 0;
        }
        c.modifierPV(degats);
        return true;
    }

    public String getImage() {
        return this.idGraphique;
    }
}
