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
    ArrayList<Integer> setPlayerA = new ArrayList<>();
    ArrayList<Integer> setPlayerB = new ArrayList<>();
    int setNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setPlayerA.add(0);
        setPlayerB.add(0);
        displayForPlayerA(pointsPlayerA);
        displaySetForPlayerA(setPlayerA);
        displayForPlayerB(pointsPlayerB);
        displaySetForPlayerB(setPlayerB);
    }

    /**
     * Displays the given points for player A.
     */
    public void displayForPlayerA(int points) {
        TextView scoreView = (TextView) findViewById(R.id.player_a_score);
        if (points > 40) {
            scoreView.setText(String.valueOf("Adv"));
        } else {
            scoreView.setText(String.valueOf(points));
        }
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
     * If its 40-40 it's deuce and a player need to win with 2 clear points.
     */
    public void addPointA(View v) {
        if (pointsPlayerA == 30) {
            pointsPlayerA += 10;
        } else if (pointsPlayerA == 40) {
            if (pointsPlayerB > 40) {
                pointsPlayerB -= 15;
            } else if (pointsPlayerB == 40) {
                pointsPlayerA += 15;
            } else {
                pointsPlayerA = 0;
                pointsPlayerB = 0;
                addGamePointA();
            }
        } else if (pointsPlayerA == 55) {
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
        Integer value = setPlayerA.get(setNum);
        value++;
        setPlayerA.set(setNum, value);

        if (value >= 6) {
            setNum++;
            setPointsPlayerA++;

            if (setPointsPlayerA < 3) {
                setPlayerA.add(setNum, 0);
                setPlayerB.add(setNum, 0);
                displaySetForPlayerB(setPlayerB);
            } else {
                //playerAWins();
                Toast.makeText(getApplicationContext(), "Player A Wins!", Toast.LENGTH_LONG).show();
                reset();
            }

        }


        displaySetForPlayerA(setPlayerA);
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
        if (points > 40) {
            scoreView.setText(String.valueOf("Adv"));
        } else {
            scoreView.setText(String.valueOf(points));
        }
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
     * If its 40-40 it's deuce and a player need to win with 2 clear points.
     */
    public void addPointB(View v) {
        if (pointsPlayerB == 30) {
            pointsPlayerB += 10;
        } else if (pointsPlayerB == 40) {
            if (pointsPlayerA > 40) {
                pointsPlayerA -= 15;
            } else if (pointsPlayerA == 40) {
                pointsPlayerB += 15;
            } else {
                pointsPlayerB = 0;
                pointsPlayerA = 0;
                addGamePointB();
            }
        } else if (pointsPlayerB == 55) {
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
        Integer value = setPlayerB.get(setNum);
        value++;
        setPlayerB.set(setNum, value);


        if (value >= 6) {
            setNum++;
            setPointsPlayerB++;

            if (setPointsPlayerB < 3) {
                setPlayerB.add(setNum, 0);
                setPlayerA.add(setNum, 0);
                displaySetForPlayerA(setPlayerA);
            } else {
                //playerBWins();
                Toast.makeText(getApplicationContext(), "Player B Wins!", Toast.LENGTH_LONG).show();
                reset();
            }
        }

        displaySetForPlayerB(setPlayerB);
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
        setPlayerA.clear();
        setPlayerA.add(0);
        setPlayerB.clear();
        setPlayerB.add(0);
        displayForPlayerA(pointsPlayerA);
        displaySetForPlayerA(setPlayerA);
        displayForPlayerB(pointsPlayerB);
        displaySetForPlayerB(setPlayerB);
    }
}
