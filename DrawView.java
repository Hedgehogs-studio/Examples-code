package studio.hedgehog.pixelpaint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;
import android.view.View;

public class DrawView extends View{

    //Величина сетки
    private int widthSquare = 25;

    private Paint p;
    private Point point = new Point();
    private Canvas mCanvas;
    private int x;
    private int y;
    private Rect rect;
    private int[][] position;
    private int mColor = 0;
    private boolean bTouch = false;

    public void setMColor(int color){
        this.mColor = color;
        invalidate();
    }

    public DrawView(Context context, Display display) {
        super(context);
        p = new Paint();
        display.getSize(point);
        position = new int[((point.x / 25) + 1) * ((point.y / 25) + 1)][5];
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mCanvas = canvas;
        drawGrid();
        drawSqare();
        colorSqare(x, y);
        bTouch = false;
    }

    //Прорисовка квадратов
    public void drawSqare(){
        for(int i = 0; i < position.length; i++){
            if(position[i][4] != 0) {
                p.setColor(position[i][4]);
                p.setStyle(Paint.Style.FILL);
                mCanvas.drawRect(position[i][0], position[i][1], position[i][2], position[i][3], p);
            }
        }
    }

    //Создание квадрата
    public void colorSqare(int x, int y){
        p.setColor(mColor);
        p.setStyle(Paint.Style.FILL);
        p.setStrokeWidth(1f);
        int a = ((x / widthSquare) * widthSquare);
        int b = ((y / widthSquare) * widthSquare);
        int temp = ((point.y / widthSquare) * ((x / widthSquare) + 1)) - ((point.y / widthSquare) - ((y / widthSquare) + 1));
        position[temp][0] = a;
        position[temp][1] = b;
        position[temp][2] = a + widthSquare;
        position[temp][3] = b + widthSquare;
        if (bTouch)position[temp][4] = mColor;
        if(mColor != 0 && bTouch) {
            rect = new Rect(a, b, a + widthSquare, b + widthSquare);
            mCanvas.drawRect(rect, p);
        }else invalidate();
    }

    //Сетка
    public void drawGrid() {
        p.setColor(Color.GRAY);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(4);
        if(mCanvas != null) {
            for (int i = 0; i < point.x; i += widthSquare) {
                mCanvas.drawLine(i, 0, i, point.y, p);
            }
            for (int i = 25; i < point.y; i += widthSquare) {
                mCanvas.drawLine(0, i, point.x, i, p);
            }
        }
    }

    //Координаты касания
    public void touch(int x, int y){
        this.x = x;
        this.y = y;
        bTouch = true;
        invalidate();
    }
}
