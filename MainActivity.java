package studio.hedgehog.pixelpaint;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity{

    private ZoomLayout zoomLayout;
    private ZoomLayout zL;
    private DrawView drawView;
    private Button button_0;
    private CustomButton button_1;
    private CustomButton button_2;
    private CustomButton button_3;
    private CustomButton button_4;
    private CustomButton button_5;
    private CustomButton tempButton;
    private ColorPalette colorPalette;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Display display = getWindowManager().getDefaultDisplay();
        zoomLayout = new ZoomLayout(MainActivity.this);
        drawView = new DrawView(this, display);
        findView();
        viewListeners();
        zL.addView(zoomLayout);
        zL.addView(drawView);

        colorPalette.subscribe((color, fromUser, shouldPropagate) -> {
            if(tempButton != null){
                tempButton.setColor(color);
            }
            drawView.setMColor(color);
        });
        colorPalette.setInitialColor(tempButton.getColor());
    }

    public void clickButton(CustomButton customButton){
        tempButton.setBorderButton(false);
        tempButton = customButton;
        drawView.setMColor(tempButton.getColor());
        tempButton.setBorderButton(true);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int i = v.getId();
            if (i == R.id.button_0) {
                tempButton.setBorderButton(false);
                drawView.setMColor(0);
            } else if (i == R.id.button_1) {
                clickButton(button_1);
            } else if (i == R.id.button_2) {
                clickButton(button_2);
            } else if (i == R.id.button_3) {
                clickButton(button_3);
            } else if (i == R.id.button_4) {
                clickButton(button_4);
            } else if (i == R.id.button_5) {
                clickButton(button_5);
            }
        }
    };

    View.OnTouchListener onTouchListener = new View.OnTouchListener(){
        int i = 0;
        boolean bool = true;
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(i < event.getPointerCount()) i = event.getPointerCount();
            if((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_MOVE){
                bool = false;
            }
            if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_DOWN && i == 1 && bool) {
                drawView.touch((int) event.getX(), (int) event.getY());
            }
            if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_DOWN) {
                bool = true;
                i = 0;
            }
            return true;
        }
    };

    public void findView(){
        zL = findViewById(R.id.zoom);
        button_0 = findViewById(R.id.button_0);
        button_1 = findViewById(R.id.button_1);
        button_1.setColor(Color.GRAY);
        button_2 = findViewById(R.id.button_2);
        button_2.setColor(Color.BLACK);
        button_3 = findViewById(R.id.button_3);
        button_3.setColor(Color.BLUE);
        button_4 = findViewById(R.id.button_4);
        button_4.setColor(Color.GREEN);
        button_5 = findViewById(R.id.button_5);
        button_5.setColor(Color.RED);
        tempButton = button_1;
        tempButton.setBorderButton(true);
        colorPalette = findViewById(R.id.color_palett);
    }

    @SuppressLint("ClickableViewAccessibility")
    public void viewListeners(){
        drawView.setOnTouchListener(onTouchListener);
        button_0.setOnClickListener(onClickListener);
        button_1.setOnClickListener(onClickListener);
        button_2.setOnClickListener(onClickListener);
        button_3.setOnClickListener(onClickListener);
        button_4.setOnClickListener(onClickListener);
        button_5.setOnClickListener(onClickListener);
    }
}
