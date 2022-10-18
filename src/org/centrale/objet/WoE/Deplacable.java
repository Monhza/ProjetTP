package org.centrale.objet.WoE;

import java.util.List;
import java.util.Random;

/**
 * Interface déplaçable qui donne des méthodes pour les déplacements des entités sur la carte
 */

public interface Deplacable {

    /**
     * Cette méthode permet le déplacement de la créature sur une position parmi
     * une liste de positions établie à l'avance
     */
    public void deplace();

}
