package com.example.diceroller;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int delayTime = 20;
    int rollAnimationTime = 40;
    int[] diceImages = new int[]{R.drawable.d1, R.drawable.d2, R.drawable.d3, R.drawable.d4, R.drawable.d5, R.drawable.d6};
    Random random = new Random();
    TextView tvHelp;
    ImageView die1;
    ImageView die2;
    LinearLayout diceContainer;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvHelp = findViewById(R.id.tvHelp);
        die1 = findViewById(R.id.die1);
        die2 = findViewById(R.id.die2);
        diceContainer = findViewById(R.id.diceContainer);
        mp = MediaPlayer.create(this, R.raw.roll);
        diceContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    rollDice();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void rollDice() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < rollAnimationTime; i++) {
                    final int finalI = i;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int dice1 = random.nextInt(6);
                            int dice2 = random.nextInt(6);
                            die1.setImageResource(diceImages[dice1]);
                            die2.setImageResource(diceImages[dice2]);
                            if(finalI == rollAnimationTime - 1) {
                                tvHelp.setText("You rolled a " + (dice1 + 1) + " and a " + (dice2 + 1) + "!");
                                mp.start();
                            }
                        }
                    });
                    try {
                        Thread.sleep(delayTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }
}