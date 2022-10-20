package org.centrale.objet.WoE;

public class TestWoE {
    public static void main(String[] arg) {

        //Test joueur
        World test = new World();

        test.creerMondeAlea(3,
                3,
                3,
                3,
                3,
                6);


        boolean gameOn = true;

        while (gameOn) {


            test.tourDeJeu();


            if (test.player.perso.ptVie <= 0) {
                gameOn = false;

                System.out.println("Le joueur est mort, fin du jeu");
            }
        }


    }
}
