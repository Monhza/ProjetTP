package org.centrale.objet.WoE;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

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

        while(gameOn){


            test.tourDeJeu();


            if(test.player.perso.ptVie <= 0){
                gameOn = false;
            }
        }



    }
}
