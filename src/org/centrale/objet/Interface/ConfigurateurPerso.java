package org.centrale.objet.Interface;

import javax.swing.*;
import java.awt.event.*;

/**
 * Cette classe affiche une première fenêtre qui nous permet d'entrer les caractéristiques
 * du personnage joueur
 *
 * Créé avec Swing UI designer
 */
public class ConfigurateurPerso extends JFrame implements ActionListener, KeyListener {
    private JPanel pan;
    private JComboBox comboType;
    private JTextField textNom;
    private JButton validerButton;
    private JLabel labelNom;
    private boolean validate = false;
    private String typePerso;
    private String nom;

    public ConfigurateurPerso(String[] listeType) {
        addListType(listeType);
        setContentPane(pan);
        setTitle("Configuration du personnage");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        // L'appui sur le bouton "valider" va vérifier la validité des valeurs des champs
        // Puis in détruira la fenêtre
        validerButton.addActionListener(this);
        textNom.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e){}

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyChar() == KeyEvent.VK_ENTER){
            this.valider();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // Actions effectuées lorsque l'on appuie sur le bouton "valider"
        if (e.getSource() == this.validerButton) {
            this.valider();
        }
    }

    public void valider() {
        if (!"".equals(this.textNom.getText())) {
            this.typePerso = (String) this.comboType.getSelectedItem();
            this.nom = this.textNom.getText();
            this.validate = true;
        }
    }


    public void addListType(String[] listeType){
        for (int i = 0 ; i<listeType.length; i++){
            this.comboType.addItem(listeType[i]);
        }
    }

    public boolean isOver(){

        return this.validate;
    }

    public String getNom() {
        return this.nom;
    }

    public String getTypePerso() {
        return this.typePerso;
    }

    public void closeWin(){

        this.setVisible(false);
        this.dispose();
    }

    public static void main(String[] args){
        String[] types = {"Archer", "Guerrier"};
        ConfigurateurPerso test = new ConfigurateurPerso(types);

        while (!test.isOver()){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println(test.getNom());
        System.out.println(test.getTypePerso());

        test.closeWin();
    }



}
