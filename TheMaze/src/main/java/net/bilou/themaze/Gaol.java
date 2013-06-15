package net.bilou.themaze;

import android.graphics.Rect;
import android.util.Log;

/**
 * Created by Bilou on 01/06/13.
 */
public class Gaol {

    private int heightGoal;
    private int widthGoal;
    private int posx;
    private int posy;

    /**
     * Cree l'objet Gaol.
     *
     * @param w
     *            La largeur du finish.
     * @param h
     *            La hauteur du finish.
     * @param x
     *            La position x du finish
     * @param y
     *            La position y du finish
     */
    public Gaol(int w,int h, int x, int y){
        this.widthGoal=w;
        this.heightGoal=h;
        this.posy = y;
        this.posx = x;
    }

    /**
     * Retourne la hauteur du finish.
     *
     * @return Un int corespond a la hauteur du finish.
     */
    public int getHeightGoal(){
        return this.heightGoal;
    }

    /**
     * Met a jour la hauteur du finish.
     *
     * @param h
     *            La hauteur du finish.
     */
    public void setHeightGoal(int h){
        this.heightGoal=h;
    }

    /**
     * Retourne la largeur du finish.
     *
     * @return Un int corespond a la largeur du finish.
     */
    public int getWidthGoal(){
        return this.widthGoal;
    }

    /**
     * Met a jour la largeur du finish.
     *
     * @param w
     *            La largeur du finish.
     */
    public void setWidthGoal(int w){
        this.widthGoal=w;
    }

    /**
     * Retourne la position x du finish.
     *
     * @return Un int corespond a la largeur du finish.
     */
    public int getPosXGoal(){
        return this.posx;
    }

    /**
     * Met a jour la position x du finish.
     *
     * @param x
     *            La position x du finish.
     */
    public void setPosxGoal(int x){
        this.posx=x;
    }

    /**
     * Retourne la position y du finish.
     *
     * @return Un int corespond a la largeur du finish.
     */
    public int getPosyGoal(){
        return this.posy;
    }

    /**
     * Met a jour la position y du finish.
     *
     * @param y
     *            La position y du finish.
     */
    public void setPosyGoal(int y){
        this.posy=y;
    }

    /**
     * Retourne l'objet Rect du finish.
     *
     * @return Un Rect qui permet de dessiner le finish sur le canvas.
     */
    public Rect getRect(){
        Rect goal = new Rect(0,0,widthGoal/2,widthGoal/2);
        goal.offset((widthGoal*6)+50,(heightGoal*3)+50);
        return goal;
    }
}
