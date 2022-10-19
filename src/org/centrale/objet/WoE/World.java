package org.centrale.objet.WoE;

import org.centrale.objet.Interface.FenetreJeu;
import org.centrale.objet.Interface.PanelJeu;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;


/**
 * La classe World permet de générer le monde dans lequel nos personnages vont pouvoir interagir
 *
 * Elle contient une méthode qui permet de placer les personnages et les mobs sur un point
 * aléatoire sur la carte tout en s'assurant qu'aucun personnage ne se situera sur le même point
 *
 * @author Nicolas Thevenot
 * @version 1.0
 */
public class World {

    //Pour l'instant, on crée les personnages ici
    //Plus tard, il faudra le faire à l'extérieur de la classe

    public List<Personnage> pers;
    public List<Monstre> bugs;
    public List<Objet> items;
    public Joueur player;

    public Carte cartejeu;
    public FenetreJeu fenetreJeu;
    public PanelJeu affichageJeu;

    public static int tailleParDefaut = 15;
    public final int TEMPS_REFRESH = 50;

    /**
     * Constructeur de la méthode
     */
    public World() {
        pers = new ArrayList<>();
        bugs = new ArrayList<>();
        items = new LinkedList<>();

        // On crée une fenêtre de jeu qui se rafraichi toutes les 200 millisecondes
        this.fenetreJeu = new FenetreJeu(tailleParDefaut,tailleParDefaut, TEMPS_REFRESH);
        this.affichageJeu = this.fenetreJeu.getAffichageJeu();
    }


    /**
     * Méthode qui permet de générer le monde
     *
     * @param nbArc Nombre d'Archers crées
     * @param nbPay Nombre de Paysans crées
     * @param nbLap Nombre de Lapins crées
     * @param nbGue Nombre de Guerriers crées
     * @param nbLou Nombre de Loups crées
     */
    public void creerMondeAlea(int nbArc, int nbPay, int nbLap, int nbGue, int nbLou, int nbItem){
        // On génère la population sur la carte
        this.genererPop(nbArc, nbPay, nbLap, nbGue, nbLou, nbItem);

        // On génère la carte avec tous les personnages dessus
        this.cartejeu = new Carte(this, this.fenetreJeu);
    }


    /**
     * Cette fonction génère toute la population de la carte
     * C'est à dire un joueur et un nombre défini de créatures
     * @param nbArc
     * @param nbPay
     * @param nbLap
     * @param nbGue
     * @param nbLou
     */
    public void genererPop(int nbArc, int nbPay, int nbLap, int nbGue, int nbLou, int nbItem){
        ArrayList<Point2D> listePoints = Positions.genererPointsAlea(nbArc+nbPay+nbLap+nbGue+nbLou+nbItem+1
                , tailleParDefaut, tailleParDefaut);

        // On crée le personnage du joueur
        this.player = new Joueur(this, listePoints.get(0));

        // On crée tous les pnj qui vont peupler la carte
        int j = 1;

        for (int i=0 ; i <nbArc ; i++){
            this.creerCreature("Archer", listePoints.get(j));
            j++;
        }

        for (int i=0 ; i <nbPay ; i++){
            this.creerCreature("Paysan", listePoints.get(j));
            j++;
        }

        for (int i=0 ; i <nbLap ; i++){
            this.creerCreature("Lapin", listePoints.get(j));
            j++;
        }

        for (int i=0 ; i <nbGue ; i++){
            this.creerCreature("Guerrier", listePoints.get(j));
            j++;
        }

        for (int i=0 ; i <nbLou ; i++){
            this.creerCreature("Loup", listePoints.get(j));
            j++;
        }

        for (int i=0 ; i<nbItem ; i++){
            this.creerItem(listePoints.get(j));
            j++;
        }
    }

    /**
     * Cette méthode nous permet de controler la création d'une créature
     * @param type : type de la créature
     * @param pos : position de la créature
     */
    public void creerCreature(String type, Point2D pos){
        Creature newPerso;

        switch (type){
            case "Paysan":
                newPerso = new Paysan(this, "Fermier", 100, 50, 50,
                        50, 50, 50, pos);
                pers.add((Paysan) newPerso);
                break;
            case "Archer":
                newPerso = new Archer(this, "Robin", 100, 50, 50,
                        50, 50, 50, pos, 100);
                pers.add((Archer) newPerso);
                break;
            case "Guerrier":
                newPerso = new Guerrier(this, "Billie", 100, 50, 50,
                        50, 50, 50, pos);
                pers.add((Guerrier) newPerso);
                break;
            case "Loup":
                newPerso = new Loup(this, 100, 50, 50,
                        50, 50, pos);
                bugs.add((Loup) newPerso);
                break;
            case "Lapin":
                newPerso = new Lapin(this, 100, 50, 50,
                        50, 50, pos);
                bugs.add((Lapin) newPerso);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }

        // On ajoute le point de la créature à la liste des positions
        Positions.posCrea.add(newPerso.pos);
        Positions.crea.add(newPerso);
    }

    public void creerItem(Point2D pos){
        Random rand = new Random();

        int opt;
        Objet newObjet;

        opt = rand.nextInt(4) + 1;

        switch (opt) {
            case 1:
                newObjet = new PotionSoin(this, 50, 3, pos);
                items.add(newObjet);
                break;

            case 2:
                newObjet = new Epee(this, "Scalibur", 200, pos);
                items.add(newObjet);
                break;

            case 3:
                newObjet = new Nourriture(this, 10,10,10,10, pos);
                items.add(newObjet);
                break;

            case 4:
                newObjet = new NuageToxique(this, 30, pos);
                items.add(newObjet);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + opt);
        }

        Positions.posItem.add(newObjet.pos);
        Positions.item.add(newObjet);
    }

    /**
     * Méthode qui permet au joueur de choisir entre se déplacer ou combattre
     */
    public void tourDeJeu(){
        player.joueTour();

        // On crée une copie de la liste originelle à chaque fois pour éviter le
        // déclenchement d'une exception
        for (Monstre bug : new ArrayList<>(bugs)){
            bug.joueTour();
        }

        for (Personnage perso : new ArrayList<>(pers)){
            perso.joueTour();
        }

        for (Objet item : new ArrayList<>(items)){
            item.joueTour();
        }
    }

    /**
     * Méthode appelée à la mort d'une créature
     * @param c
     */
    public void mort(Creature c){
        if (c instanceof Monstre){
            this.bugs.remove(c);
        }else if(c instanceof Personnage){
            this.pers.remove(c);
        }
        this.disparaitreCarte(c);
    }

    /**
     * Méthode qui fait disparaitre un objet de la carte
     * La méthode fait aussi disparaitre l'élément des listes de World
     */
    public void disparaitreCarte(ElementDeJeu element){
        if (element instanceof Creature) {
            Positions.posCrea.remove(element.pos);
            Positions.crea.remove(element);
            if (element instanceof Monstre){
                this.bugs.remove(element);
            }else {
                this.pers.remove(element);
            }

        } else if (element instanceof Objet) {
            Positions.posItem.remove(element.pos);
            Positions.item.remove(element);

            this.items.remove(element);
        }

        this.affichageJeu.supprimerElement(element.tag);
    }
}
