package org.centrale.objet.Interface;

import org.centrale.objet.WoE.Point2D;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.*;


public class PanelInventaire extends JPanel implements MouseListener, Runnable{

    public final int PANEL_WIDTH = 200;
    public final int PANEL_HEIGHT = 150;

    protected int invWidth = 4;
    protected int invHeight = 3;

    // On définit les couleurs de l'arrière-plan et de la grille
    public Color GRID_COLOR = Color.WHITE;
    public Color BACKGROUND_COLOR = Color.lightGray;


    protected int sideLengthX, sideLengthY;
    protected int selectionX,selectionY;

    protected Thread refreshThread;
    protected long tempsRefresh;
    protected boolean refreshOn;


    public HashMap<String, File> imagesDisponibles;

    //Ces hashmaps vont servir à stoquer les données des éléments sur la carte
    public HashMap<String, BufferedImage> imageElements;
    public HashMap<String, Point2D> positionElements;


    public PanelInventaire(long tempsRefresh){
        super();

        this.tempsRefresh = tempsRefresh;


        this.setPreferredSize(new Dimension(PANEL_WIDTH,PANEL_HEIGHT));
        this.setOpaque(true);
        this.setBackground(this.BACKGROUND_COLOR);

        // On crée une nouvelle Hashmap pour donner tous les éléments présents sur la carte
        imageElements = new HashMap<String, BufferedImage>();
        positionElements = new HashMap<String, Point2D>();

        // On charge les chemins des images présents dans le fichier "graphismes"
        this.chargerCheminsImages();

        // On détecte les mouvements et des clics de la souris sur le panel pour permettre de
        // sélectionner une case
        addMouseListener(this);
    }

