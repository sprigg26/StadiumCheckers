package com.example.stadiumcheckers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.SurfaceView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        GameView view = findViewById(R.id.gameView);
        LinearLayout layout = findViewById(R.id.layout);

        view.setScale(layout.getWidth(), layout.getHeight());
        view.invalidate();
    }
}