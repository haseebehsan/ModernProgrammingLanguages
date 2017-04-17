package me.haseebehsan.me.classgraphics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Haseeb on 3/30/2017.
 */

public class canvasView extends View {
    int radius = 5;
    int Xadder = 10;
    int Yadder = 1;
    int heigth ;
    int width, x, y;
    boolean start = false;

    public canvasView(Context context , AttributeSet attrs) {
        super(context, attrs);

    }




    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint p = new Paint();
        p.setARGB(255, 255, 0, 0);

         heigth = canvas.getHeight();
         width = canvas.getWidth();



        canvas.drawLine(0 , heigth/20 , width , heigth/20, p);
        canvas.drawLine(0 , (heigth/20)*19 , width , (heigth/20)*19, p);
        canvas.drawLine(0 , (heigth/2) , width , (heigth/2), p);



        canvas.drawCircle(x,y, radius, p );

        if(radius >= width/2 || radius < 5){
            Xadder *= -1;
        }

        radius += Xadder;

        invalidate();
    }


}
