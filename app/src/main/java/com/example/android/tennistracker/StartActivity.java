package com.example.android.tennistracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.EditText;

public class StartActivity extends AppCompatActivity {
    private EditText editTextA;
    private EditText editTextB;

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
    }

    public void goToMainActivity(View view) {
        String playerAName = editTextA.getText().toString();
        String playerBName = editTextB.getText().toString();

        Intent mainAct = new Intent(this, MainActivity.class);
        mainAct.putExtra("playerAName", playerAName);
        mainAct.putExtra("playerBName", playerBName);
        startActivity(mainAct);
    }
}
