package com.example.stadiumcheckers;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.SurfaceView;

import java.util.HashMap;
import java.util.Random;

public class GameView extends SurfaceView {
    private int width;
    private int height;
    private final Paint ringPaint;
    private final Paint ringPaint2;
    private final Paint slotPaint;
    private final Paint[] colorPaints;
    private final Paint[] colorPaints2;
    private final Paint whitePaint;
    private final Paint blackPaint;
    private boolean ringB = false;
    private final int[] ringSlots = {4, 5, 4, 6, 5, 6, 7, 6};

    private final HashMap<Point, Integer> positions = new HashMap<>();

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

        Paint redPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        redPaint2.setColor(0xFFFF1010);
        redPaint2.setStyle(Paint.Style.FILL);
        redPaint2.setTextSize(40);

        Paint greenPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        greenPaint2.setColor(0xFF10FF10);
        greenPaint2.setStyle(Paint.Style.FILL);

        Paint yellowPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        yellowPaint2.setColor(0xFFF0F010);
        yellowPaint2.setStyle(Paint.Style.FILL);

        Paint bluePaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        bluePaint2.setColor(0xFF1010FF);
        bluePaint2.setStyle(Paint.Style.FILL);

        colorPaints2 = new Paint[]{redPaint2, yellowPaint2, greenPaint2, bluePaint2};

        whitePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        whitePaint.setColor(0xFFFFFFFF);
        whitePaint.setStyle(Paint.Style.FILL);

        blackPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        blackPaint.setColor(0xFF000000);
        blackPaint.setStyle(Paint.Style.FILL);

        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                Point p;
                do {
                    int ring = random.nextInt(8);

                    int boundMin = 0;
                    int boundMax;
                    if (ring == 0) {
                        boundMin = i * 5;
                        boundMax = (i + 1) * 5;
                    } else {
                        boundMax = ringSlots[ring - 1];
                    }

                    int slot = random.nextInt(boundMax - boundMin) + boundMin;
                    p = new Point(ring, slot);
                } while (positions.putIfAbsent(p, i) != null);
            }
        }
    }

    public void setScale(int w, int h) {
        this.width = w;
        this.height = h;
    }

    private void drawRing(Canvas canvas, int r, int id, float rBase) {
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
        float angle = 360f / ringSlots[id];

        float sweep = 5 * rBase / r;
        for (int i = 0; i < ringSlots[id]; i++) {
            float mA = (angle * i) + random;
            canvas.drawArc(oval, mA, sweep, true, slotPaint);

            Integer point = positions.get(new Point(id, i));
            if (point == null) {
                continue;
            }

            mA = (float) ((mA + sweep / 2) * (Math.PI / 180));
            float mR = rBase * (id + 0.5f) / 8;
            float x = w + (float) (mR * Math.cos(mA));
            float y = h + (float) (mR * Math.sin(mA));
            drawMarble(x, y, point, rBase, canvas);
        }

        ringB = !ringB;
    }

    private void drawOuterRing(Canvas canvas, float rBase) {
        int w = width / 2;
        int h = height / 2;
        int j = 0;
        double angleBase = Math.PI / 10;
        for (int c = 0; c < 4; c++) {
            for (int i = 0; i < 5; i++) {
                double angle = angleBase * j + angleBase / 2;
                float x = w + (float) (rBase * Math.sin(angle) * 1.02);
                float y = h + (float) (rBase * Math.cos(angle) * 1.02);
                canvas.drawCircle(x, y, rBase / 15, colorPaints[c]);
                j++;
            }
        }
    }

    private void drawOuterRing2(Canvas canvas, float rBase) {
        int w = width / 2;
        int h = height / 2;
        int j = 0;
        double angleBase = Math.PI / 10;
        for (int c = 0; c < 4; c++) {
            for (int i = 0; i < 5; i++) {
                Integer point = positions.get(new Point(0, j));
                if (point == null) {
                    j++;
                    continue;
                }

                double angle = angleBase * j + angleBase / 2;
                float x = w + (float) (rBase * Math.sin(angle) * 1.02);
                float y = h + (float) (rBase * Math.cos(angle) * 1.02);
                drawMarble(x, y, c, rBase, canvas);
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

    private void drawMarble(float x, float y, int team, float rBase, Canvas canvas) {
        if (team == 0) {
            canvas.drawCircle(x, y, rBase / 20f, whitePaint);
        } else {
            canvas.drawCircle(x, y, rBase / 20f, blackPaint);
        }
        canvas.drawCircle(x, y, rBase / 24f, colorPaints2[team]);
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawRect(0, 0, 250, 50, whitePaint);
        canvas.drawText("Turn 36: RED", 10, 38, colorPaints2[0]);

        int rBase = (Math.min(width, height) * 3) / 8;
        ringB = false;

        drawOuterRing(canvas, rBase);
        for (int i = 7; i >= 1; i--) {
            int r = rBase * (i + 1) / 8;
            drawRing(canvas, r, i, rBase);
        }

        drawOuterRing2(canvas, rBase);

        drawInnerRing(canvas, rBase / 8f, rBase);
    }
}
