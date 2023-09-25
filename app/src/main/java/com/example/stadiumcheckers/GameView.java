package com.example.stadiumcheckers;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.SurfaceView;

public class GameView extends SurfaceView {
    private int width;
    private int height;
    private final Paint ringPaint;
    private final Paint ringPaint2;
    private final Paint slotPaint;
    private boolean ringB = false;
    private final int[] ringSlots = {4, 5, 4, 6, 5, 6, 7, 6};

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setWillNotDraw(false);

        ringPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        ringPaint.setColor(0xFFFFD700);
        ringPaint.setStyle(Paint.Style.FILL);

        ringPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        ringPaint2.setColor(0xFF00D7FF);
        ringPaint2.setStyle(Paint.Style.FILL);

        slotPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        slotPaint.setColor(0xFFFFA500);
        slotPaint.setStyle(Paint.Style.FILL);
    }

    public void setScale(int w, int h) {
        this.width = w;
        this.height = h;
    }

    private void drawRing(Canvas canvas, int r, int slots, float rBase) {
        int w = width / 2;
        int h = height / 2;

        if (ringB) {
            canvas.drawCircle(w, h, r, ringPaint2);
        } else {
            canvas.drawCircle(w, h, r, ringPaint);
        }


        RectF oval = new RectF();
        oval.set(w - r, h - r, w + r, h + r);
        float random = (float) Math.random() * 360;
        float angle = 360f / slots;
        float sweep = 5 * rBase / r;
        for (int i = 0; i < slots; i++) {
            canvas.drawArc(oval, (angle * i) + random, sweep, true, slotPaint);
        }

        ringB = !ringB;
    }

    @Override
    public void onDraw(Canvas canvas) {
        int rBase = (Math.min(width, height) * 3) / 8;

        for (int i = 7; i >= 0; i--) {
            int r = rBase * (i + 1) / 8;
            drawRing(canvas, r, ringSlots[i], rBase);
        }
    }
}
