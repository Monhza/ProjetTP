package org.centrale.objet.WoE;

import java.security.InvalidParameterException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import  java.util.Scanner;
import  org.centrale.objet.Interface.*;

/**
 * Classe permettant de gérer un joueur humain qui pourra
 */
public class Joueur implements ElementGraphique, Combattant{

    public PanelTouches touchesAction;
    public PanelInventaire inventaireEcran;
    public String typePerso;
    public String nomPerso;
    public Personnage perso;
    public Point2D pos;
    public List<Objet> inventaire;
    public World monde;

    public String idGraphique = "hero";
    public String tag;

    public Joueur(World monde, Point2D pos){

        this.monde = monde;
        this.touchesAction = monde.fenetreJeu.getTouchesAction();
        this.inventaireEcran = monde.fenetreJeu.getInventaireEcran();

        inventaire = new LinkedList<>();

        String[] typesPossibles = {"Archer", "Guerrier"};
        this.choixPerso(typesPossibles);

        switch (this.typePerso){
            case "Guerrier" :
                this.perso = new Guerrier(this.monde, this.nomPerso, 100, 100, 20,
                        100, 100, 100, pos);
                break;

            case "Archer" :
                this.perso = new Archer(this.monde, this.nomPerso, 100, 50, 100,
                        100, 100, 100, pos, 100);
                break;
        }

        this.pos = perso.pos;

        // On ajoute le point du joueur à la liste des créatures sur la carte
        Positions.posCrea.add(this.pos);
    }

    public void joueTour(){
        String[] descriptionAction;

        // On vérifie s'il y a un item sur la case du personnage au début du tour
        // On le fera aussi à la fin
        // Ramasser items prend aussi en compte les nuages toxiques
        this.ramasserItem();

        System.out.println("C'est votre tour...");

        this.touchesAction.initialiseAction();
        // On va créer une boucle qui tournera tant que le joueur n'aura pas effectué une action
        boolean actionEffectuee = false;
        while (!actionEffectuee) {
            if (this.touchesAction.isActionEffectuee()){
                descriptionAction = this.touchesAction.getDescriptionAction();

                actionEffectuee = this.verifieAction(descriptionAction);

            }

            // On met un sleep, sinon rien ne marche
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        // On vérifie s'il y a un item sur la case du personnage à la fin du tour
        this.ramasserItem();
    }

    public void choixPerso(String[] types){
        ConfigurateurPerso test = new ConfigurateurPerso(types);

        while (!test.isOver()){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        this.nomPerso = test.getNom();
        this.typePerso = test.getTypePerso();

        test.closeWin();
    }


    /**
     * Cette méthode donne si une action est "valide" (possible) ou non.
     * Si l'action est valide, la méthode va aussi l'effectuer
     */
    public boolean verifieAction(String[] descriptionAction){

        if (descriptionAction.length < 1){
            throw new InvalidParameterException("La description des action année par le panel touche n'est pas valide");
        }

        switch (descriptionAction[0]){
            // En cas de déplacement, on interprète les données de coordonnées données par le panel touches
            case "deplacement" :
                // Les touches nous donnent le delta des coordonnées souhaitées par l'utilisateur
                String[] stringDelta = descriptionAction[1].split(",");

                int dXvoulu = Integer.parseInt(stringDelta[0]);
                int dYvoulu = Integer.parseInt(stringDelta[1]);

                int Xvoulu = dXvoulu + this.pos.getX();
                int Yvoulu = dYvoulu + this.pos.getY();

                // On essaie le déplacement
                return this.essayerDeplace(Xvoulu,Yvoulu);

            case "attaque" :
                // On récupère la case visée
                String[] cible = descriptionAction[1].split(",");

                int Xcible = Integer.parseInt(cible[0]);
                int Ycible = Integer.parseInt(cible[1]);

                Creature crea = Positions.whatCreature(Xcible, Ycible);

                if (crea != null){
                    return this.combattre(crea);
                }

                return false;

            case "utiliser":
                // On récupère la sélectionnée
                String[] selection = descriptionAction[1].split(",");

                int Xselection = Integer.parseInt(selection[0]);
                int Yselection = Integer.parseInt(selection[1]);

                //Objet item = Positions.whatCreature(Xselection, Yselection);


            default :
                return false;
        }
    }

    /**
     * Cette méthode permet de demander à la classe si un déplacement est possible
     * Si oui, la méthode renvoie true et déplace le personnage sur la case indiquée
     * Si non, la méthode renvoie false et rien ne se passe
     */
    public boolean essayerDeplace(int X, int Y) {
        // Liste des possibilités de déplacement du personnage
        List<int[]> listePossible = Positions.see_possibilities(this.pos);

        for (int[] element : listePossible){
            // Si le déplacement est possible, alors on l'effectue
            if ((X == element[0]) && (Y == element[1])){
                this.pos.setX(X);
                this.pos.setY(Y);

                // On renvoie true pour indiquer que la position du personnage a changé
                return true;
            }
        }
        // Si l'exécution se termine sans trouver que le chemin que veut emprunter l'utilisateur est
        // possible, on renvoie false
        return false;
    }


    /**
     * Méthode appelée à la fin de chaque tour
     * Si le personnage se trouve sur un item, décrit le comportement de celui ci
     */
    public void ramasserItem(){
        Objet item;

        // On vérifie s'il y a un item sur la case du personnage à la fin de son tour
        if (Positions.isItem()[pos.getX()][pos.getY()]){
            item = Positions.whatItem(pos.getX(), pos.getY());

            item.interagit(this);
        }
    }

    @Override
    public boolean combattre(Creature c) {
        return this.perso.combattre(c);
    }

    public String getImage() {
        return idGraphique;
    }

    public int getPV(){
        return this.perso.ptVie;
    }

    /**
     * Change les points de vie du personnage d'un delta passé en paramètre
     * @param deltaPV
     */
    public void modifierPV(int deltaPV){
        this.perso.ptVie += deltaPV;

        System.out.println("Le joueur a maintenant " + this.perso.ptVie + " PV");
    }


    public void modifierDegAtt(int modDegAtt){
        this.perso.modifierDegAtt(modDegAtt);
    }

    public void modifierPtPar(int modPtPar){
        this.perso.modifierPtPar(modPtPar);
    }

    public void modifierPageAtt(int modPageAtt){
        this.perso.modifierPageAtt(modPageAtt);
    }

    public void modifierPagePar(int modPagePar){
        this.perso.modifierPagePar(modPagePar);
    }

    public void setPtDeg(int ptDeg){
        this.perso.setPtDeg(ptDeg);
    }

    public void ajoutInventaire(Objet item){
        inventaire.add(item);

        this.inventaireEcran.chargerElement(item.getImage(), item.tag);
    }

    public void retirerInventaire(Objet item){
        inventaire.remove(item);

        this.inventaireEcran.supprimerElement(item.tag);
    }


}
