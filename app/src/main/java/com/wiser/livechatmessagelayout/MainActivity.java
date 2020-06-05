package com.wiser.livechatmessagelayout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.wiser.livechatmessagelayout.ui.LiveHomeActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_live).setOnClickListener(v -> {
            startActivity(new Intent(this, LiveHomeActivity.class));
        });
    }
}
