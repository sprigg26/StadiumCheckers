package com.example.stadiumcheckers;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.SurfaceView;

public class GameView extends SurfaceView {
    private int width;
    private int height;
    private final Paint ringPaint;
    private final Paint ringPaint2;
    private final Paint slotPaint;
    private final Paint[] colorPaints;
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

        Paint redPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        redPaint.setColor(0xFFEF4020);
        redPaint.setStyle(Paint.Style.FILL);

        Paint greenPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        greenPaint.setColor(0xFF20EF40);
        greenPaint.setStyle(Paint.Style.FILL);

        Paint yellowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        yellowPaint.setColor(0xFFF0EF40);
        yellowPaint.setStyle(Paint.Style.FILL);

        Paint bluePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bluePaint.setColor(0xFF4020EF);
        bluePaint.setStyle(Paint.Style.FILL);

        colorPaints = new Paint[]{redPaint, yellowPaint, greenPaint, bluePaint};
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

    private void drawOuterRing(Canvas canvas, float rBase) {
        int w = width / 2;
        int h = height / 2;
        int j = 0;
        double angleBase = Math.PI / 10;
        for (Paint color : colorPaints) {
            for (int i = 0; i < 5; i++) {
                double angle = angleBase * j + angleBase / 2;
                canvas.drawCircle(w + (float) (rBase * Math.sin(angle) * 1.02),
                        h + (float) (rBase * Math.cos(angle) * 1.02), rBase / 15, color);
                j++;
            }
        }
    }

    private void drawInnerRing(Canvas canvas, float r, float rBase) {
        int w = width / 2;
        int h = height / 2;
        double angleBase = Math.PI / 2;

        canvas.drawCircle(w, h, r, ringPaint2);

        Path circlePath = new Path();
        circlePath.addCircle(w, h, r, Path.Direction.CW);
        canvas.clipPath(circlePath);

        for (int i = 0; i < 4; i++) {
            double angle = angleBase * i + angleBase / 2;
            canvas.drawCircle(w + (float) (r * Math.sin(angle) * 0.8),
                    h + (float) (r * Math.cos(angle) * 0.8), rBase / 15, colorPaints[i]);
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        int rBase = (Math.min(width, height) * 3) / 8;
        ringB = false;

        drawOuterRing(canvas, rBase);
        for (int i = 7; i >= 1; i--) {
            int r = rBase * (i + 1) / 8;
            drawRing(canvas, r, ringSlots[i], rBase);
        }
        drawInnerRing(canvas, rBase/8f, rBase);
    }
}
