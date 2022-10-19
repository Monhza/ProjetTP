package org.centrale.objet.Interface;

import org.centrale.objet.WoE.Joueur;

import javax.swing.*;
import java.awt.*;

public class FenetreJeu extends JFrame {

    private PanelJeu affichageJeu;
    private PanelInventaire inventaire;
    private PanelTouches touchesAction;
    private PanelStat stats;

    public FenetreJeu(int widthCarte,int heightCarte, long tempsRefresh) {
        setTitle("Fenetre de jeu");
        setSize(920, 640);
        //setBackground(Color.lightGray);
        setLayout(null);

        // Ajout du panel qui affiche la carte
        this.affichageJeu = new PanelJeu(widthCarte, heightCarte,tempsRefresh);
        int panWidth = this.affichageJeu.PANEL_WIDTH;
        int panHeight = this.affichageJeu.PANEL_HEIGHT;
        this.affichageJeu.setBounds(0,0,panWidth, panHeight);
        this.add(this.affichageJeu);
        //On démarre l'affichage dynamique du panel
        this.affichageJeu.demarrerAffichage();


        //Ajout du panel qui affiche les boutons d'action
        this.touchesAction = new PanelTouches(this);
        panWidth = this.touchesAction.PANEL_WIDTH;
        panHeight = this.touchesAction.PANEL_HEIGHT;
        this.touchesAction.setBounds(650,200,panWidth, panHeight);
        this.add(this.touchesAction);


        // Ajout du panel qui affiche l'inventaire
        this.inventaire = new PanelInventaire(tempsRefresh);
        panWidth = this.inventaire.PANEL_WIDTH;
        panHeight = this.inventaire.PANEL_HEIGHT;
        this.inventaire.setBounds(650,430,panWidth, panHeight);
        this.add(this.inventaire);
        //On démarre l'affichage dynamique du panel
        this.inventaire.demarrerAffichage();


        // Ajout du panel qui affiche les statistiques
        this.stats = new PanelStat(tempsRefresh);
        panWidth = this.stats.PANEL_WIDTH;
        panHeight = this.stats.PANEL_HEIGHT;
        this.stats.setBounds(675,10,panWidth, panHeight);
        this.add(this.stats);


        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);

    }

    public PanelJeu getAffichageJeu() {
        return affichageJeu;
    }

    public PanelInventaire getInventaireEcran(){ return inventaire;}

    public PanelTouches getTouchesAction(){ return touchesAction; };

    public void setStats(Joueur joueur){
        this.stats.lierAuJoueur(joueur);
    }

    public static void main(String[] args){
        FenetreJeu test = new FenetreJeu(10, 10, 200);
    }
}
