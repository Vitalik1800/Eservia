package com.eservia.booking.ui.about_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.eservia.booking.BuildConfig;
import com.eservia.booking.R;
import com.eservia.booking.common.view.BaseActivity;
import com.eservia.booking.util.WindowUtils;
import com.eservia.butterknife.ButterKnife;

public class AboutAppActivity extends BaseActivity {
    Toolbar toolbar;
    TextView tvVersion;

    public static void start(Context context) {
        Intent starter = new Intent(context, AboutAppActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);
        WindowUtils.setLightStatusBar(this);
        setUnbinder(ButterKnife.bind(this));
        toolbar = findViewById(R.id.toolbar);
        tvVersion = findViewById(R.id.tvVersion);
        initViews();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initViews() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setElevation(0);

        String ver = getResources().getString(R.string.version) + " " + BuildConfig.VERSION_NAME;
        tvVersion.setText(ver);
    }
}
