package net.bilou.themaze;

import android.graphics.Rect;
import android.util.Log;

/**
 * Created by Bilou on 01/06/13.
 */
public class Wall {
    private int heightWall;
    private  int sizeWall;
    private int heigthCell;
    private int widthCell;
    private int posx;
    private int posy;
    private int sens;

    /**
     * Cree l'objet mur.
     *
     * @param h
     *            La nouvelle longeur du mur.
     * @param x
     *            La position du mur a partir de la gauche.
     * @param y
     *            La position du mur a partir du haut de l'ecran
     * @param hC
     *            Hauteur des cellules du maze
     * @param wC
     *            Largeur des cellules du maze
     */
    public Wall(int h, int x, int y,int hC, int wC,int s){
        this.heightWall = h;
        this.posy = y;
        this.posx = x;
        this.sizeWall=10;
        this.heigthCell = hC;
        this.widthCell = wC;
        this.sens=s;
    }

    /**
     * Retourne la longueur du mur.
     *
     * @return Un int qui correspond a la longueur du mur.
     */
    public int getHeightWall(){
        return this.heightWall;
    }
    /**
     * Met a jour la longeur du mur.
     *
     * @param h
     *            La nouvelle longeur du mur.
     */
    public void setHeightWall(int h){
        this.heightWall=h;
    }

    /**
     * Retourne la position x du mur.
     *
     * @return Un int qui correspond a la position x du mur.
     */
    public int getPosx(){
        return this.posx;
    }

    /**
     * Met a jour la position x du mur.
     *
     * @param x
     *            La nouvelle position x du mur.
     */
    public void setPosx(int x){
        this.posx=x;
    }

    /**
     * Retourne la position y du mur.
     *
     * @return Un int qui correspond a la position y du mur.
     */
    public int getPosy(){
        return this.posy;
    }

    /**
     * Met a jour la position y du mur.
     *
     * @param y
     *            La nouvelle position y du mur.
     */
    public void setPosy(int y){
        this.posy=y;
    }

    /**
     * Retourne la hauteur des cellules.
     *
     * @return Un int qui correspond a la hauteur des cellules.
     */
    public int getHeigthCell(){
        return this.heigthCell;
    }

    /**
     * Met a jour la hauteur des cellules
     *
     * @param h
     *            La nouvelle hauteur des cellules.
     */
    public void setHeigthCell(int h){
        this.heigthCell=h;
    }

    /**
     * Retourne la largeur des cellules.
     *
     * @return Un int qui correspond a la largeur des cellules.
     */
    public int getWidthCell(){
        return this.widthCell;
    }

    /**
     * Met a jour la largeur des cellules
     *
     * @param w
     *            La nouvelle largeur des cellules.
     */
    public void setWidthCell(int w){
        this.widthCell=w;
    }

    /**
     * Retourne la largeur des murs.
     *
     * @return Un int qui correspond a la largeur des murs.
     */
    public int getSizeWall(){
        return sizeWall;
    }

    /**
     * Met a jour la largeur des murs.
     *
     * @param size
     *            La nouvelle largeur des murs.
     */
    public void setSizeWall(int size){
        this.sizeWall=size;
    }

    /**
     * Retourne le mur
     *
     * @return Un objet Rect qui permet de dissiner le mur dans la view.
     */
    public Rect getRect(){
        int longueur = this.getHeigthCell()*this.getHeightWall();
        int posX = getWidthCell()*getPosx();
        int posY = getHeigthCell()*getPosy();

        if(this.sens==1){
            Rect Wall = new Rect(0,0,getSizeWall(),longueur);
            Wall.offset(posX,posY);
            return Wall;
        }
        else {

            Rect Wall = new Rect(0,0,longueur,getSizeWall());
            Wall.offset(posX,posY);
            return Wall;
        }
    }
}
