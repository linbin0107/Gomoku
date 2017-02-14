package com.example.linbin.gomoku;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * Created by linbin on 2/9/2017.
 */

public class gomokuView extends View{
    private int numColumns, numRows;
    private int cellWidth, cellHeight;
    private int startX = 0, startY = 0;
    private Paint paint = null;
    int winFlag = 0;
    final private int STONE_BLACK = 1;
    final private int STONE_WHITE = 2;
    // 2D array, each element indicates a point on the board
    private int [][] chess;
    // track the color of last move
    private int stone_flag = 0;

    public TextView statusTV;
    CharSequence mText;
    CharSequence BLACK_WIN = "Black win!!!";
    CharSequence WHITE_WIN = "White win!!!";

    public gomokuView(Context context) {
        super(context);

    }

    public void init(int size) {
        paint = new Paint();
        paint.setColor(0xff000000);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(5);

        winFlag = 0;
        stone_flag = 0;

        setNumRows(size);
        setNumColumns(size);

        calculateDimensions();
    }

    private void setNumColumns(int numColumns) {
        this.numColumns = numColumns;

    }

    private void setNumRows(int numRows) {
        this.numRows = numRows;
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
                // start the game, the black always play first
                if (stone_flag == 0) {
                    chess[indexX][indexY] = STONE_BLACK;
                    stone_flag = STONE_BLACK;
                } else if (stone_flag == STONE_BLACK && chess[indexX][indexY] == 0) {
                    chess[indexX][indexY] = STONE_WHITE;

                    if (checkWin(STONE_BLACK)) {
                        this.init(15);
                        this.invalidate();
                        mText = BLACK_WIN;
                        showTV(mText);
                    }

                    stone_flag = STONE_WHITE;
                } else if (stone_flag == STONE_WHITE && chess[indexX][indexY] == 0) {
                    chess[indexX][indexY] = STONE_BLACK;

                    if (checkWin(STONE_WHITE)) {
                        this.init(15);
                        this.invalidate();
                        mText = WHITE_WIN;
                        showTV(mText);
                    }

                    stone_flag = STONE_BLACK;
                }
            }

            invalidate();
        }
        return true;
    }

    public boolean checkWin(int stone_flag) {
        for (int i = 0; i < numColumns - 1; ++i) {
            for (int j = 0; j < numRows - 1; ++j) {
                // check horizontal 5
                if (((i + 4) < (numColumns - 1)) && (chess[i][j] == stone_flag) &&
                        (chess[i + 1][j] == stone_flag) && (chess[i + 2][j] == stone_flag) &&
                        (chess[i + 3][j] == stone_flag) && (chess[i + 4][j] == stone_flag)) {
                    winFlag = stone_flag;
                }

                // check vertical 5
                if (((j + 4) < (numRows - 1)) && (chess[i][j] == stone_flag) &&
                        (chess[i][j + 1] == stone_flag) && (chess[i][j + 2] == stone_flag) &&
                        (chess[i][j + 3] == stone_flag) && (chess[i][j + 4] == stone_flag)) {
                    winFlag = stone_flag;
                }

                // check from upleft to downright 5
                if (((j + 4) < (numRows - 1)) && ((i + 4) < (numColumns - 1)) &&
                        (chess[i][j] == stone_flag) && (chess[i + 1][j + 1] == stone_flag) &&
                        (chess[i + 2][j + 2] == stone_flag) && (chess[i + 3][j + 3] == stone_flag) &&
                        (chess[i + 4][j + 4] == stone_flag)) {
                    winFlag = stone_flag;
                }

                // check from upright to downleft 5
                if (((j + 4) < (numRows - 1)) && ((i - 4) >= 0) &&
                        (chess[i][j] == stone_flag) && (chess[i - 1][j + 1] == stone_flag) &&
                        (chess[i - 2][j + 2] == stone_flag) && (chess[i - 3][j + 3] == stone_flag) &&
                        (chess[i - 4][j + 4] == stone_flag)) {
                    winFlag = stone_flag;
                }
            }
        }

        if (winFlag == stone_flag)
            return true;
        else
            return false;
    }

    public void setTV(TextView tv) {
        statusTV = tv;
        statusTV.setVisibility(View.VISIBLE);
    }

    public void showTV(CharSequence text) {
        this.statusTV.setText(text);
        statusTV.setVisibility(View.VISIBLE);
    }
}
