package org.centrale.objet.WoE;

import java.util.Random;

/**
 * La classe Loup permet de définir les attibuts et les méthodes de ce monstre.
 */
public class Loup extends Monstre implements Combattant{
    public String idGraphique = "loup";

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
    public Loup(World monde, int pV, int dA, int pPar, int paAtt, int paPar, Point2D p) {
        super(monde, pV,dA,pPar, paAtt, paPar, p);
    }

    /**
     * Constructeur de recopie
     *
     * @param l : Objet du type Loup qui sera copié
     */
    public Loup(Loup l){
        super(l);
    }

    /**
     * Constructeur sans paramètres
     */
    public Loup(){
        super();
    }

    //Methodes
    /**
     * Cette methode permet de avoir un combattre soit au corps à corps ou
     * à distance avec l'un des autres creatures dans le monde.
     *
     * @param c : Objet du type Creature avec laquelle le loup va se battre.
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
        }else {
            return false;
        }
        return true;
    }

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
