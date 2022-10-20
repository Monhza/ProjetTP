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


/**
 * Panel qui permet l'affichage de la carte du jeu de WoE
 */
public class PanelJeu extends AffichageGraphique implements MouseListener{

    public int PANEL_WIDTH = 600;
    public int PANEL_HEIGHT = 600;

    // On définit les couleurs de l'arrière-plan et de la grille
    public final Color GRID_COLOR = Color.lightGray;
    public final Color BACKGROUND_COLOR = Color.BLACK;
    public final Color TARGET_COLOR = Color.red;

    protected int mapWidth;
    protected int mapHeight;

    protected int sideLengthX, sideLengthY;

    protected int selectionX,selectionY;

    protected long tempsRefresh;


    //Ces hashmaps vont servir à stocker les données des éléments sur la carte
    public HashMap<String, BufferedImage> imageElements;
    public HashMap<String, Point2D> positionElements;


    /**
     * Constructeur de la classe, auquel on précise les dimensions de la carte en terme de cases
     * (et non pas en pixels pour l'affichage)
     * @param mapWidth
     * @param mapHeight
     * @param tempsRefresh : Période de rafraichissement de la carte
     */
    public PanelJeu(int mapWidth, int mapHeight, long tempsRefresh){
        super();

        //On donne les données de la grille
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;

        this.tempsRefresh = tempsRefresh;


        this.setPreferredSize(new Dimension(PANEL_WIDTH,PANEL_HEIGHT));
        this.setOpaque(true);
        this.setBackground(this.BACKGROUND_COLOR);

        // On crée une nouvelle Hashmap pour donner tous les éléments présents sur la carte
        imageElements = new HashMap<String, BufferedImage>();
        positionElements = new HashMap<String, Point2D>();

        // On charge les chemins des images présents dans le fichier "graphismes"
        this.chargerCheminsImages("\\graphismes");

        // On détecte les mouvements et des clics de la souris sur le panel pour permettre de
        // sélectionner une case
        addMouseListener(this);
    }



    /**
     * On enregistre la case du dernier click
     * @param
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        selectionX = e.getX()/this.sideLengthX;
        selectionY = e.getY()/this.sideLengthY;
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
     * Cette classe permet de charger un nouvel élément sur la carte
     * Ce peut être un joueur, un item au tout autre chose utile à notre interface visuelle
     *
     * @param nomImage
     * @param point
     * @param tag : identifiant de l'image présente sur la carte
     */
    public void chargerElement(String nomImage, Point2D point, String tag) {

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
        positionElements.put(tag, point);
    }

    /**
     * Méthode qui permet de supprimer un élément présent sur la carte
     * Cette suppression se fait via son tag
     *
     * @param tag : identifiant de l'image présente sur la carte
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

        // De temps en temps, à cause du Threading, un élément est supprimé pendant l'exécution
        // de la méthode. On vérifie ce phénomène ici
        if (imageTemp == null){
            return null;
        }

        double heightImage = imageTemp.getHeight();
        double widthImage = imageTemp.getWidth();
        double facteur = 0;

        // On définit le format de l'image
        // On fait rentrer l'image dans une case, sans pour autant en changer les proportions
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
     * Place une "cible" aux coordonnées de la sélection faite par la souris
     * Permet de repérer quelle case de l'inventaire est sélectionnée
     * @param g
     */
    protected void placerSelection(Graphics2D g){

        int coordX, coordY;

        g.setColor(this.TARGET_COLOR);
        g.setStroke(new BasicStroke((float) 1.5));

        coordX = selectionX*sideLengthX;
        coordY = selectionY*sideLengthY;

        // On place un petit carré de couleur pour indiquer la sélection du joueur
        g.drawLine(coordX+2, coordY+2, coordX+sideLengthX-2, coordY+2);
        g.drawLine(coordX+2, coordY+2, coordX+2, coordY+sideLengthY-2);
        g.drawLine(coordX+sideLengthX-2, coordY+2, coordX+sideLengthX-2, coordY+sideLengthY-2);
        g.drawLine(coordX+2, coordY+sideLengthY-2, coordX+sideLengthX-2, coordY+sideLengthY-2);
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

            // De temps en temps, à cause du Threading, un élément est supprimé pendant l'exécution
            // de la méthode. On vérifie ce phénomène ici
            if (imageDraw != null) {
                g2D.drawImage(imageDraw, coordonnees[0], coordonnees[1], coordonnees[2], coordonnees[3], null);
            }
        }

        this.placerSelection(g2D);
    }

    // Méthode qui gère les fonds d'écran
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // On appelle la méthode qui permet de créer la grille de jeu
        this.creerGrille(g);
    }


    /**
     * Création de la grille de jeu.
     * Cette grille compose le fond du panel de jeu
     * @param g
     */
    protected  void creerGrille(Graphics g){

        int nRowCount, nColumnCount, currentX, currentY;

        //On récupère les dimensions en pixels des cases de la grille
        this.sideLengthX = this.getWidth()/this.mapWidth;
        this.sideLengthY = this.getHeight()/this.mapHeight;

        // Grid start
        // On trace les lignes qui vont constituer notre grille
        g.setColor(this.GRID_COLOR);
        nRowCount = this.mapHeight;
        currentY = this.sideLengthY;
        for (int i = 0; i < nRowCount; i++) {
            g.drawLine(0, currentY, getWidth(), currentY);
            currentY = currentY + this.sideLengthY;
        }

        // On trace les colonnes qui vont constituer notre grille
        nColumnCount = this.mapWidth;
        currentX = this.sideLengthX;
        for (int i = 0; i < nColumnCount; i++) {
            g.drawLine(currentX, 0, currentX, getHeight());
            currentX = currentX + this.sideLengthX;
        }
    }



    public static void main(String[] args){
        JFrame f = new JFrame();
        PanelJeu panel = new PanelJeu(10,10, 200);
        panel.demarrerAffichage();

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        f.add(panel);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);


        panel.chargerElement("lapin", new Point2D(3,2),"tag1");

        panel.chargerElement("hero", new Point2D(4,1),"tag2");

        panel.chargerElement("loup", new Point2D(4,3),"tag3");

        panel.chargerElement("fermier", new Point2D(7,1),"tag4");

        panel.chargerElement("guerrier", new Point2D(5,3),"tag5");

        panel.chargerElement("archer", new Point2D(9,6),"tag6");

    }
}