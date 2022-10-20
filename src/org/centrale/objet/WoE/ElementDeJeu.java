package org.centrale.objet.WoE;

/**
 * Superclasse Element de jeu
 * Contient la position et un tag qui permet d'identifier l'élément
 */
public abstract class ElementDeJeu {
    public String tag;
    public Point2D pos;

    public ElementDeJeu(Point2D pos) {
        this.pos = pos;
    }
    public ElementDeJeu() {
    }
}
