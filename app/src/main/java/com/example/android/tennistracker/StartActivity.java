package com.example.android.tennistracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class StartActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startscreen);

    }

    public void goToMainActivity(View view) {
        EditText editTextA = (EditText) findViewById(R.id.player_a_name);
        EditText editTextB = (EditText) findViewById(R.id.player_b_name);
        String playerAName = editTextA.getText().toString();
        String playerBName = editTextB.getText().toString();

        Intent mainAct = new Intent(this, MainActivity.class);
        mainAct.putExtra("playerAName", playerAName);
        mainAct.putExtra("playerBName", playerBName);
        startActivity(mainAct);
    }
}
