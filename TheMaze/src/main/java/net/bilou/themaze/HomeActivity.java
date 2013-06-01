package net.bilou.themaze;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;

public class HomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public  void launchGame(View v){
        Intent mazeActivity = new Intent(getBaseContext(),MazeActivity.class);
        startActivity(mazeActivity);
    }
    
}
