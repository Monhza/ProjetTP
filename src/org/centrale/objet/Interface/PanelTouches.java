package org.centrale.objet.Interface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
            int x = this.fenetre.getInventaireEcran().selectionX;
            int y = this.fenetre.getInventaireEcran().selectionY;
            this.descriptionAction = new String[]{"utiliser", x + "," + y};
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

    public String[] getDescriptionAction(){
        String[] actionTemp = this.descriptionAction.clone();

        // On réinitialise l'action à l'appel de cette méthode
        this.initialiseAction();

        return actionTemp;

    }

    public void initialiseAction(){
        this.actionEffectuee = false;
        this.descriptionAction = null;
    }
}


