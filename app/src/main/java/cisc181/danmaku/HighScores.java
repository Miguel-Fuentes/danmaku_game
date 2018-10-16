package cisc181.danmaku;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class HighScores extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);

        SharedPreferences sharedPref = getSharedPreferences("highscore", Context.MODE_PRIVATE);

        int[] IDs = {R.id.textScore1,R.id.textScore2,R.id.textScore3,R.id.textScore4,R.id.textScore5
                ,R.id.textScore6,R.id.textScore7,R.id.textScore8,R.id.textScore9,R.id.textScore10};

        for (int i = 1; i < 11; i++){
            String currentName = "score" + Integer.toString(i);
            TextView textView = findViewById(IDs[i-1]);
            textView.setText(sharedPref.getString(currentName,"Error" + i));
        }
    }

    public void goHome(View view){
        Intent intent = new Intent(this, TitleScreen.class);
        startActivity(intent);
    }

    public void reset(View view) {
        SharedPreferences sharedPref = getSharedPreferences("highscore", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        for (int i = 1; i < 11; i++) {
            editor.putString("score" + i, "No Score Yet");
        }
        editor.apply();
    }
}
