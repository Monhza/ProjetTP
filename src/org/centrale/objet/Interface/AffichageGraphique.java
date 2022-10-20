package org.centrale.objet.Interface;

import javax.swing.*;
import java.io.File;
import java.util.HashMap;

/**
 * Classe qui permet le chargement d'images pour l'affichage une interface graphique
 */
public abstract class AffichageGraphique extends JPanel implements Runnable {

    public HashMap<String, File> imagesDisponibles;
    protected Thread refreshThread;
    protected long tempsRefresh;
    protected boolean refreshOn;

    /**
     * Cette méthode permet de charger le chemin de toutes les images présentes dans le fichier graphismes
     */
    protected void chargerCheminsImages(String cheminImages) {

        imagesDisponibles = new HashMap<String, File>();

        String filePath = new File("").getAbsolutePath();
        filePath += cheminImages;
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
        this.refreshOn = true;
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
     * Méthode appelée lors du jeu
     */
    public void refresh(){
        this.repaint();
    }
}
