package org.centrale.objet.WoE;

import org.centrale.objet.Interface.ConfigurateurPerso;
import org.centrale.objet.Interface.PanelInventaire;
import org.centrale.objet.Interface.PanelTouches;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.List;

/**
 * Classe permettant de gérer un joueur humain qui pourra effectuer des actions pendant son tour
 */
public class Joueur implements ElementGraphique, Combattant {

    public PanelTouches touchesAction;
    public PanelInventaire inventaireEcran;
    public String typePerso;
    public String nomPerso;
    public Personnage perso;
    public Point2D pos;
    public HashMap<String, Objet> inventaire;
    public World monde;

    public String idGraphique = "hero";
    public String tag;

    public Joueur(World monde, Point2D pos) {

        this.monde = monde;
        this.touchesAction = monde.fenetreJeu.getTouchesAction();
        this.inventaireEcran = monde.fenetreJeu.getInventaireEcran();

        inventaire = new HashMap<>();

        // On liste les classes possibles par le joueur
        String[] typesPossibles = {"Archer", "Guerrier"};

        // On appelle la méthode qui permet au joueur de choisir le personnage
        this.choixPerso(typesPossibles);

        // Une fois le personnage choisi, on l'initialise
        switch (this.typePerso) {
            case "Guerrier":
                this.perso = new Guerrier(this.monde, this.nomPerso, 100, 90, 50,
                        50, 50, 50, pos);
                break;

            case "Archer":
                this.perso = new Archer(this.monde, this.nomPerso, 100, 60, 50,
                        50, 50, 50, pos, 100);
                break;
        }

        this.pos = perso.pos;

        // On ajoute le point du joueur à la liste des créatures sur la carte
        Positions.posCrea.add(this.pos);

        // On lie ce joueur au panneau qui affiche les statistiques
        // cela nous permet d'afficher certaines caractéristiques du personnage
        this.monde.fenetreJeu.setStats(this);
    }

    /**
     * Classe appelée au tour du joueur
     */
    public void joueTour() {
        String[] descriptionAction;

        // On vérifie s'il y a un item sur la case du personnage au début du tour
        // On le fera aussi à la fin
        // Ramasser items prend aussi en compte les nuages toxiques
        this.ramasserItem();

        this.touchesAction.initialiseAction();
        // On va créer une boucle qui tournera tant que le joueur n'aura pas effectué une action
        boolean actionEffectuee = false;
        while (!actionEffectuee) {

            // On est à l'écoute de l'entrée d'un ordre donné grâce aux touches présentes sur l'interface
            if (this.touchesAction.isActionEffectuee()) {
                // On récupère la commande
                descriptionAction = this.touchesAction.getDescriptionAction();

                // La commande est vérifiée et exécutée si valide
                actionEffectuee = this.verifieAction(descriptionAction);
            }

            // On met un sleep, sinon rien ne marche
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        // On vérifie s'il y a un item sur la case du personnage à la fin du tour
        this.ramasserItem();
    }

    /**
     * Méthode appelée à l'initialisation du personnage pour afficher un panneau de sélection au joueur
     *
     * @param types
     */
    public void choixPerso(String[] types) {
        ConfigurateurPerso fenetreConfig = new ConfigurateurPerso(types);

        while (!fenetreConfig.isOver()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        this.nomPerso = fenetreConfig.getNom();
        this.typePerso = fenetreConfig.getTypePerso();

        // On ferme la fenêtre après la décision
        fenetreConfig.closeWin();
    }


    /**
     * Cette méthode donne si une action est "valide" (possible) ou non.
     * Si l'action est valide, la méthode va aussi l'effectuer
     */
    public boolean verifieAction(String[] descriptionAction) {

        if (descriptionAction.length < 1) {
            throw new InvalidParameterException("La description des action année par le panel touche n'est pas conforme");
        }

        switch (descriptionAction[0]) {
            // En cas de déplacement, on interprète les données de coordonnées données par le panel touches
            case "deplacement":
                // Les touches nous donnent le delta des coordonnées souhaitées par l'utilisateur
                String[] stringDelta = descriptionAction[1].split(",");

                int dXvoulu = Integer.parseInt(stringDelta[0]);
                int dYvoulu = Integer.parseInt(stringDelta[1]);

                int Xvoulu = dXvoulu + this.pos.getX();
                int Yvoulu = dYvoulu + this.pos.getY();

                // On essaie le déplacement
                return this.essayerDeplace(Xvoulu, Yvoulu);

            case "attaque":
                // On récupère la case visée
                String[] cible = descriptionAction[1].split(",");

                int Xcible = Integer.parseInt(cible[0]);
                int Ycible = Integer.parseInt(cible[1]);

                // On trouve quelle créature est présente sur la carte
                Creature crea = Positions.whatCreature(Xcible, Ycible);

                if (crea != null) {
                    return this.combattre(crea);
                }

                return false;

            case "utiliser":
                // On récupère le tag de l'item sélectionné
                String tagItem = descriptionAction[1];

                // On l'utilise, la méthode renvoie true si l'exécution s'est bien déroulé
                return utiliserDepuisInventaire(tagItem);


            default:
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

        for (int[] element : listePossible) {
            // Si le déplacement est possible, alors on l'effectue
            if ((X == element[0]) && (Y == element[1])) {
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
    public void ramasserItem() {
        Objet item;

        // On vérifie s'il y a un item sur la case du personnage à la fin de son tour
        if (Positions.isItem()[pos.getX()][pos.getY()]) {
            item = Positions.whatItem(pos.getX(), pos.getY());

            item.interagit(this);
        }
    }

    // Si le personnage combat, on appelle la méthode associée à la classe de son personnage
    @Override
    public boolean combattre(Creature c) {
        return this.perso.combattre(c);
    }

    public String getImage() {
        return idGraphique;
    }

    public int getPV() {
        return this.perso.ptVie;
    }

    /**
     * Change les points de vie du personnage d'un delta passé en paramètre
     *
     * @param deltaPV
     */
    public void modifierPV(int deltaPV) {
        this.perso.ptVie += deltaPV;

        System.out.println("Le joueur a maintenant " + this.perso.ptVie + " PV");
    }


    public void modifierDegAtt(int modDegAtt) {
        this.perso.modifierDegAtt(modDegAtt);
    }

    public void modifierPtPar(int modPtPar) {
        this.perso.modifierPtPar(modPtPar);
    }

    public void modifierPageAtt(int modPageAtt) {
        this.perso.modifierPageAtt(modPageAtt);
    }

    public void modifierPagePar(int modPagePar) {
        this.perso.modifierPagePar(modPagePar);
    }

    public void setPtDeg(int ptDeg) {
        this.perso.setPtDeg(ptDeg);
    }

    public void ajoutInventaire(Objet item) {
        inventaire.put(item.tag, item);

        this.inventaireEcran.chargerElement(item.getImage(), item.tag);
    }

    public void retirerInventaire(String tag) {
        inventaire.remove(tag);

        this.inventaireEcran.supprimerElement(tag);
    }

    /**
     * Classe appelée lorsque le joueur veut utiliser un élément dans l'inventaire
     * On l'appelle grâce à son tag, le tag est donné par la fenêtre de jeu
     *
     * @param tag
     * @return
     */
    public boolean utiliserDepuisInventaire(String tag) {

        if (tag == null) {
            System.out.println("Il n'y a pas d'item sur la case sélectionnée");
            return false;
        }

        Objet item = inventaire.get(tag);

        item.utiliser(this);

        this.retirerInventaire(tag);

        return true;
    }


}
