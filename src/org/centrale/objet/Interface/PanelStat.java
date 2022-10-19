package org.centrale.objet.Interface;

import org.centrale.objet.WoE.Archer;
import org.centrale.objet.WoE.Joueur;
import org.centrale.objet.WoE.Point2D;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class PanelStat extends JPanel implements Runnable {

    public int PANEL_WIDTH = 150;
    public int PANEL_HEIGHT = 150;


    protected Thread refreshThread;
    protected long tempsRefresh;
    protected boolean refreshOn;

    public HashMap<String, File> imagesDisponibles;
    public HashMap<String, BufferedImage> imageElements;

    public boolean fleches = true;

    public Joueur player;
    private JLabel labelNom;
    private JLabel labelVie;
    private JLabel labelDegats;
    private JLabel labelFleches;
    private JPanel mainPanel;

    public PanelStat(long tempsRefresh) {
        this.add(mainPanel);
        this.setPreferredSize(new Dimension(PANEL_WIDTH,PANEL_HEIGHT));

        // On met à jour le temps de rafraichissement
        this.tempsRefresh = tempsRefresh;

        imageElements = new HashMap<String, BufferedImage>();

        //On met les noms par défaut des labels
        labelNom.setText("Nom du personnage");
        labelVie.setText("Vie");
        labelDegats.setText("Degats");
        labelFleches.setText("Fleches");

        // On charge les chemins des images présents dans le fichier "graphismes"
        this.chargerCheminsImages();
    }


    /**
     * Cette méthode permet de charger le chemin de toutes les images présentes dans le fichier graphismes
     */
    protected void chargerCheminsImages(){

        imagesDisponibles = new HashMap<String, File>();

        String filePath = new File("").getAbsolutePath();
        filePath += "\\graphismes_statistiques";
        File ImageFile = new File(filePath);

        String[] pathnames = ImageFile.list();

        String name;
        String finalPath;
        for (String pathname : pathnames) {

            name = pathname.split("[.]")[0];
            finalPath = filePath + "\\" + pathname;

            imagesDisponibles.put(name, new File(finalPath));
        }
    }


    /**
     * Cette classe permet de charger les éléments à utiliser dans le panneau de statistiques
     */
    public void chargerElement(String nomImage) {

        BufferedImage imageChargee = null;
        try {
            imageChargee = ImageIO.read(imagesDisponibles.get(nomImage));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e){
            throw new IllegalArgumentException("L\'image "+ nomImage+ " n\'est pas presente dans les elements graphiques disponibles");
        }

        if (imageChargee == null){
            new Exception("L\'image que nous essayez de charger n\'est pas disponible");
        }

        imageElements.put(nomImage, imageChargee);
    }

    /**
     * On place les images utiles pour notre panneau statistiques
     */
    protected void placerImages(Graphics2D g2D){

        BufferedImage imageCoeur, imageDegats, imageFleches;

        this.chargerElement("coeur");
        this.chargerElement("fleche");
        this.chargerElement("attaque");

        imageCoeur = imageElements.get("coeur");
        imageDegats = imageElements.get("attaque");
        imageFleches = imageElements.get("fleche");

        int yRef = labelVie.getY() ;
        g2D.drawImage(imageCoeur, 20, yRef, 25, 25, null);

        yRef = labelDegats.getY() ;
        g2D.drawImage(imageDegats, 20, yRef, 25, 25, null);

        yRef = labelFleches.getY() ;
        g2D.drawImage(imageFleches, 20, yRef, 25, 25, null);

    }

    /**
     * Affichage des éléments de la carte
     * @param g  the <code>Graphics</code> context in which to paint
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2D = (Graphics2D) g;

        this.placerImages(g2D);
    }


    /**
     * On lie un joueur au panneau pour démarrer l'affichage dynamique
     * @param player
     */
    public void lierAuJoueur(Joueur player){
        this.player = player;

        labelNom.setText(player.nomPerso);

        // On vérifie si le joueur est un archer
        // C'est pas propre, mais j'ai fait ça vie :x
        try {
            Archer testArcher = (Archer) player.perso;
        }catch (Exception e){
            this.fleches = false;
            labelFleches.setText("X");
        }

        this.demarrerAffichage();
    }

    /**
     * Cette méthode permet de démarrer l'affichage dynamique des statistiques via un Thread
     */
    public void demarrerAffichage(){
        refreshOn = true;
        this.refreshThread = new Thread(this);
        this.refreshThread.setDaemon(true);
        this.refreshThread.start();
    }

    /**
     * Permet l'affichage dynamique de la carte
     */
    @Override
    public void run() {

        int i = 0;

        while (this.refreshOn) {
            try {
                Thread.sleep(this.tempsRefresh);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            labelVie.setText(String.valueOf(player.perso.ptVie));
            labelDegats.setText(String.valueOf(player.perso.degAtt));

            if (fleches){
                labelFleches.setText(String.valueOf(((Archer) player.perso).nbFleches));
            }

        }
    }



    public static void main(String[] args){
        JFrame f = new JFrame();
        PanelStat panel = new PanelStat(50);

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        f.add(panel);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}
