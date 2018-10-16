package cisc181.danmaku;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EnterName extends AppCompatActivity {

    String[] scoreNames;
    int[] highScores;
    int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_name_screen);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        score = intent.getIntExtra("SCORE", 0);

        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.ScoreLocation);
        textView.setText(Integer.toString(score));

        SharedPreferences sharedPreferences = getSharedPreferences("highscore", Context.MODE_PRIVATE);

        scoreNames = new String[10];
        highScores = new int[10];

        for (int i = 1; i < 11; i++) {
            String currentName = "score" + Integer.toString(i);
            String scores = sharedPreferences.getString(currentName,"a_0");
            ScoreData currentData = make_Score_object(scores);
            scoreNames[i-1] = currentData.name;
            highScores[i-1] = currentData.score;
        }
    }

    public void score_Submit(View view) {
        Intent intent = new Intent(this, TitleScreen.class);
        EditText editText = findViewById(R.id.editText2);
        String name_in = editText.getText().toString();
        adjustScores(name_in,score);
        startActivity(intent);
    }

    private ScoreData make_Score_object(String line) {
        if (line.equals("No Score Yet")){
            line = "a_0";
        }
        String[] new_line = line.split("_");
        String name = "";
        for (int i = 0; i < new_line.length - 1; i++) {
            name += new_line[i];
        }
        int number = Integer.parseInt(new_line[new_line.length-1]);
        return new ScoreData(name,number);
    }

    private void adjustScores(String Name, int Score) {
        int checkScore = Score;
        String checkName = Name;

        int holderScore;
        String holderName;

        for (int i = 0; i < 10; i++) {
            if (checkScore > highScores[i]) {
                holderScore = highScores[i];
                highScores[i] = checkScore;
                checkScore = holderScore;

                holderName = scoreNames[i];
                scoreNames[i] = checkName;
                checkName = holderName;
            }
        }

        SharedPreferences sharedPref = getSharedPreferences("highscore", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        for (int i = 1; i < 11; i++){
            if (highScores[i - 1] == 0){
                editor.putString("score" + i,"No Score Yet" );
            }
            else {
                editor.putString("score" + i, scoreNames[i - 1] + "_" + Integer.toString(highScores[i - 1]));
            }
        }

        editor.apply();

    }

    private class ScoreData{
        public String name;
        public int score;

        ScoreData (String n, int s){
            name = n;
            score = s;
        }
    }
}
