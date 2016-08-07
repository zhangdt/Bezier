package com.abner.bezier;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by abner on 2016/8/6.
 */
public class BubbleView extends View {

    private Paint paint;

    private float startX=100 ;
    private float startY =100;

    private float moveX ;
    private float moveY ;

    private float anchorX;
    private float anchorY;

    private float radius = 50;
    private float radius2 = 50;

    private boolean isMove = false;

    private Path path;


    public BubbleView(Context context) {
        super(context);
        init();
    }

    public BubbleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BubbleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        path = new Path();
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
    }


    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        if (!isMove)
        canvas.drawCircle(startX, startY, radius2, paint);
        if (isMove)
        {


            Log.d("TAG","---------------------draw");
            anchorX =  (moveX+ startX)/2;
            anchorY =  (moveY + startY)/2;
            float offsetX = (float) (radius*Math.sin(Math.atan((moveY - startY) / (moveX - startX))));
            float offsetY = (float) (radius*Math.cos(Math.atan((moveY - startY) / (moveX - startX))));
            float distance = (float) Math.sqrt(Math.pow(moveY-startY, 2) + Math.pow(moveX-startX, 2));
            radius2 = -distance/15+50;
            float offsetX2 = (float) (radius2*Math.sin(Math.atan((moveY - startY) / (moveX - startX))));
            float offsetY2 = (float) (radius2*Math.cos(Math.atan((moveY - startY) / (moveX - startX))));



            float x1 = startX - offsetX2;
            float y1 = startY + offsetY2;

            float x2 = moveX - offsetX;
            float y2 = moveY + offsetY;

            float x3 = moveX + offsetX;
            float y3 = moveY - offsetY;

            float x4 = startX + offsetX2;
            float y4 = startY - offsetY2;

            Log.d("TAG", "anchorX :"+anchorX+"\n"+
                                "anchorY: "+anchorY +"\n"+
                                "x1:"+x1+"\n"+
                                "y1:"+y1+"\n"+
                    "x2:"+x2+"\n"+
                    "y2:"+y2+"\n"+
                    "x3:"+x3+"\n"+
                    "y3:"+y3+"\n"+
                    "x4:"+x4+"\n"+
                    "y4:"+y4+"\n"+""

            );
            if (!(radius2<=10)) {
                canvas.drawCircle(startX, startY, radius2, paint);
                path.reset();
                path.moveTo(x1, y1);
                path.quadTo(anchorX, anchorY, x2, y2);
                path.lineTo(x3, y3);
                path.quadTo(anchorX, anchorY, x4, y4);
//            path.lineTo(x1, y1);
                canvas.drawPath(path, paint);
            }
            canvas.drawCircle(moveX, moveY, radius, paint);
        }




    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        Log.d("TAG","Event:+++++++++++++++++++");
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            startX = event.getX();
            startY = event.getY();
            isMove = true;
            Log.d("TAG","downX:"+startX);
            Log.d("TAG","downY:"+startY);


        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            moveX = event.getX();
            moveY = event.getY();
            Log.d("TAG","MoveX:"+moveX);
            Log.d("TAG","MoveY:"+moveY);
            invalidate();
        } else if (event.getAction() == MotionEvent.ACTION_UP) {

            isMove = false;
            radius2 = 50;
            invalidate();
        }


        return true;
    }

}

