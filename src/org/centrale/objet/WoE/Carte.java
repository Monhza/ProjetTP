package org.centrale.objet.WoE;

import org.centrale.objet.Interface.FenetreJeu;
import org.centrale.objet.Interface.PanelJeu;

public class Carte {
    public World monde;
    public PanelJeu affichageJeu;

    /**
     * Constructeur de la carte de jeu
     * @param monde : Monde à afficher, de la classe World
     * @param affichageJeu : Panel qui gère l'interface graphique
     */
    public Carte(World monde, FenetreJeu affichageJeu) {
        this.monde = monde;
        this.affichageJeu = affichageJeu.getAffichageJeu();

        this.chargerElements();
    }

    /**
     * Méthode appelée dasn le constructeur qui permet de charger tous les éléments sur la carte
     */
    public void chargerElements(){

        String tagElement;
        int i = 0;

        Joueur hero = this.monde.player;
        tagElement = "tag" + i;
        hero.tag = tagElement;
        this.affichageJeu.chargerElement(hero.getImage(), hero.pos, tagElement);
        i++;

        for (Personnage perso : this.monde.pers){
            tagElement = "tag" + i;
            perso.tag = tagElement;

            this.affichageJeu.chargerElement(perso.getImage(), perso.pos, tagElement);

            i++;
        }

        for (Creature crea : this.monde.bugs){
            tagElement = "tag" + i;
            crea.tag = tagElement;

            this.affichageJeu.chargerElement(crea.getImage(), crea.pos, tagElement);

            i++;
        }

        for (Objet item : this.monde.items){
            tagElement = "tag" + i;
            item.tag = tagElement;

            this.affichageJeu.chargerElement(item.getImage(), item.pos, tagElement);

            i++;
        }
    }

}
