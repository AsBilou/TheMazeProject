package net.bilou.themaze;

import android.graphics.Rect;
import android.util.Log;

import net.bilou.themaze.Wall;

import java.util.ArrayList;

/**
 * Created by Bilou on 01/06/13.
 */
public class Maze {

    private ArrayList<Rect> vWall = new ArrayList<Rect>();
    private int heightScreen;
    private int widthScreen;
    private boolean status = false;
    private int heightWall = 10;

    /**
     * Cree l'objet Maze.
     *
     * @param hS
     *            La hauteur de l'ecran.
     * @param wS
     *            La largeur de l'ecran
     */
    public Maze(int hS,int wS){
        this.heightScreen = hS;
        this.widthScreen = wS;
    }

    /**
     * Met a jour la hauteur de l'ecran
     *
     * @param h
     *            La hauteur de l'ecran.
     */
    public void setHeightScreen(int h){
        this.heightScreen = h;
    }

    /**
     * Retourne la hauteur de l'ecran.
     *
     * @return Un int corespond a la hauteur de l'ecran.
     */
    public int getHeightScreen(){
        return this.heightScreen;
    }

    /**
     * Met a jour la largeur de l'ecran
     *
     * @param w
     *            La largeur de l'ecran.
     */
    public void setWidthScreen(int w){
        this.widthScreen = w;
    }

    /**
     * Retourne la largeur de l'ecran.
     *
     * @return Un int corespond a la largeur de l'ecran.
     */
    public int getWidthScreen(){
        return this.widthScreen;
    }

    /**
     * Genere le maze
     *
     */
    public void buildMaze(){

        int widthCell = widthScreen/7;
        int heightCell = heightScreen/4;


        //Creation des wall
        //Vertical

        Wall vWall1 = new Wall(2,1,0,heightCell,widthCell,1);
        vWall.add(vWall1.getRect());

        Wall vWall2 = new Wall(1,2,0,heightCell,widthCell,1);
        vWall.add(vWall2.getRect());

        Wall vWall3 = new Wall(3,5,0,heightCell,widthCell,1);
        vWall.add(vWall3.getRect());

        Wall vWall4 = new Wall(3,4,1,heightCell,widthCell,1);
        vWall.add(vWall4.getRect());

        Wall vWall5 = new Wall(1,3,2,heightCell,widthCell,1);
        vWall.add(vWall5.getRect());

        Wall vWall6 = new Wall(2,2,3,heightCell,widthCell,1);
        vWall.add(vWall6.getRect());

        Wall vWall7 = new Wall(2,6,2,heightCell,widthCell,1);
        vWall.add(vWall7.getRect());

        //Horizontal
        Wall hWall1 = new Wall(2,1,2,heightCell,widthCell,0);
        vWall.add(hWall1.getRect());

        Wall hWall2 = new Wall(1,1,3,heightCell,widthCell,0);
        vWall.add(hWall2.getRect());

        Wall hWall3 = new Wall(1,3,1,heightCell,widthCell,0);
        vWall.add(hWall3.getRect());

        Wall hWall4 = new Wall(1,5,1,heightCell,widthCell,0);
        vWall.add(hWall4.getRect());

        this.status = true;
    }

    /**
     * Retourne true si le maze a ete genere.
     *
     * @return Un boolean, true si cree, false si non cree.
     */
    public boolean isCreate(){
        return status;
    }

    /**
     * Retourne une collection de mur.
     *
     * @return Une collection de mur qui constitue le maze.
     */
    public ArrayList<Rect> getvWall(){
        return this.vWall;
    }
}
