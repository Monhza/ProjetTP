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

        //Combat au corps a corps
        if(this.pos.distance(c.pos)<=1 + Point2D.epsilon){
            System.out.println("Combat au corps a corps");

            Rand = tirageAlea.nextInt(100) + 1;

            if(Rand>this.pageAtt){
                System.out.println("Attaque ratée");
            }
            else{
                Rand = tirageAlea.nextInt(100)+1;
                System.out.println("Attaque réussie");

                if(Rand>c.pagePar){
                    System.out.println("Dégâts maximaux");
                    c.ptVie-=this.degAtt;
                }
                else{
                    System.out.println("Dégâts atténués");
                    c.ptVie-=this.degAtt-c.ptPar;
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
            } else {
                System.out.println("Attaque réussie");
                c.ptVie -= this.degAtt;
            }
        }else{
            return false;
        }
        return true;
    }

    @Override
    public String getImage() {
        return this.idGraphique;
    }

    @Override
    public void joueTour() {
        // On fait un tirage pour déterminer le comportement du personnage pendant ce tour
        int choix = tirageAlea.nextInt(5);

        switch (choix) {
            // Si le tirage tombe sur 0, le personnage se déplace
            case 0:
                super.joueTour();
        }
    }
}
