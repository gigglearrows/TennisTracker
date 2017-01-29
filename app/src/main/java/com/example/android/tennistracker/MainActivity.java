package com.example.android.tennistracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    int pointsPlayerA = 0;
    int pointsPlayerB = 0;
    int setPointsPlayerA = 0;
    int setPointsPlayerB = 0;
    int[] setPlayerA = {0, 0, 0, 0, 0};
    int[] setPlayerB = {0, 0, 0, 0, 0};
    ArrayList<Integer> setPlayerAA = new ArrayList<>();
    ArrayList<Integer> setPlayerBB = new ArrayList<>();
    int setNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setPlayerAA.add(0);
        setPlayerBB.add(0);
        displayForPlayerA(pointsPlayerA);
        displaySetForPlayerA(setPlayerAA);
        displayForPlayerB(pointsPlayerB);
        displaySetForPlayerB(setPlayerBB);
    }

    /**
     * Displays the given points for player A.
     */
    public void displayForPlayerA(int points) {
        TextView scoreView = (TextView) findViewById(R.id.player_a_score);
        scoreView.setText(String.valueOf(points));
    }

    /**
     * Displays the given game points for player A.
     */
    public void displaySetForPlayerA(ArrayList gamePoints) {
        TextView scoreView = (TextView) findViewById(R.id.player_a_set);
        scoreView.setText(String.valueOf(gamePoints));
    }

    /**
     * Adding points to player A and then prints out the score.
     */
    public void addPointA(View v) {
        if (pointsPlayerA == 30) {
            pointsPlayerA += 10;
        } else if (pointsPlayerA == 40) {
            pointsPlayerA = 0;
            pointsPlayerB = 0;
            addGamePointA();
        } else {
            pointsPlayerA += 15;
        }
        displayForPlayerA(pointsPlayerA);
        displayForPlayerB(pointsPlayerB);
    }

    /**
     * Adding game points to player A. Also automatically starts a new set when reached 6.
     */
    private void addGamePointA() {
        Integer value = setPlayerAA.get(setNum);
        value++;
        setPlayerAA.set(setNum, value);

        if (value >= 6) {
            setNum++;
            setPointsPlayerA++;

            if (setPointsPlayerA < 3) {
                setPlayerAA.add(setNum, 0);
                setPlayerBB.add(setNum, 0);
                displaySetForPlayerB(setPlayerBB);
            } else {
                //playerAWins();
                Toast.makeText(getApplicationContext(), "Player A Wins!", Toast.LENGTH_LONG).show();
                reset();
            }

        }


        displaySetForPlayerA(setPlayerAA);
    }

    public void plus1A(View v) {
        pointsPlayerA += 1;
        displayForPlayerA(pointsPlayerA);
    }

    /**
     * Displays the given score for player B.
     */
    public void displayForPlayerB(int points) {
        TextView scoreView = (TextView) findViewById(R.id.player_b_score);
        scoreView.setText(String.valueOf(points));
    }

    /**
     * Displays the given game points for player B.
     */
    public void displaySetForPlayerB(ArrayList gamePoints) {
        TextView scoreView = (TextView) findViewById(R.id.player_b_set);
        scoreView.setText(String.valueOf(gamePoints));
    }

    /**
     * Adding points to player B and then prints out the score.
     */
    public void addPointB(View v) {
        if (pointsPlayerB == 30) {
            pointsPlayerB += 10;
        } else if (pointsPlayerB == 40) {
            pointsPlayerB = 0;
            pointsPlayerA = 0;
            addGamePointB();
        } else {
            pointsPlayerB += 15;
        }
        displayForPlayerB(pointsPlayerB);
        displayForPlayerA(pointsPlayerA);
    }

    /**
     * Adding game points to player B. Also automatically starts a new set when reached 6.
     */
    private void addGamePointB() {
        Integer value = setPlayerBB.get(setNum);
        value++;
        setPlayerBB.set(setNum, value);


        if (value >= 6) {
            setNum++;
            setPointsPlayerB++;

            if (setPointsPlayerB < 3) {
                setPlayerBB.add(setNum, 0);
                setPlayerAA.add(setNum, 0);
                displaySetForPlayerA(setPlayerAA);
            } else {
                //playerBWins();
                Toast.makeText(getApplicationContext(), "Player B Wins!", Toast.LENGTH_LONG).show();
                reset();
            }
        }

        displaySetForPlayerB(setPlayerBB);
    }

    public void plus1B(View v) {
        pointsPlayerB += 1;
        displayForPlayerB(pointsPlayerB);
    }

    /**
     * Resets score to 0.
     */
    public void resetBtn(View v) {
        reset();
    }

    private void reset() {
        pointsPlayerA = 0;
        pointsPlayerB = 0;
        setPointsPlayerA = 0;
        setPointsPlayerB = 0;
        setNum = 0;
        setPlayerAA.clear();
        setPlayerAA.add(0);
        setPlayerBB.clear();
        setPlayerBB.add(0);
        displayForPlayerA(pointsPlayerA);
        displaySetForPlayerA(setPlayerAA);
        displayForPlayerB(pointsPlayerB);
        displaySetForPlayerB(setPlayerBB);
    }
}
