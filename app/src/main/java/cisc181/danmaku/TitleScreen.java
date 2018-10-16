package cisc181.danmaku;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class TitleScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_screen);

        SharedPreferences sharedPref = getSharedPreferences("highscore", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        if (!sharedPref.contains("score1")){
            for (int i = 1; i < 11; i++){
                editor.putString("score" + i, "No Score Yet");
            }
        }
        editor.apply();
    }

    public void goToGame(View view) {
        Intent intent = new Intent(this, GameScreen.class);
        startActivity(intent);
    }

    public void goToInstructions(View view){
        Intent intent = new Intent(this, HowToPlay.class);
        startActivity(intent);
    }

    public void goToHighScores(View view){
        Intent intent = new Intent(this, HighScores.class);
        startActivity(intent);
    }
}
