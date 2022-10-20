package org.centrale.objet.WoE;

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Cette classe regroupe des méthodes statiques utiles dans notre programme
 *
 * Elle ne devrait pas exister sous cette forme, cette classe sera refondue dans une version future (ou pas)
 */
public class Positions {

    // Liste qui contient toutes les positions des créatures
    public static List<Point2D> posCrea = new LinkedList<>();
    public static List<Point2D> posItem = new LinkedList<>();

    public static List<Creature> crea = new LinkedList<>();
    public static List<Objet> item = new LinkedList<>();


    /**
     * Méthode qui donne les cases non occupées autour d'une case
     * @param pointInitial : Point autour duquel on va vérifier la non-présence de créatures
     * @return Liste des coordonnées des cases non occupées
     */
    public static List<int[]> see_possibilities(Point2D pointInitial){

        List<int[]> pointsPossibles = new ArrayList<>();

        int Xinit = pointInitial.getX();
        int Yinit = pointInitial.getY();
        int indX = -1;
        int indY = -1;

        int Xcons;
        int Ycons;

        boolean condInit;
        boolean condPresence;

        boolean[][] matPres = Positions.is_creature();

        // On crée tous les points autour du point considéré
        while (indX <= 1){
            while (indY <= 1){

                Xcons = Xinit + indX;
                Ycons = Yinit + indY;

                // On vérifie que l'on ajoute pas le point initial à la liste
                // de possibilités
                condInit = !(Xcons==Xinit && Ycons==Yinit);

                // On vérifie qu'il n'y a pas de créature sur la case visée
                // on attrape l'exception qui signifie que le point est hors carte
                try {
                    condPresence = !matPres[Xcons][Ycons];
                }catch (ArrayIndexOutOfBoundsException e){
                    condPresence = false;
                }

                if (condInit && condPresence){
                    int[] coord = {Xcons, Ycons};

                    pointsPossibles.add(coord);
                }
                indY++;
            }
            indY = -1;
            indX++;
        }

        return pointsPossibles;
    }



    /**
     * Méthode qui donne les possiblités de déplacement pour un éventuel nuage
     * @param pointInitial : Point autour duquel on va vérifier la non-présence d'item autour
     * @return Liste des coordonnées des cases non occupées
     */
    public static List<int[]> see_possibilities_nuage(Point2D pointInitial){

        List<int[]> pointsPossibles = new ArrayList<>();

        int Xinit = pointInitial.getX();
        int Yinit = pointInitial.getY();
        int indX = -1;
        int indY = -1;

        int Xcons;
        int Ycons;

        boolean condInit;
        boolean condPresence;

        boolean[][] matPres = Positions.isItem();

        // On crée tous les points autour du point considéré
        while (indX <= 1){
            while (indY <= 1){

                Xcons = Xinit + indX;
                Ycons = Yinit + indY;

                // On vérifie que l'on ajoute pas le point initial à la liste
                // de possibilités
                condInit = !(Xcons==Xinit && Ycons==Yinit);

                // On vérifie qu'il n'y a pas de créature sur la case visée
                // on attrape l'exception qui signifie que le point est hors carte
                try {
                    condPresence = !matPres[Xcons][Ycons];
                }catch (ArrayIndexOutOfBoundsException e){
                    condPresence = false;
                }

                if (condInit && condPresence){
                    int[] coord = {Xcons, Ycons};

                    pointsPossibles.add(coord);
                }
                indY++;
            }
            indY = -1;
            indX++;
        }

        return pointsPossibles;
    }



    /**
     * Méthode qui donne les cases occupées par des creatures
     *
     * @return une matrice de booleen representant les creatures
     */
    public static boolean[][] is_creature(){
        boolean[][] matPresence = new boolean[World.tailleParDefaut][World.tailleParDefaut];

        for (Point2D point : Positions.posCrea){
            matPresence[point.getX()][point.getY()] = true;
        }

        return matPresence;
    }


    /**
     * Méthode qui donne les cases occupées par des items
     *
     * @return vrai (true) s'il y a un item sur la case
     */
    public static boolean[][] isItem(){
        boolean[][] matPresence = new boolean[World.tailleParDefaut][World.tailleParDefaut];

        for(Point2D point : Positions.posItem){
            matPresence[point.getX()][point.getY()] = true;
        }

        return matPresence;
    }

    /**
     * Pointe sur l'Item présent sur la case sélectionnée
     * @param x
     * @param y
     * @return
     */
    public static Objet whatItem(int x, int y){

        for (Objet o : item){
            if(x == o.pos.getX() && y == o.pos.getY()){
                return o;
            }
        }
        return null;
    }

    /**
     * Pointe sur la Créature présente sur la case sélectionnée
     * @param x
     * @param y
     * @return
     */
    public static Creature whatCreature(int x, int y){

        for (Creature c : crea){
            if(x == c.pos.getX() && y == c.pos.getY()){
                return c;
            }
        }
        return null;
    }



    /**
     * Cette méthode permet de générer une liste de points qui sont strictement différents
     * les uns des autres
     *
     * @param nbPoints : Nombre de points à générer
     * @param xMax : Taille max x de la zone à couvrir
     * @param yMax : Taille max y de la zone à couvrir
     */
    static ArrayList<Point2D> genererPointsAlea(int nbPoints, int xMax, int yMax){
        //On crée nbPoints points qui sont les positions de nos personnages
        //Les personnages ne doivent pas être situés sur les mêmes points

        Random generateurAleatoire = new Random();

        Point2D pointTemp;
        ArrayList<Point2D> listePointsTemp = new ArrayList<>();
        int Xtemp;
        int Ytemp;

        int i = 0;
        while (i<nbPoints){

            Xtemp = generateurAleatoire.nextInt(xMax);
            Ytemp = generateurAleatoire.nextInt(yMax);

            pointTemp = new Point2D(Xtemp,Ytemp);


            boolean collision = false;
            for (int j=0; j<i; j++) {
                Point2D pointTempCompare = listePointsTemp.get(j);
                if (pointTempCompare.equals(pointTemp)) {
                    collision = true;
                    break;
                }
            }

            if (!collision){
                listePointsTemp.add(pointTemp);
                i++;
            }

        }
        return listePointsTemp;
    }
}
