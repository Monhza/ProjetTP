package org.centrale.objet.Interface;

import org.centrale.objet.WoE.Point2D;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Panel qui affiche l'inventaire, ce panel est lié à un joueur
 */
public class PanelInventaire extends AffichageGraphique implements MouseListener{

    public final int PANEL_WIDTH = 200;
    public final int PANEL_HEIGHT = 150;

    protected int invWidth = 4;
    protected int invHeight = 3;

    // On définit les couleurs de l'arrière-plan et de la grille
    public final Color GRID_COLOR = Color.WHITE;
    public final Color BACKGROUND_COLOR = Color.lightGray;
    public final Color TARGET_COLOR = Color.red;


    protected int sideLengthX, sideLengthY;
    protected int selectionX,selectionY;





    //Ces hashmaps vont servir à stocker les données des éléments présents dans l'inventaire
    public HashMap<String, BufferedImage> imageElements;
    public HashMap<String, Point2D> positionElements;

    /**
     * Constructeur du Panel
     * @param tempsRefresh : intervalle de rafraichissement de l'interface graphique
     */
    public PanelInventaire(long tempsRefresh){
        super();

        this.tempsRefresh = tempsRefresh;


        this.setPreferredSize(new Dimension(PANEL_WIDTH,PANEL_HEIGHT));
        this.setOpaque(true);
        this.setBackground(this.BACKGROUND_COLOR);

        // On crée une nouvelle Hashmap pour donner tous les éléments présents sur la carte
        imageElements = new HashMap<>();
        positionElements = new HashMap<>();

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
     * Cette classe permet de charger un nouvel élément sur l'inventaire
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
        // Pour la position de l'élément de l'inventaire, on invoque la méthode allouer position de la classe
        // qui nous retourne une position vide de l'inventaire visuel
        positionElements.put(tag, this.allouerPosition());
    }

    /**
     * Méthode qui permet de supprimer un élément présent sur l'inventaire
     * Cette suppression se fait via son tag
     */
    public void supprimerElement(String tag){
        imageElements.remove(tag);
        positionElements.remove(tag);
    }

    /**
     * Cette classe nous permet de convertir la position d'un élément en coordonnées exploitables pour notre
     * interface graphique. Méthode utile pour "dessiner" les éléments sur l'inventaire
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
     * Affichage des éléments de la carte
     * @param g  the <code>Graphics</code> context in which to paint
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2D = (Graphics2D) g;


        // On recharge tous les éléments sur la carte
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

        // Cette méthode place le curseur de sélection sur l'inventaire
        // Le curseur est affiché en permanence
        this.placerSelection(g2D);
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

        g.drawLine(coordX+2, coordY+2, coordX+sideLengthX-2, coordY+2);
        g.drawLine(coordX+2, coordY+2, coordX+2, coordY+sideLengthY-2);
        g.drawLine(coordX+sideLengthX-2, coordY+2, coordX+sideLengthX-2, coordY+sideLengthY-2);
        g.drawLine(coordX+2, coordY+sideLengthY-2, coordX+sideLengthX-2, coordY+sideLengthY-2);
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
     * Renvoie la matrice de présence des items dans l'inventaire
     * @return
     */
    protected boolean[][] presenceItem(){

        boolean[][] matPresence = new boolean[this.invWidth][this.invHeight];

        List<Point2D> listePoints = new ArrayList<Point2D>(this.positionElements.values());

        for (Point2D point : listePoints){

            matPresence[point.getX()][point.getY()] = true;
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