package org.centrale.objet.WoE;

import java.util.List;
import java.util.Random;

public class NuageToxique extends Objet implements Deplacable, Combattant{
    public int ptDeg;

    public String idGraphique = "nuage_toxique";

    public NuageToxique(World monde, int ptDeg, Point2D pos){
        super(monde, pos);
        this.ptDeg = ptDeg;
    }

    public void deplace(){
        Random rand = new Random();

        // Le nuage toxique peut se déplacer partout, sauf sur les autres items
        List<int[]> listePoints = Positions.see_possibilities_nuage(this.pos);

        int[] randomElement = listePoints.get(rand.nextInt(listePoints.size()));

        this.pos.setX(randomElement[0]);
        this.pos.setY(randomElement[1]);
    }

    public boolean combattre(Creature c){
        if(this.pos.getX() == c.pos.getX() && this.pos.getY() == c.pos.getY()){
            c.ptVie -= this.ptDeg;
            return true;
        }
        return false;
    };

    public String getImage() {
        return this.idGraphique;
    }

    public void joueTour(){
        this.deplace();
    }

    @Override
    public void interagit(Joueur joueur){
        // Invoquer le super permet de faire disparaitre l'élément de la carte (pas du jeu)
        super.interagit(joueur);

        joueur.modifierPV(-this.ptDeg);
    }
}
