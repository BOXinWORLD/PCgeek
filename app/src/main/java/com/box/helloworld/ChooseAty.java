package com.box.helloworld;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

/**
 * Created by BOXinWORLD on 2018/4/1.
 */

public class ChooseAty extends AppCompatActivity {
    Button bt1;
    Button bt2;
    Button bt3;
    Button bt4;
    Button bt5;
    Button bt6;
    Intent intent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        setContentView(R.layout.choose);

        intent = getIntent();

        bt1=findViewById(R.id.button1);
        bt2=findViewById(R.id.button2);
        bt3=findViewById(R.id.button3);
        bt4=findViewById(R.id.button4);
        bt5=findViewById(R.id.button5);
        bt6=findViewById(R.id.button6);


        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent传递参数
                intent.putExtra("choose", "0");
                setResult(1,intent);
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent传递参数
                intent.putExtra("choose", "1");
                setResult(1,intent);
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent传递参数
                intent.putExtra("choose", "2");
                setResult(1,intent);
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent传递参数
                intent.putExtra("choose", "3");
                setResult(1,intent);
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
        bt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent传递参数
                intent.putExtra("choose", "4");
                setResult(1,intent);
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
        bt6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent传递参数
                intent.putExtra("choose", "5");
                setResult(1,intent);
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

    }
//    @Override
//    protected void onPause() {
//        Log.e("====", "B onPause()");
//        super.onPause();
//    }
//
//    @Override
//    protected void onStop() {
//        Log.e("====", "B onStop()");
//        super.onStop();
//    }
//
//    @Override
//    protected void onDestroy() {
//        Log.e("====", "B onDestroy()");
//        super.onDestroy();
//    }
//
//    @Override
//    protected void onResume() {
//        Log.e("====", "B onResume()");
//        super.onResume();
//    }
//
//    @Override
//    protected void onStart() {
//        Log.e("====", "B onStart()");
//        super.onStart();
//    }
//
//    @Override
//    protected void onRestart() {
//        Log.e("====", "B onRestart()");
//        super.onRestart();
//    }
}
