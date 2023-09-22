package com.example.stadiumcheckers;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(new SampleView(this));
    }

    private static class SampleView extends View {

        // CONSTRUCTOR
        public SampleView(Context context) {
            super(context);
            setFocusable(true);

        }

        @SuppressLint("DrawAllocation")
        @Override
        protected void onDraw(Canvas canvas) {

            canvas.drawColor(Color.WHITE);

            //1
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.GRAY);
            RectF oval1 = new RectF(350, 100, 700, 550);

            Paint p1 = new Paint();
            p1.setColor(Color.BLACK);

            canvas.drawText("Parent", 30, 50, p1);
            canvas.drawOval(oval1, paint);


            //2
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.BLUE);
            RectF oval2 = new RectF(50, 50, 150, 150);

            Paint p2 = new Paint();
            p2.setColor(Color.GREEN);

            canvas.drawText("ring 3", 75, 75, p2);
            canvas.drawOval(oval2, paint);
        }
    }
}