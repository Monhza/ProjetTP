package org.centrale.objet.WoE;

public abstract class ElementDeJeu {
    public String tag;
    public Point2D pos;

    public ElementDeJeu(Point2D pos) {
        this.pos = pos;
    }
    public ElementDeJeu() {
    }
}
