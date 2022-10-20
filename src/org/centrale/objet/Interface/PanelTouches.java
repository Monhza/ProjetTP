package org.centrale.objet.Interface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Panel qui affiche les possibilités d'action d'un joueur
 */
public class PanelTouches extends JPanel implements ActionListener {

    public int PANEL_WIDTH = 200;
    public int PANEL_HEIGHT = 200;
    public FenetreJeu fenetre;
    private JButton utiliserButton;
    private JButton attaqueButton;
    private JButton buttonUp;
    private JButton buttonDown;
    private JButton buttonRight;
    private JButton buttonLeft;
    private JPanel mainPanel;

    private boolean actionEffectuee = false;
    private String[] descriptionAction;

    public PanelTouches(FenetreJeu fenetre) {
        this.add(mainPanel);
        this.setPreferredSize(new Dimension(PANEL_WIDTH,PANEL_HEIGHT));

        this.fenetre = fenetre;

        buttonUp.addActionListener(this);
        buttonDown.addActionListener(this);
        buttonLeft.addActionListener(this);
        buttonRight.addActionListener(this);
        attaqueButton.addActionListener(this);
        utiliserButton.addActionListener(this);
    }

    /**
     * Méthode qui indique les commandes envoyées au jeu lors de l'appui sur une touche particulière
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // On efface la dernière action s'il y en a eu une
        this.initialiseAction();

        Object source = e.getSource();
        if (this.buttonUp.equals(source)) {
            this.descriptionAction = new String[]{"deplacement", "0,-1"};
        } else if (this.buttonDown.equals(source)) {
            this.descriptionAction = new String[]{"deplacement", "0,1"};
        } else if (this.buttonLeft.equals(source)) {
            this.descriptionAction = new String[]{"deplacement", "-1,0"};
        } else if (this.buttonRight.equals(source)) {
            this.descriptionAction = new String[]{"deplacement", "1,0"};
        } else if (this.attaqueButton.equals(source)) {
            int x = this.fenetre.getAffichageJeu().selectionX;
            int y = this.fenetre.getAffichageJeu().selectionY;
            this.descriptionAction = new String[]{"attaque", x + "," + y};
        } else if (this.utiliserButton.equals(source)) {
            String tag = this.fenetre.getInventaireEcran().getTagSelection();

            this.descriptionAction = new String[]{"utiliser", tag};
        }

        if (this.descriptionAction != null){
            this.actionEffectuee = true;
        }
    }

    /**
     * Retourne si une action a été effectuée ou non dans le panel touches
     * @return boolean
     */
    public boolean isActionEffectuee(){
        if (this.actionEffectuee){
            this.actionEffectuee = false;
            return true;
        }
        return false;
    }

    /**
     * Méthode qui permet au jeu de demander quelle a été la dernière commande entrée par le joueur
     * Une fois la commande récupérée, le panel réinitialise la commande
     * @return
     */
    public String[] getDescriptionAction(){
        String[] actionTemp = this.descriptionAction.clone();

        // On réinitialise l'action à l'appel de cette méthode
        this.initialiseAction();

        return actionTemp;

    }

    /**
     * Permet d'initialiser la commande
     * Permet de gérer plus ergonomiquement le jeu du point de vue du joueur
     */
    public void initialiseAction(){
        this.actionEffectuee = false;
        this.descriptionAction = null;
    }
}


