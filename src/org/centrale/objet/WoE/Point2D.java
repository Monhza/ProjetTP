package org.centrale.objet.WoE;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * Classe qui représente une position sur la carte
 */
public class Point2D {

    static double epsilon = pow(10, -3);
    //Attributs
    public int X;
    public int Y;
    //boolean qui nous informe si le point doit être affiché sur la carte ou non
    public boolean presentSurCarte = true;

    /**
     * Constructeur sans paramètres
     */
    public Point2D() {

    }

    /**
     * Constructeur avec paramètres
     *
     * @param X : Coordonnée en X
     * @param Y : Coordonnée en Y
     */
    public Point2D(int X, int Y) {
        this.X = X;
        this.Y = Y;

    }

    /**
     * Constructeur de recopie
     *
     * @param point : Objet du type Point2D qui sera copié
     */
    public Point2D(Point2D point) {
        this.X = point.X;
        this.Y = point.Y;

    }


    //Methodes

    /**
     * Cette methode permet de comparer un objet passé sur paramètre
     */
    public boolean equals(Object o) {

        if (o == this) {
            return true;
        }

        if (!(o instanceof Point2D)) {
            return false;
        }

        Point2D p = (Point2D) o;

        return ((this.X == p.X) && (this.Y == p.Y));
    }

    //Getters et Setters
    public int getX() {
        return this.X;
    }

    public void setX(int X) {

        this.X = X;
    }

    public int getY() {
        return this.Y;
    }

    public void setY(int Y) {
        this.Y = Y;
    }

    public void setPosition(int X, int Y) {
        this.setX(X);
        this.setY(Y);
    }

    //Methodes

    /**
     * Cette méthode permet de déplacer le point avec un delta dX et dY
     *
     * @param dX : Deplacement en x.
     * @param dY : Deplacement en y.
     */
    public void translate(int dX, int dY) {
        this.setX(dX + this.X);
        this.setY(dY + this.Y);
    }

    /**
     * Cette methode permet d'afficher les coordonnées du point
     */
    public void affiche() {
        System.out.println("[" + this.X + " ; " + this.Y + "]");
    }

    /**
     * Cette methode permet de calculer la distance existante entre un point passé sur paramètre
     * et le point de cette classe
     *
     * @param point : point jusqu'auquel la distance doit être calculée
     */
    public float distance(Point2D point) {
        double distance;

        distance = sqrt((float) ((this.Y - point.Y) * (this.Y - point.Y)
                + (this.X - point.X) * (this.X - point.X)));

        return (float) (distance);
    }
}