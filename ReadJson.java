package studio.hedgehog.retrofittest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ReadJson extends AppCompatActivity implements Runnable {

    private OkHttpClient client = new OkHttpClient();
    private TextView editText;
    private String updateView = "http://103.233.34.92:8010/api/get"; // URL адрес к Json файлу

    private String myRun(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text);
        editText = findViewById(R.id.editText);
        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            updateView = myRun(updateView);
        } catch (IOException e) {
            e.printStackTrace();
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                editText.setText(updateView);
            }
        });
    }
}
