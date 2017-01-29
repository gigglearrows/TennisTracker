package com.example.android.tennistracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Arrays;


public class MainActivity extends AppCompatActivity {

    int scorePlayerA = 0;
    int scorePlayerB = 0;
    int[] setPlayerA = {0, 0, 0, 0, 0};
    int[] setPlayerB = {0, 0, 0, 0, 0};
    int setNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayForPlayerA(scorePlayerA);
        displaySetForPlayerA(setPlayerA);
        displayForPlayerB(scorePlayerB);
        displaySetForPlayerB(setPlayerB);
    }

    /**
     * Displays the given points for player A.
     */
    public void displayForPlayerA(int score) {
        TextView scoreView = (TextView) findViewById(R.id.player_a_score);
        scoreView.setText(String.valueOf(score));
    }

    /**
     * Displays the given game points for player A.
     */
    public void displaySetForPlayerA(int[] score) {
        TextView scoreView = (TextView) findViewById(R.id.player_a_set);
        scoreView.setText(String.valueOf(Arrays.toString(score)));
    }

    /**
     * Adding points to player A and then prints out the score.
     */
    public void addPointA(View v) {
        if (scorePlayerA == 30) {
            scorePlayerA += 10;
        } else if (scorePlayerA == 40) {
            scorePlayerA = 0;
            scorePlayerB = 0;
            addGamePointA();
        } else {
            scorePlayerA += 15;
        }
        displayForPlayerA(scorePlayerA);
        displayForPlayerB(scorePlayerB);
    }

    /**
     * Adding game points to player A. Also automatically starts a new set when reached 6.
     */
    private void addGamePointA() {
        setPlayerA[setNum]++;
        if (setPlayerA[setNum] >= 6) {
            setNum++;
        }

        displaySetForPlayerA(setPlayerA);
    }

    public void plus1A(View v) {
        scorePlayerA += 1;
        displayForPlayerA(scorePlayerA);
    }

    /**
     * Displays the given score for player B.
     */
    public void displayForPlayerB(int score) {
        TextView scoreView = (TextView) findViewById(R.id.player_b_score);
        scoreView.setText(String.valueOf(score));
    }

    /**
     * Displays the given game points for player B.
     */
    public void displaySetForPlayerB(int[] score) {
        TextView scoreView = (TextView) findViewById(R.id.player_b_set);
        scoreView.setText(String.valueOf(Arrays.toString(score)));
    }

    /**
     * Adding points to player B and then prints out the score.
     */
    public void addPointB(View v) {
        if (scorePlayerB == 30) {
            scorePlayerB += 10;
        } else if (scorePlayerB == 40) {
            scorePlayerB = 0;
            scorePlayerA = 0;
            addGamePointB();
        } else {
            scorePlayerB += 15;
        }
        displayForPlayerB(scorePlayerB);
        displayForPlayerA(scorePlayerA);
    }

    /**
     * Adding game points to player B. Also automatically starts a new set when reached 6.
     */
    private void addGamePointB() {
        setPlayerB[setNum]++;
        if (setPlayerB[setNum] >= 6) {
            setNum++;
        }

        displaySetForPlayerB(setPlayerB);
    }

    public void plus1B(View v) {
        scorePlayerB += 1;
        displayForPlayerB(scorePlayerB);
    }

    /**
     * Resets score to 0.
     */
    public void reset(View v) {
        scorePlayerA = 0;
        scorePlayerB = 0;
        Arrays.fill(setPlayerA, 0);
        Arrays.fill(setPlayerB, 0);
        setNum = 0;
        displayForPlayerA(scorePlayerA);
        displaySetForPlayerA(setPlayerA);
        displayForPlayerB(scorePlayerB);
        displaySetForPlayerB(setPlayerB);
    }
}
