package com.example.android.tennistracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

public class StartActivity extends AppCompatActivity {
    private EditText editTextA;
    private EditText editTextB;
    private int setsToWin = 3;

    private InputFilter filter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            for (int i = start; i < end; i++) {
                if (!Character.isLetterOrDigit(source.charAt(i)) && !Character.isWhitespace(source.charAt(i))) {
                    return "";
                }
            }
            return null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startscreen);
        editTextA = (EditText) findViewById(R.id.player_a_name);
        editTextA.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(35)});
        editTextB = (EditText) findViewById(R.id.player_b_name);
        editTextB.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(35)});

        // Check whether we're recreating a previously destroyed instance
        if (savedInstanceState != null) {
            // Restore value of members from saved state
            setsToWin = savedInstanceState.getInt("SETS_TO_WIN");
        } else {
            // Probably initialize members with default values for a new instance
            setsToWin = 3;
        }
    }

    /**
     * Starts the main activity
     */
    public void goToMainActivity(View view) {
        String playerAName = editTextA.getText().toString();
        String playerBName = editTextB.getText().toString();

        Intent mainAct = new Intent(this, MainActivity.class);
        mainAct.putExtra("playerAName", playerAName);
        mainAct.putExtra("playerBName", playerBName);
        mainAct.putExtra("setsToWin", setsToWin);
        startActivity(mainAct);
    }

    /**
     * When radiobutton is clicked. check which one and asign the right gamemode
     */
    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.radio_3games:
                if (checked)
                    setsToWin = 2;
                break;
            case R.id.radio_5games:
                if (checked)
                    setsToWin = 3;
                break;
        }
    }

    /**
     * Save state so that variable is not reset on rotating screen
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        savedInstanceState.putInt("SETS_TO_WIN", setsToWin);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }
}
