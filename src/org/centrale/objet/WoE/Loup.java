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


    public void joueTour(){
        // On fait un tirage pour déterminer le comportement du personnage pendant ce tour
        int choix = tirageAlea.nextInt(5);

        switch (choix) {
            // Si le tirage tombe sur 0, le personnage se déplace
            case 0:
                this.deplace();
                break;
            case 1:
                System.out.println("Le loup cherche une cible a attaquer...");

                // On utilise une boucle qui vérifie l'attaquabilité des créatures sur la carte
                attaque :{
                    for (Creature c : Positions.crea){
                        // Si une créature est attaquée, l'action est interrompue
                        if (this.combattre(c)){
                            break attaque;
                        }
                    }
                    // Si l'action n'est jamais interrompue, c'est que personne n'était à portée
                    System.out.println("Mais personne n\'est a portee...");
                }
                break;
        }
    }
    /**
     * Cette methode permet de avoir un combattre soit au corps à corps ou
     * à distance avec l'un des autres creatures dans le monde.
     *
     * @param c : Objet du type Creature avec laquelle le loup va se battre.
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
        if (degats >= 0){
            System.out.println("Le personnage ne subit aucun degats");
            degats = 0;
        }
        // On modifie les PV de la créature attaquée
        c.modifierPV(degats);

        return true;
    }

    public String getImage() {
        return this.idGraphique;
    }
}
