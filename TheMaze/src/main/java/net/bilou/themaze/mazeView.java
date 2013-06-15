package net.bilou.themaze;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Bilou on 01/06/13.
 */
@SuppressLint("HandlerLeak")
public class mazeView extends View {

    private MazeActivity activity;

    private int diamCircle = 40;
    private int width;
    private int height;
    private int coef = 6;

    private ArrayList<Rect> vAllWall = new ArrayList<Rect>();
    private Rect goal = new Rect();
    private Rect myBall = new Rect(0,0,diamCircle,diamCircle);

    //Creation de la vue
    private Paint paint = new Paint();
    //Canvas dessine dans la vue
    private Canvas canvas;
    private float x = 0, y = 0;
    private float xLin = 0, yLin = 0;

    AtomicBoolean isRunning = new AtomicBoolean(false);
    AtomicBoolean isPausing = new AtomicBoolean(false);
    private final Handler slowDownDrawingHandler;
    private Thread background;
    private MazeActivity mainActivity;


    Maze maze = new Maze(0,0);
    Gaol mygoal = new Gaol(0,0,6,3);
    boolean init = false;
    boolean isAppear = false;

    /**
     * Cree la vue du maze
     *
     * @param context
     *            context de l'application
     */
    public mazeView(Context context) {
        super(context);
        if(context instanceof MazeActivity){
            mainActivity = (MazeActivity) context;
        }
        activity = (MazeActivity) context;
        slowDownDrawingHandler = new Handler() {
            @SuppressLint("HandlerLeak")
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                redraw();
            }
        };

        //Lancement du thread qui met a jour le canvas
        background = new Thread(new Runnable() {
            Message myMessage;

            public void run() {
                try {
                    while (isRunning.get()) {
                        if (isPausing.get()) {
                            Thread.sleep(2000);
                        } else {
                            //60 images par seconde
                            Thread.sleep(1000 / 60);
                            myMessage = slowDownDrawingHandler.obtainMessage();

                            slowDownDrawingHandler.sendMessage(myMessage);
                        }
                    }
                } catch (Throwable t) {

                }
            }
        });
        isRunning.set(true);
        isPausing.set(false);
        background.start();
    }

    /**
     * Dessine le maze dans la vue.
     *
     */
    @Override
    public void onDraw(Canvas canvas) {
        width = this.getWidth();
        height = this.getHeight();

        if (!init) {
            // Coordonee au milieux de l'ecran
            x = width / 2;
            y = height / 2;
            xLin = x;
            yLin = y;

            init = true;
        }
        this.canvas = canvas;

        //Generation du labyrinthe
        maze.setHeightScreen(height);
        maze.setWidthScreen(width);
        if((!maze.isCreate())&&(maze.getHeightScreen()!=0)&&(maze.getWidthScreen()!=0)){
            maze.buildMaze();
        }
        vAllWall = maze.getvWall();

        mygoal.setHeightGoal((int)Math.round(height/4));
        mygoal.setWidthGoal((int)Math.round(width/7));


        //Dessin du maze
        displayMaze(vAllWall);
        drawBall();
        drawGoal();
    }

    /**
     * Affiche le maze dans la vue
     *
     * @param vWall
     *            Contient la liste des murs du maze.
     */
    private void displayMaze(ArrayList<Rect> vWall){
        paint.setARGB(255, 66, 28, 11);
        Rect test = new Rect(0,0,10,0);
        for(Rect aWall : vWall){
            canvas.drawRect(aWall, paint);
        }
    }

    /**
     * Dessine la balle dans le maze
     *
     */
    private void drawBall(){
        float xAcceleration = activity.x, yAcceleration = activity.y;
        drawPoint(xAcceleration, yAcceleration);
    }

    /**
     * Dessine le finish dans le maze
     *
     */
    private void drawGoal(){
        paint.setARGB(255, 66, 28, 11);
        goal=mygoal.getRect();
        canvas.drawRect(goal,paint);
    }

    private void redraw() {
        invalidate();
    }

    /**
     * Dessine et gere la boule.
     *
     * @param xAcceleration
     *              float du deplacement honrizontal
     * @param  yAcceleration
     *              float du deplacement vertical
     */
    private void drawPoint(float xAcceleration, float yAcceleration) {
        float newxAcc,newyAcc;

        // augmentation de la vitesse
        newxAcc = coef*xAcceleration;
        newyAcc = coef*yAcceleration;
        xLin = (myBall.left - newxAcc);
        yLin = (myBall.top + newyAcc);

        /** Gestion des collision */

       //Collision avec les murs
        for(Rect aWall : vAllWall){
            if(mainActivity.onIntersects(aWall, myBall)){
                //Log.e("Touche", "Vertical : "+aWall.toString());
                onLose();

            }
        }

        //collision avec l'objectif
        if(mainActivity.onIntersects(mygoal.getRect(),myBall)){
            onWin();
        }

        //Sort a droite ou a gauche
        if ((xLin+diamCircle > width)||(xLin < 0)) {
            xLin = xLin + newxAcc;
            //vibratePhone();
            //onTouchVerticale = true;
        }

        // Sort en bas ou en haut
        if ((yLin+diamCircle > height)||(yLin < 0)) {
            yLin = yLin - newyAcc;
            //vibratePhone();
            //onTouchHorizontal = true;
        }


        // dessin de la boule
        paint.setARGB(255, 0, 255, 0);
        myBall.set((int)Math.round(xLin),(int)Math.round(yLin), (int)Math.round(xLin+diamCircle), (int)Math.round(yLin+diamCircle));
        canvas.drawCircle(xLin+diamCircle/2, yLin+diamCircle/2, diamCircle/2, paint);


    }

    /**
     * Fait vibrer le teleohone
     *
     */
    private void vibratePhone(){
        //if(vibrateOnce = false){
        mainActivity.phoneVibrate();
        //}
    }

    /**
     * A la mort de la balle. Reinitialisation de la position de la balle
     *
     */
    private void onDeath(){
        xLin = 0;
        yLin = 0;
        vibratePhone();
        isAppear=false;
    }

    /**
     * A la reussite du maze. Fait vibrer le telephone et affiche une alerte.
     *
     */
    private void onWin(){
        if(!isAppear){
            mainActivity.alert("You Win !!!");
            isAppear = true;
            onDeath();
        }
    }

    /**
     * A la defaite du maze. Fait vibrer le telephone et affiche une alerte.
     *
     */
    private void onLose(){
        if(!isAppear){
            mainActivity.alert("You Lose !!!");
            isAppear = true;
            onDeath();
        }
    }
}
