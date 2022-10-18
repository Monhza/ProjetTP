package org.centrale.objet.WoE;

public class TestPoint2D {

    public static void main(String[] arg) {
        Point2D point1 = new Point2D();
        point1.affiche();

        Point2D point2 = new Point2D(3, 3);
        point2.affiche();

        Point2D point3 = new Point2D(point2);
        point3.affiche();

        System.out.println("\nObtention des parametres d un point par les methodes get...");
        System.out.println("Point_2 : \n" +
                "X : " + point2.getX() + "   Y : " + point2.getY());

        System.out.println("\nChangement des coordonnees du point 2 par les methodes set...");
        point2.setX(5);
        point2.setY(7);
        // Pour l'exemple, je vais modifier les coordonnees deux fois de la meme maniere
        point2.setPosition(5, 7);
        //
        System.out.println("Point_2 : \n" +
                "X : " + point2.getX() + "   Y : " + point2.getY());

        System.out.println("\nAjout de une unite a chaque coordonnee du point 2 par la methode translate...");
        point2.translate(1, 1);
        System.out.println("Point_2 : \n" +
                "X : " + point2.getX() + "   Y : " + point2.getY());


        System.out.println("\nCalcul de la distance entre le point 2 et 3...");
        float distance = point2.distance(point3);
        System.out.println("La distance entre les deux points vaut : \n" +
                distance);

    }
}
