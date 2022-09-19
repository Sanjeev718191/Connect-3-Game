package com.example.app10_gameconnect3;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.net.UrlQuerySanitizer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    VideoView backgroundVideo;
    // 0: yellow, 1: red and -1: empty shell
    int activePlayer = 0;

    int[] gameStatus = {-1, -1, -1, -1, -1, -1, -1, -1, -1};

    int[][] winningPositions = {{0, 1, 2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};

    boolean gameActive = true;//use to stop the game when one wins the game

    public void dropin(View view){

        ImageView counter = (ImageView) view;
        int currShell = Integer.parseInt(counter.getTag().toString());

        if(gameStatus[currShell] == -1 && gameActive) {

            gameStatus[currShell] = activePlayer;
            counter.setTranslationY(-1500);

            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.red);
                activePlayer = 0;
            }

            counter.animate().translationYBy(1500).rotation(3600).setDuration(300);
            boolean showButton = false;
            String winner = "";
            for (int[] wPos : winningPositions) {
                if ( gameStatus[wPos[0]] == gameStatus[wPos[1]] && gameStatus[wPos[1]] == gameStatus[wPos[2]] && gameStatus[wPos[0]] != -1) {
                    // Someone has won!
                    gameActive = false;
                    if (activePlayer == 1)
                        winner = "YELLOW";
                    else
                        winner = "RED";
                    showButton = true;
                }
            }
            //check if all shells are fill or not
            int count = 0;
            for(int i = 0; i<9; i++)
                if(gameStatus[i] != -1)
                    count++;
            if(count == 9){
                winner = "No One";
                showButton = true;
            }
            if(showButton){
                Button playAgainButton = (Button) findViewById(R.id.playAgainButton);
                TextView winnerTextView = (TextView) findViewById(R.id.winnerTextView);
                winnerTextView.setText(winner + " has won");
                winnerTextView.setVisibility(View.VISIBLE);
                playAgainButton.setVisibility(View.VISIBLE);
            }
        }
    }
    public void playAgain(View view){
        Button playAgainButton = (Button) findViewById(R.id.playAgainButton);
        TextView winnerTextView = (TextView) findViewById(R.id.winnerTextView);
        winnerTextView.setVisibility(View.INVISIBLE);
        playAgainButton.setVisibility(View.INVISIBLE);

        ImageView iv1 = (ImageView) findViewById(R.id.imageView1);
        iv1.setImageDrawable(null);
        ImageView iv2 = (ImageView) findViewById(R.id.imageView2);
        iv2.setImageDrawable(null);
        ImageView iv3 = (ImageView) findViewById(R.id.imageView3);
        iv3.setImageDrawable(null);
        ImageView iv4 = (ImageView) findViewById(R.id.imageView4);
        iv4.setImageDrawable(null);
        ImageView iv5 = (ImageView) findViewById(R.id.imageView5);
        iv5.setImageDrawable(null);
        ImageView iv6 = (ImageView) findViewById(R.id.imageView6);
        iv6.setImageDrawable(null);
        ImageView iv7 = (ImageView) findViewById(R.id.imageView7);
        iv7.setImageDrawable(null);
        ImageView iv8 = (ImageView) findViewById(R.id.imageView8);
        iv8.setImageDrawable(null);
        ImageView iv9 = (ImageView) findViewById(R.id.imageView9);
        iv9.setImageDrawable(null);

        activePlayer = 0;
        Arrays.fill(gameStatus, -1);
        gameActive = true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        backgroundVideo = findViewById(R.id.backgroundVideo);
        Uri url = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.bubble);
        backgroundVideo.setVideoURI(url);
        backgroundVideo.start();
        backgroundVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });
    }

    @Override
    protected void onPostResume() {
        backgroundVideo.resume();
        super.onPostResume();
    }

    @Override
    protected void onRestart() {
        backgroundVideo.start();
        super.onRestart();
    }

    @Override
    protected void onPause() {
        backgroundVideo.suspend();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        backgroundVideo.stopPlayback();
        super.onDestroy();
    }
}