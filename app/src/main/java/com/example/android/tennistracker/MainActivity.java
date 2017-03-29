package com.example.android.tennistracker;

import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.util.TypedValue;
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
    TextView playerANameLabel;
    TextView playerBNameLabel;
    TextView FaultViewB;
    TextView FaultViewA;
    LinearLayout linearLayoutA;
    LinearLayout linearLayoutB;
    TextView scoreViewA;
    TextView scoreViewB;
    TextView dueceView;
    TextView tieBreakView;
    CardView cardViewA;
    CardView cardViewB;
    TextView tableHeaderPlayerA;
    TextView tableHeaderPlayerB;
    int tableHeight;
    int tablePadding;
    int tablePaddingV;
    boolean aHasTwoSetsMore = false;
    boolean bHasTwoSetsMore = false;
    boolean isTieBreak = false;
    private int setsToWin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setHomeButtonEnabled(true);

        playerANameLabel = (TextView) findViewById(R.id.player_a_name_main);
        playerBNameLabel = (TextView) findViewById(R.id.player_b_name_main);
        linearLayoutA = (LinearLayout) findViewById(R.id.player_a_set);
        linearLayoutB = (LinearLayout) findViewById(R.id.player_b_set);
        FaultViewA = (TextView) findViewById(R.id.player_a_fault);
        FaultViewB = (TextView) findViewById(R.id.player_b_fault);
        scoreViewA = (TextView) findViewById(R.id.player_a_score);
        scoreViewB = (TextView) findViewById(R.id.player_b_score);
        dueceView = (TextView) findViewById(R.id.deuce_text);
        tieBreakView = (TextView) findViewById(R.id.tiebreak_text);
        cardViewA = (CardView) findViewById(R.id.card_viewA);
        cardViewB = (CardView) findViewById(R.id.card_viewB);
        tableHeaderPlayerA = (TextView) findViewById(R.id.tableHeaderPlayerA);
        tableHeaderPlayerB = (TextView) findViewById(R.id.tableHeaderPlayerB);

        tableHeight = (int) getResources().getDimension(R.dimen.tableText);
        tablePadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
        tablePaddingV = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());
        int headerPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());

        tableHeaderPlayerA.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.headerText));
        tableHeaderPlayerA.setPadding(tablePadding, headerPadding, tablePadding, headerPadding);
        tableHeaderPlayerB.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.headerText));
        tableHeaderPlayerB.setPadding(tablePadding, headerPadding, tablePadding, headerPadding);


        // Check whether we're recreating a previously destroyed instance
        if (savedInstanceState != null) {
            // Restore value of members from saved state

            pointsPlayerA = savedInstanceState.getInt("POINTS_PLAYER_A");
            pointsPlayerB = savedInstanceState.getInt("POINTS_PLAYER_B");
            setPointsPlayerA = savedInstanceState.getInt("SET_POINTS_PLAYER_A");
            setPointsPlayerB = savedInstanceState.getInt("SET_POINTS_PLAYER_B");
            setPlayerA = savedInstanceState.getIntegerArrayList("SET_ARRAY_PLAYER_A");
            setPlayerB = savedInstanceState.getIntegerArrayList("SET_ARRAY_PLAYER_B");
            setNum = savedInstanceState.getInt("SET_NUMBER");
            faultsPlayerA = savedInstanceState.getInt("FAULTS_PLAYER_A");
            faultsPlayerB = savedInstanceState.getInt("FAULTS_PLAYER_B");
            playerAName = savedInstanceState.getString("PLAYER_A_NAME");
            playerBName = savedInstanceState.getString("PLAYER_B_NAME");
            aHasTwoSetsMore = savedInstanceState.getBoolean("A_HAS_TWO_SETS_MORE");
            bHasTwoSetsMore = savedInstanceState.getBoolean("B_HAS_TWO_SETS_MORE");
            isTieBreak = savedInstanceState.getBoolean("IS_TIEBREAK");
            setsToWin = savedInstanceState.getInt("SETS_TO_WIN");

            if (isTieBreak) changeToTieBreak();

        } else {
            // Probably initialize members with default values for a new instance
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                if (extras.containsKey("playerAName") && !extras.getString("playerAName").isEmpty()) {
                    playerAName = extras.getString("playerAName");
                    playerANameLabel.setText(playerAName);
                    ((TextView) findViewById(R.id.tableHeaderPlayerA)).setText(playerAName);
                } else {
                    playerAName = getString(R.string.player_a_name);
                }
                if (extras.containsKey("playerBName") && !extras.getString("playerBName").isEmpty()) {
                    playerBName = extras.getString("playerBName");
                    playerBNameLabel.setText(playerBName);
                    ((TextView) findViewById(R.id.tableHeaderPlayerB)).setText(playerBName);
                } else {
                    playerBName = getString(R.string.player_b_name);
                }
                if (extras.containsKey("setsToWin")) {
                    setsToWin = extras.getInt("setsToWin");
                } else {
                    setsToWin = 3;
                }
            }

            setPlayerA.add(0);
            setPlayerB.add(0);
        }

        displayForPlayerA(pointsPlayerA);
        displaySetForPlayerA(setPlayerA);
        displayForPlayerB(pointsPlayerB);
        displaySetForPlayerB(setPlayerB);
        displayFaultA();
        displayFaultB();
    }

    /**
     * Save state so that variable is not reset on rotating screen
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        savedInstanceState.putInt("POINTS_PLAYER_A", pointsPlayerA);
        savedInstanceState.putInt("POINTS_PLAYER_B", pointsPlayerB);
        savedInstanceState.putInt("SET_POINTS_PLAYER_A", setPointsPlayerA);
        savedInstanceState.putInt("SET_POINTS_PLAYER_B", setPointsPlayerB);
        savedInstanceState.putIntegerArrayList("SET_ARRAY_PLAYER_A", setPlayerA);
        savedInstanceState.putIntegerArrayList("SET_ARRAY_PLAYER_B", setPlayerB);
        savedInstanceState.putInt("SET_NUMBER", setNum);
        savedInstanceState.putInt("FAULTS_PLAYER_A", faultsPlayerA);
        savedInstanceState.putInt("FAULTS_PLAYER_B", faultsPlayerB);
        savedInstanceState.putString("PLAYER_A_NAME", playerAName);
        savedInstanceState.putString("PLAYER_B_NAME", playerBName);
        savedInstanceState.putBoolean("A_HAS_TWO_SETS_MORE", aHasTwoSetsMore);
        savedInstanceState.putBoolean("B_HAS_TWO_SETS_MORE", bHasTwoSetsMore);
        savedInstanceState.putBoolean("IS_TIEBREAK", isTieBreak);
        savedInstanceState.putInt("SETS_TO_WIN", setsToWin);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    /**
     * Displays the given points for player A.
     *
     * @param points points to display for player A
     */
    public void displayForPlayerA(int points) {
        if (points > 40 && !isTieBreak) {
            scoreViewA.setText(getString(R.string.advantage_msg));
        } else {
            scoreViewA.setText(String.valueOf(points));
        }
        if (pointsPlayerA == 40 && pointsPlayerB == 40 && !isTieBreak) {
            dueceView.setVisibility(View.VISIBLE);
        } else if (dueceView.getVisibility() == View.VISIBLE) {
            dueceView.setVisibility(View.GONE);
        }
    }

    /**
     * Displays the given game points for player A.
     *
     * @param gamePoints ArrayList with game points for player A to display
     */
    public void displaySetForPlayerA(ArrayList<Integer> gamePoints) {

        if (linearLayoutA.getChildCount() > 0) {
            linearLayoutA.removeAllViews();
        }

        int iNumber = 0;
        for (int i : gamePoints) {
            TextView textView = new TextView(new ContextThemeWrapper(MainActivity.this, R.style.TableText));
            LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            llp.setMargins(tablePaddingV, 0, 0, 0);
            textView.setLayoutParams(llp);
            textView.setPadding(tablePadding, tablePaddingV, tablePadding, tablePaddingV);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, tableHeight);
            if ((i > 5 && (i - setPlayerB.get(iNumber)) >= 2) || i == 7)
                textView.setTypeface(null, Typeface.BOLD);
            textView.setText(String.valueOf(i));
            textView.setBackgroundResource(R.drawable.border);
            linearLayoutA.addView(textView);
            iNumber++;
        }
    }

    /**
     * Adding points to player A and then prints out the score.
     * If its 40-40 it's deuce and a player need to win with 2 clear points.
     */
    public void addPointA(View v) {
        if (isTieBreak) {
            pointsPlayerA++;
            if (pointsPlayerA >= 6 && (pointsPlayerA - pointsPlayerB >= 2)) {
                pointsPlayerA = 0;
                pointsPlayerB = 0;
                addGamePointA();
                changeBack();
            }
        } else if (pointsPlayerA == 30) {
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
        Integer valueB = setPlayerB.get(setNum);
        value++;
        setPlayerA.set(setNum, value);
        aHasTwoSetsMore = (value - valueB >= 2);

        if ((value >= 6 && aHasTwoSetsMore) || isTieBreak) {
            setNum++;
            setPointsPlayerA++;

            if (setPointsPlayerA < setsToWin) {
                setPlayerA.add(setNum, 0);
                setPlayerB.add(setNum, 0);
                displaySetForPlayerB(setPlayerB);
                Toast.makeText(getApplicationContext(), playerAName + " " + getString(R.string.setPoint_msg) + " #" + setNum + "!", Toast.LENGTH_LONG).show();
            } else {
                matchWin(playerAName + " " + getString(R.string.matchPoint_msg_1) + " " + setPointsPlayerA + " - " + setPointsPlayerB + " " + getString(R.string.matchPoint_msg_2) + " " + playerBName + ".");
            }
        } else if (value == 6 && valueB == 6) {
            changeToTieBreak();
        }

        displaySetForPlayerA(setPlayerA);
    }

    /**
     * Displays faults for player A.
     */
    public void displayFaultA() {
        if (faultsPlayerB < 1) {
            if (faultsPlayerA == 1)
                FaultViewA.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.x, 0);
            else if (faultsPlayerA == 2)
                FaultViewA.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
    }

    /**
     * Adds fault to player A. Displays icon if 1 fault, removes it if 2 faults
     * and gives point to player B.
     */
    public void addFaultA(View v) {
        if (faultsPlayerB < 1) {
            faultsPlayerA += 1;
            displayFaultA();

            if (faultsPlayerA == 2) {
                faultsPlayerA = 0;
                addPointB(v);
            }
        }
    }

    /**
     * Displays the given score for player B.
     *
     * @param points number of points for player B to display
     */
    public void displayForPlayerB(int points) {
        if (points > 40 && !isTieBreak) {
            scoreViewB.setText(getString(R.string.advantage_msg));
        } else {
            scoreViewB.setText(String.valueOf(points));
        }
        if (pointsPlayerA == 40 && pointsPlayerB == 40 && !isTieBreak) {
            dueceView.setVisibility(View.VISIBLE);
        } else if (dueceView.getVisibility() == View.VISIBLE) {
            dueceView.setVisibility(View.GONE);
        }
    }

    /**
     * Displays the given game points for player B.
     *
     * @param gamePoints Arraylist containing the game points for player B
     */
    public void displaySetForPlayerB(ArrayList<Integer> gamePoints) {
        if (linearLayoutB.getChildCount() > 0) {
            linearLayoutB.removeAllViews();
        }

        int iNumber = 0;
        for (int i : gamePoints) {
            TextView textView = new TextView(new ContextThemeWrapper(MainActivity.this, R.style.TableText));
            LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            llp.setMargins(tablePaddingV, 0, 0, 0);
            textView.setLayoutParams(llp);
            textView.setPadding(tablePadding, tablePaddingV, tablePadding, tablePaddingV);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, tableHeight);
            if ((i == 6 && (i - setPlayerA.get(iNumber)) >= 2) || i == 7)
                textView.setTypeface(null, Typeface.BOLD);
            textView.setText(String.valueOf(i));
            textView.setBackgroundResource(R.drawable.border);
            linearLayoutB.addView(textView);
            iNumber++;
        }
    }

    /**
     * Adding points to player B and then prints out the score.
     * If its 40-40 it's deuce and a player need to win with 2 clear points.
     */
    public void addPointB(View v) {
        if (isTieBreak) {
            pointsPlayerB++;
            if (pointsPlayerB >= 6 && (pointsPlayerB - pointsPlayerA >= 2)) {
                pointsPlayerA = 0;
                pointsPlayerB = 0;
                addGamePointB();
                changeBack();
            }
        } else if (pointsPlayerB == 30) {
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
        Integer valueA = setPlayerA.get(setNum);
        value++;
        setPlayerB.set(setNum, value);
        bHasTwoSetsMore = (value - valueA >= 2);

        if ((value >= 6 && bHasTwoSetsMore) || isTieBreak) {
            setNum++;
            setPointsPlayerB++;

            if (setPointsPlayerB < setsToWin) {
                setPlayerB.add(setNum, 0);
                setPlayerA.add(setNum, 0);
                displaySetForPlayerA(setPlayerA);
                Toast.makeText(getApplicationContext(), playerBName + " " + getString(R.string.setPoint_msg) + " #" + setNum + "!", Toast.LENGTH_LONG).show();
            } else {
                matchWin(playerBName + " " + getString(R.string.matchPoint_msg_1) + " " + setPointsPlayerB + " - " + setPointsPlayerA + " " + getString(R.string.matchPoint_msg_2) + " " + playerAName + ".");
            }
        } else if (value == 6 && valueA == 6) {
            changeToTieBreak();
        }

        displaySetForPlayerB(setPlayerB);
    }

    /**
     * Displays faults for player B.
     */
    public void displayFaultB() {
        if (faultsPlayerA < 1) {
            if (faultsPlayerB == 1)
                FaultViewB.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.x, 0);

            if (faultsPlayerB == 2)
                FaultViewB.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
    }

    /**
     * Adds fault to player B. Displays icon if 1 fault, removes it if 2 faults
     * and gives point to player A.
     */
    public void addFaultB(View v) {
        if (faultsPlayerA < 1) {
            faultsPlayerB += 1;
            displayFaultB();

            if (faultsPlayerB == 2) {
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
        changeBack();
    }

    /**
     * Changing colors on tiebreak
     */
    private void changeToTieBreak() {
        isTieBreak = true;
        tieBreakView.setVisibility(View.VISIBLE);
        cardViewA.setBackgroundColor(ContextCompat.getColor(this, R.color.dark));
        playerANameLabel.setTextColor(ContextCompat.getColor(this, R.color.almostWhiteCC));
        cardViewB.setBackgroundColor(ContextCompat.getColor(this, R.color.dark));
        playerBNameLabel.setTextColor(ContextCompat.getColor(this, R.color.almostWhiteCC));
        scoreViewA.setTextColor(ContextCompat.getColor(this, R.color.almostWhite));
        scoreViewB.setTextColor(ContextCompat.getColor(this, R.color.almostWhite));
        FaultViewA.setTextColor(ContextCompat.getColor(this, R.color.almostWhiteBB));
        FaultViewB.setTextColor(ContextCompat.getColor(this, R.color.almostWhiteBB));
    }

    /**
     * Changing colors back from tiebreakcolors
     */
    private void changeBack() {
        isTieBreak = false;
        tieBreakView.setVisibility(View.GONE);
        cardViewA.setBackgroundColor(ContextCompat.getColor(this, R.color.almostWhite));
        playerANameLabel.setTextColor(ContextCompat.getColor(this, R.color.darkCC));
        cardViewB.setBackgroundColor(ContextCompat.getColor(this, R.color.almostWhite));
        playerBNameLabel.setTextColor(ContextCompat.getColor(this, R.color.darkCC));
        scoreViewA.setTextColor(ContextCompat.getColor(this, R.color.dark));
        scoreViewB.setTextColor(ContextCompat.getColor(this, R.color.dark));
        FaultViewA.setTextColor(ContextCompat.getColor(this, R.color.darkBB));
        FaultViewB.setTextColor(ContextCompat.getColor(this, R.color.darkBB));
    }

}