    /**
     * Cette méthode permet de charger le chemin de toutes les images présentes dans le fichier graphismes
     */
    protected void chargerCheminsImages(){

        imagesDisponibles = new HashMap<String, File>();

        String filePath = new File("").getAbsolutePath();
        filePath += "\\graphismes";
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
     * Cette méthode permet de démarrer l'affichage dynamique du jeu via un Thread
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

        while (this.refreshOn) {
            try {
                Thread.sleep(this.tempsRefresh);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            this.refresh();
        }
    }

    /**
     * On enregistre la case du dernier click
     * @param
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        selectionX = e.getX()/this.sideLengthX;
        selectionY = e.getY()/this.sideLengthY;

        System.out.println(selectionX + "'" + selectionY);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    /**
     * Méthode appelée lors du jeu
     */
    public void refresh(){
        this.repaint();
    }


    /**
     * Cette classe permet de charger un nouvel élément sur la carte
     * Ce peut être un joueur, un item au tout autre chose utile à notre interface visuelle
     */
    public void chargerElement(String nomImage, String tag) {

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

        imageElements.put(tag, imageChargee);
        positionElements.put(tag, this.allouerPosition());
    }

    /**
     * Méthode qui permet de supprimer un élément présent sur la carte
     * Cette suppression se fait via son tag
     */
    public void supprimerElement(String tag){
        imageElements.remove(tag);
        positionElements.remove(tag);
    }

    /**
     * Cette classe nous permet de convertir la position d'un élément en coordonnées exploitables pour notre
     * interface graphique
     * @param tag : tag de l'élément considéré
     * @return : {x, y, width, height}
     */
    public int[] donnerCoordonnerElement(String tag){

        double x, y, width, height;

        BufferedImage imageTemp = imageElements.get(tag);
        Point2D pointTemp = positionElements.get(tag);

        double heightImage = imageTemp.getHeight();
        double widthImage = imageTemp.getWidth();
        double facteur = 0;

        if (heightImage > widthImage){
            height = this.sideLengthY - 1;
            y = pointTemp.getY() * this.sideLengthY + 1;

            facteur = widthImage/heightImage;
            width = this.sideLengthX*facteur - 1;
            x = (pointTemp.getX() + (1-facteur)/2) * this.sideLengthX + 1;
        } else {
            width = this.sideLengthX - 1;
            x = pointTemp.getX() * sideLengthX + 1;

            facteur = heightImage/widthImage;
            height = this.sideLengthY*facteur - 1;
            y = (pointTemp.getY() + (1-facteur)/2) * this.sideLengthY + 1;
        }

        int[] coordonnees = {(int) x, (int) y, (int) width, (int) height};

        return coordonnees;
    }



    /**
     * Affichage des éléments de la carte
     * @param g  the <code>Graphics</code> context in which to paint
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2D = (Graphics2D) g;


        // On place sur la carte tous les nouveaux éléments et on bouge tous les éléments qui ont
        // subi une translation
        int[] coordonnees;
        String[] listeTags = this.positionElements.keySet().toArray(new String[0]);
        BufferedImage imageDraw;
        for (String tag : listeTags){
            coordonnees = this.donnerCoordonnerElement(tag);
            imageDraw = imageElements.get(tag);

            g2D.drawImage(imageDraw, coordonnees[0], coordonnees[1], coordonnees[2], coordonnees[3], null);
        }
    }

    // Méthode qui gère les fonds d'écran
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // On appelle la méthode qui permet de créer la grille de jeu
        this.creerGrille((Graphics2D) g);
    }


    /**
     * Création de la grille de l'inventaire.
     * Cette grille compose le fond du panel de jeu
     * @param g
     */
    protected  void creerGrille(Graphics2D g){

        int nRowCount, nColumnCount, currentX, currentY;

        g.setStroke(new BasicStroke((float) 1.5));

        //On récupère les dimensions en pixels des cases de la grille
        this.sideLengthX = this.getWidth()/this.invWidth;
        this.sideLengthY = this.getHeight()/this.invHeight;

        // Grid start
        // On trace les lignes qui vont constituer notre grille
        g.setColor(this.GRID_COLOR);
        nRowCount = this.invHeight;
        currentY = this.sideLengthY;
        for (int i = 0; i < nRowCount; i++) {
            g.drawLine(0, currentY, getWidth(), currentY);
            currentY = currentY + this.sideLengthY;
        }

        // On trace les colonnes qui vont constituer notre grille
        nColumnCount = this.invWidth;
        currentX = this.sideLengthX;
        for (int i = 0; i < nColumnCount; i++) {
            g.drawLine(currentX, 0, currentX, getHeight());
            currentX = currentX + this.sideLengthX;
        }
    }

    /**
     * Méthode appelée lorsqu'un nouvel élément est ajouté à l'inventaire
     * On place l'objet dans le premier slot disponible dans le sens de lecture
     * @return : Point2D de la position de l'élément
     */
    protected Point2D allouerPosition(){
        boolean[][] matPresence = this.presenceItem();

        for (int i = 0; i < this.invHeight; i++){
            for (int y = 0; y < this.invWidth; y++){
                if (!matPresence[y][i]){
                    return new Point2D(y, i);
                }
            }
        }
        // On déclenche une exception s'il n'y a plus de place dans l'inventaire
        // Le cas ne se présente pas, donc on laisse comme ça pour l'instant
        throw new IndexOutOfBoundsException("Il n'y a plus de place dans l'inventaire");
    }


    /**
     * Revoie la matrice de présence des items dans l'inventaire
     * @return
     */
    protected boolean[][] presenceItem(){

        Point2D pointTemp;

        boolean[][] matPresence = new boolean[this.invWidth][this.invHeight];

        String[] listeTags = this.positionElements.keySet().toArray(new String[0]);

        for (String tag : listeTags){

            pointTemp = this.positionElements.get(tag);

            matPresence[pointTemp.getX()][pointTemp.getY()] = true;
        }

        return matPresence;
    }

    public String getTagSelection(){
        Point2D pointTemp;

        String[] listeTags = this.positionElements.keySet().toArray(new String[0]);

        for (String tag : listeTags){

            pointTemp = this.positionElements.get(tag);

            if (pointTemp.equals(new Point2D(selectionX, selectionY))){
                return tag;
            }
        }

        return null;
    }



    public static void main(String[] args){
        JFrame f = new JFrame();
        PanelInventaire panel = new PanelInventaire(50);
        panel.demarrerAffichage();

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        f.add(panel);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);

        panel.chargerElement("pomme","tag1");
        panel.chargerElement("epee","tag2");
        panel.chargerElement("potion","tag3");

        panel.supprimerElement("tag2");
        panel.chargerElement("epee","tag4");

    }
}