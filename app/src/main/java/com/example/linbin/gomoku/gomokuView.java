package com.example.linbin.gomoku;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by linbin on 2/9/2017.
 */

public class gomokuView extends View{
    private int numColumns, numRows;
    private int cellWidth, cellHeight;
    private int startX = 0, startY = 0;
    private Paint paint = null;
    private boolean[][] cellChecked;
    final private int STONE_BLACK = 1;
    final private int STONE_WHITE = 2;
    // 2D array, each element indicates a point on the board
    private int [][] chess;
    // track the color of last move
    private int stone_flag = 0;

    public gomokuView(Context context) {
        super(context);

        paint = new Paint();
        paint.setColor(0xff000000);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(5);
    }

//    public gomokuView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        //blackPaint.setStyle(Paint.Style.FILL_AND_STROKE);
//    }

    public void setNumColumns(int numColumns) {
        this.numColumns = numColumns;
        calculateDimensions();
    }

    public int getNumColumns() {
        return numColumns;
    }

    public void setNumRows(int numRows) {
        this.numRows = numRows;
        calculateDimensions();
    }

    public int getNumRows() {
        return numRows;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        calculateDimensions();
    }

    private void calculateDimensions() {
        if (numColumns < 1 || numRows < 1) {
            return;
        }

        cellWidth = getWidth() / numColumns;
        cellHeight = getHeight() / numRows;

        cellChecked = new boolean[numColumns][numRows];

        chess = new int [numColumns][numRows];

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // set the background of the board to yellow
        canvas.drawColor(Color.YELLOW);

        if (numColumns == 0 || numRows == 0) {
            return;
        }

        int width = getWidth();
        int height = getHeight();

        for (int i = 0; i < numColumns; i++) {
            for (int j = 0; j < numRows; j++) {
                if (chess[i][j] == STONE_BLACK) {
                    paint.setColor(0xff000000);
                    canvas.drawCircle(i * cellWidth, j * cellHeight, cellWidth/3, paint);
                }

                if (chess[i][j] == STONE_WHITE) {
                    paint.setColor(0xffffffff);
                    canvas.drawCircle(i * cellWidth, j * cellHeight, cellWidth/3, paint);
                }
            }
        }

        for (int i = 1; i < numColumns; i++) {
            paint.setColor(0xff000000);
            canvas.drawLine(i * cellWidth, 0, i * cellWidth, height, paint);
        }

        for (int i = 1; i < numRows; i++) {
            paint.setColor(0xff000000);
            canvas.drawLine(0, i * cellHeight, width, i * cellHeight, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        int indexX = (int)(event.getX() / cellWidth);
//        int indexY = (int)(event.getY() / cellHeight);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int indexX = Math.round((event.getX() - startX) / cellWidth);
            int indexY = Math.round((event.getY() - startY) / cellHeight);

            if (indexX <= startX || indexX >= startX + (numColumns) * cellWidth ||
                    indexY <= startY || indexY >= startY + (numRows) * cellHeight)
            {
                // out of the edge of the board, do nothing;
                //invalidate();
                //System.out.println("out of bound");
            } else {
                if (stone_flag == 0) {
                    chess[indexX][indexY] = STONE_BLACK;
                    stone_flag = STONE_BLACK;
                } else if (stone_flag == STONE_BLACK && chess[indexX][indexY] == 0) {
                    chess[indexX][indexY] = STONE_WHITE;
                    stone_flag = STONE_WHITE;
                } else if (stone_flag == STONE_WHITE && chess[indexX][indexY] == 0) {
                    chess[indexX][indexY] = STONE_BLACK;
                    stone_flag = STONE_BLACK;
                }
            }

            invalidate();
        }
        return true;
    }
}
