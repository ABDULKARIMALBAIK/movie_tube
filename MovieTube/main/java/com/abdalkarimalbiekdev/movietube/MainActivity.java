package com.abdalkarimalbiekdev.movietube;

import androidx.appcompat.app.AppCompatActivity;
import io.supercharge.shimmerlayout.ShimmerLayout;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.abdalkarimalbiekdev.movietube.Utils.Common;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    TextView txtTitle;
    believe.cht.fadeintextview.TextView txtslogan;
    ShimmerLayout shimmerLayout;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Note: add this code before setContentView method
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/teko.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());

        setContentView(R.layout.activity_main);

        //Change soft-key Color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setNavigationBarColor(getResources().getColor(R.color.colorPrimary));
        }

        initViews();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
                startActivity(new Intent(MainActivity.this , SignIn.class));
            }
        } , 4000);
    }

    @Override
    protected void onDestroy() {

        File dir = new File(Common.getPathImages());

        if (dir.isDirectory()) {

            String[] children = dir.list();

            if (children != null){

                for (int i = 0; i < children.length; i++)
                {
                    new File(dir, children[i]).delete();
                }
            }

        }

        super.onDestroy();
    }

    private void initViews() {
        txtTitle = findViewById(R.id.title);
        Typeface typeface = Typeface.createFromAsset(getAssets() , "fonts/giddyupstd.otf");
        txtTitle.setTypeface(typeface);

        txtslogan = findViewById(R.id.txtslogan);
        txtslogan.setText(getResources().getString(R.string.slogan));
        txtslogan.setLetterDuration(10);

        shimmerLayout = findViewById(R.id.shimmerLayout);
        shimmerLayout.startShimmerAnimation();
    }

    @Override
    protected void onResume() {
        super.onResume();

        txtslogan.setText(getResources().getString(R.string.slogan));
        txtslogan.setLetterDuration(10);

        shimmerLayout.startShimmerAnimation();
    }

    @Override
    protected void onPause() {
        super.onPause();

        shimmerLayout.stopShimmerAnimation();
    }
}
