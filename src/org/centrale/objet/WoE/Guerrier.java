package org.centrale.objet.WoE;

/**
 * La classe Guerrier permet de définir les attributs et les méthodes de ce personnage.
 */

public class Guerrier extends Personnage implements Combattant {

    public String idGraphique = "guerrier";

    //Constructeurs
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

    //Methodes
    /**
     * Cette methode permet de avoir un combattre soit au corps à corps ou
     * à distance avec l'un des autres creatures dans le monde.
     *
     * Renvoie true si l'attaque est possible, false sinon
     *
     * @param c : Objet du type Creature avec laquelle le guerrier va se battre.
     */
    public boolean combattre(Creature c){
        int Rand;

        //Combat au corps à corps
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
        else{
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
