package studio.hedgehog.pixelpaint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

public class CustomButton extends android.support.v7.widget.AppCompatButton {

    private Paint paint;
    private Paint borderPaint;
    private int mWidth;
    private int mHeight;
    private int mColor = Color.WHITE;
    private boolean borderButton = false;

    public void setBorderButton(boolean borderButton){
        this.borderButton = borderButton;
        invalidate();
    }

    public void setColor(int color){
        this.mColor = color;
        invalidate();
    }

    public int getColor(){
        return this.mColor;
    }

    public CustomButton(Context context) {
        this(context, null);
    }

    public CustomButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mHeight = h;
        mWidth = w;

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);

        borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setColor(Color.WHITE);
        borderPaint.setStrokeWidth(2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setColor(mColor);
        int temp;
        if(mHeight > mWidth){
            temp = mWidth;
        }else temp = mHeight;
        canvas.drawCircle(mWidth * 0.5f, mHeight * 0.5f, (temp * 0.5f), paint);
        if(borderButton) canvas.drawCircle(mWidth * 0.5f, mHeight * 0.5f, (temp * 0.49f), borderPaint);
    }
}
