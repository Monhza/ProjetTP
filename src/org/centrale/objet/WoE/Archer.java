package org.centrale.objet.WoE;

import java.util.Random;

/**
 * La classe Archer permet de définir les attributs et les méthodes de ce personnage.
 */

public class Archer extends Personnage implements Combattant {

    //Attributs
    public int nbFleches;

    public String idGraphique = "archer";

    //Constructeurs
    /**
     * Constructeur avec paramètres
     *
     * @param n : Nom de l'archer
     * @param pV : Points de vie
     * @param dA : Dégâts attaque
     * @param pPar : Points de parade
     * @param paAtt : Percentage d'attaque
     * @param paPar : Percentage de parade
     * @param dMax : Distance maximal d'attaque
     * @param p : Position de l'archer
     * @param nbFleches : Nombre de fléches disponibles
     *
     */
    public Archer(World monde, String n, int pV, int dA, int pPar,
                  int paAtt, int paPar, int dMax,  Point2D p, int nbFleches) {

        super(monde, n, pV, dA, pPar, paAtt, paPar, dMax, p);
        this.nbFleches = nbFleches;
    }

    /**
     * Constructeur de recopie
     *
     *
     * @param a : Objet du type Archer qui sera copié
     */
    public Archer(Archer a) {
        super(a);
        this.nbFleches = a.nbFleches;
    }

    /**
     * Constructeur sans paramètres
     */
    public Archer(){
        super();
    }

    //Methodes
    /**
     * Cette methode permet de avoir un combattre soit au corps à corps ou
     * à distance avec l'un des autres creatures dans le monde.
     *
     * @param c : Objet du type Creature avec laquelle l'archer va se battre.
     */
    public boolean combattre(Creature c){
        int Rand;
        int degats;

        //Combat au corps a corps
        if(this.pos.distance(c.pos)<=1 + Point2D.epsilon){
            System.out.println("Combat au corps a corps");

            Rand = tirageAlea.nextInt(100) + 1;

            if(Rand>this.pageAtt){
                System.out.println("Attaque ratée");
                degats = 0;
            }
            else{
                Rand = tirageAlea.nextInt(100)+1;
                System.out.println("Attaque réussie");

                if(Rand>c.pagePar){
                    System.out.println("Dégâts maximaux");
                    degats = -this.degAtt;
                }
                else{
                    System.out.println("Dégâts atténués");

                    degats = -this.degAtt+c.ptPar;
                }
            }
        }

        //Combat a distance
        else if (this.pos.distance(c.pos)>1 && this.pos.distance(c.pos)<this.distAttMax) {
            System.out.println("Combat a distance");

            Rand = tirageAlea.nextInt(100) + 1;

            this.nbFleches -= 1;

            if (Rand > this.pageAtt) {
                System.out.println("Attaque ratée");
                degats = 0;
            } else {
                System.out.println("Attaque réussie");
                degats = -this.degAtt;
            }
        }else{
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

    @Override
    public String getImage() {
        return this.idGraphique;
    }

}
