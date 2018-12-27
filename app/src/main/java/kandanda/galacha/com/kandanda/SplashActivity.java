package kandanda.galacha.com.kandanda;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                redirectToMain();
            }
        }, 2000);
    }

    private void redirectToMain(){
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
