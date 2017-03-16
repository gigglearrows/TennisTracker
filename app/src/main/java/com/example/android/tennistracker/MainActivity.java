package com.example.android.tennistracker;

import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    int pointsPlayerA = 0;
    int pointsPlayerB = 0;
    int setPointsPlayerA = 0;
    int setPointsPlayerB = 0;
    ArrayList<Integer> setPlayerA = new ArrayList<>();
    ArrayList<Integer> setPlayerB = new ArrayList<>();
    int setNum = 0;
    int faultsPlayerA = 0;
    int faultsPlayerB = 0;
    String playerAName;
    String playerBName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (!extras.getString("playerAName").isEmpty()) {
                playerAName = extras.getString("playerAName");
                ((TextView) findViewById(R.id.player_a_name_main)).setText(playerAName);
                ((TextView) findViewById(R.id.tableHeaderPlayerA)).setText(playerAName);
            } else {
                playerAName = getString(R.string.player_a_name);
            }
            if (!extras.getString("playerBName").isEmpty()) {
                playerBName = extras.getString("playerBName");
                ((TextView) findViewById(R.id.player_b_name_main)).setText(playerBName);
                ((TextView) findViewById(R.id.tableHeaderPlayerB)).setText(playerBName);
            } else {
                playerBName = getString(R.string.player_b_name);
            }
        }

        setPlayerA.add(0);
        setPlayerB.add(0);
        displayForPlayerA(pointsPlayerA);
        displaySetForPlayerA(setPlayerA);
        displayForPlayerB(pointsPlayerB);
        displaySetForPlayerB(setPlayerB);
    }

    /**
     * Displays the given points for player A.
     *
     * @param points points to display for player A
     */
    public void displayForPlayerA(int points) {
        TextView scoreView = (TextView) findViewById(R.id.player_a_score);
        if (points > 40) {
            scoreView.setText(getString(R.string.advantage_msg));
        } else {
            scoreView.setText(String.valueOf(points));
        }
        if (pointsPlayerA == 40 && pointsPlayerB == 40) {
            findViewById(R.id.deuce_text).setVisibility(View.VISIBLE);
        } else if (findViewById(R.id.deuce_text).getVisibility() == View.VISIBLE) {
            findViewById(R.id.deuce_text).setVisibility(View.GONE);
        }
    }

    /**
     * Displays the given game points for player A.
     *
     * @param gamePoints ArrayList with game points for player A to display
     */
    public void displaySetForPlayerA(ArrayList<Integer> gamePoints) {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.player_a_set);

        if (linearLayout.getChildCount() > 0) {
            linearLayout.removeAllViews();
        }

        for (int i : gamePoints) {
            TextView textView = new TextView(new ContextThemeWrapper(MainActivity.this, R.style.TableText));
            LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            llp.setMargins(8, 0, 0, 0);
            textView.setLayoutParams(llp);
            textView.setPadding(32, 0, 32, 0);
            if (i > 5) textView.setTypeface(null, Typeface.BOLD);
            textView.setText(String.valueOf(i));
            textView.setBackgroundResource(R.drawable.border);
            linearLayout.addView(textView);
        }
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
        if (faultsPlayerA > 0 || faultsPlayerB > 0) {
            resetFaults();
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
                Toast.makeText(getApplicationContext(), playerAName + " " + getString(R.string.setPoint_msg) + " #" + setNum + "!", Toast.LENGTH_LONG).show();
            } else {
                matchWin(playerAName + " " + getString(R.string.matchPoint_msg_1) + " " + setPointsPlayerA + " - " + setPointsPlayerB + " " + getString(R.string.matchPoint_msg_2) + " " + playerBName + ".");
            }
        }

        displaySetForPlayerA(setPlayerA);
    }

    /**
     * Adds fault to player A. Displays icon if 1 fault, removes it if 2 faults
     * and gives point to player B.
     */
    public void addFaultA(View v) {
        if (faultsPlayerB < 1) {
            faultsPlayerA += 1;
            TextView FaultViewA = (TextView) findViewById(R.id.player_a_fault);
            FaultViewA.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.x, 0);

            if (faultsPlayerA == 2) {
                FaultViewA.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                faultsPlayerA = 0;
                addPointB(v);
            }
        }
    }

    /**
     * Displays the given score for player B.
     * @param points number of points for player B to display
     */
    public void displayForPlayerB(int points) {
        TextView scoreView = (TextView) findViewById(R.id.player_b_score);
        if (points > 40) {
            scoreView.setText(getString(R.string.advantage_msg));
        } else {
            scoreView.setText(String.valueOf(points));
        }
        if (pointsPlayerA == 40 && pointsPlayerB == 40) {
            findViewById(R.id.deuce_text).setVisibility(View.VISIBLE);
        } else if (findViewById(R.id.deuce_text).getVisibility() == View.VISIBLE) {
            findViewById(R.id.deuce_text).setVisibility(View.GONE);
        }
    }

    /**
     * Displays the given game points for player B.
     * @param gamePoints Arraylist containing the game points for player B
     */
    public void displaySetForPlayerB(ArrayList<Integer> gamePoints) {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.player_b_set);
        if (linearLayout.getChildCount() > 0) {
            linearLayout.removeAllViews();
        }

        for (int i : gamePoints) {
            TextView textView = new TextView(new ContextThemeWrapper(MainActivity.this, R.style.TableText));
            LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            llp.setMargins(8, 0, 0, 0);
            textView.setLayoutParams(llp);
            textView.setPadding(32, 0, 32, 0);
            textView.setText(String.valueOf(i));
            if (i > 5) textView.setTypeface(null, Typeface.BOLD);
            textView.setBackgroundResource(R.drawable.border);
            linearLayout.addView(textView);
        }
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
        if (faultsPlayerA > 0 || faultsPlayerB > 0) {
            resetFaults();
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
                Toast.makeText(getApplicationContext(), playerBName + " " + getString(R.string.setPoint_msg) + " #" + setNum + "!", Toast.LENGTH_LONG).show();
            } else {
                matchWin(playerBName + " " + getString(R.string.matchPoint_msg_1) + " " + setPointsPlayerB + " - " + setPointsPlayerA + " " + getString(R.string.matchPoint_msg_2) + " " + playerAName + ".");
            }
        }

        displaySetForPlayerB(setPlayerB);
    }

    /**
     * Adds fault to player B. Displays icon if 1 fault, removes it if 2 faults
     * and gives point to player A.
     */
    public void addFaultB(View v) {
        if (faultsPlayerA < 1) {
            faultsPlayerB += 1;
            TextView FaultViewB = (TextView) findViewById(R.id.player_b_fault);
            FaultViewB.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.x, 0);

            if (faultsPlayerB == 2) {
                FaultViewB.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                faultsPlayerB = 0;
                addPointA(v);
            }
        }
    }

    /**
     * Win match for player X. Opens up an AlertDialog
     *
     * @param winMessage Message to display (e.g Player X wins!)
     */
    private void matchWin(String winMessage) {
        Log.i(TAG, "END of game");
        AlertDialog.Builder winDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        winDialogBuilder.setCancelable(false)
                .setTitle(getString(R.string.matchPoint_title))
                .setMessage(winMessage)
                .setPositiveButton("Quit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("New Game", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        reset();
                    }
                });
        AlertDialog winDialog = winDialogBuilder.create();
        winDialog.show();
    }

    /**
     * Resets faults to 0 and removes fault icons.
     */
    private void resetFaults() {
        faultsPlayerA = 0;
        faultsPlayerB = 0;

        TextView FaultViewA = (TextView) findViewById(R.id.player_a_fault);
        TextView FaultViewB = (TextView) findViewById(R.id.player_b_fault);
        FaultViewA.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        FaultViewB.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
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
        resetFaults();
    }
}