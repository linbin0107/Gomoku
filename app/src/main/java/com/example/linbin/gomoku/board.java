package com.example.linbin.gomoku;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class board extends AppCompatActivity {
    //declare a int for board size
    final static int maxN = 15;
    private Context context;
    //declare for imageView (Cells) array
    private ImageView[][] ivCell = new ImageView[maxN][maxN];
    //0 is empty, 1 is player, 2 is bot, 3 is background
    private Drawable[] drawCell = new Drawable[4];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        context = this;
        Intent intent = getIntent();

        gomokuView gomoku = new gomokuView(this);
        gomoku.setNumColumns(15);
        gomoku.setNumRows(15);

        setContentView(gomoku);
    }

}
