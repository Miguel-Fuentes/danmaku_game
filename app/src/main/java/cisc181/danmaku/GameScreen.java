package cisc181.danmaku;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class GameScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);
    }

    // invoked when the activity loses user focus.

    @Override
    protected void onPause() {
        super.onPause();
        finish();   // just die...getting force close on resume otherwise...
    }

    public void endGame(View view){
        Intent intent = new Intent(this,EnterName.class);
        startActivity(intent);
    }
}