package com.box.helloworld;
/**
 * Created by BOXinWORLD on 2018/4
 */
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import android.widget.TextView;
import cn.bmob.v3.Bmob;

public class MainActivity extends AppCompatActivity  implements Thread.UncaughtExceptionHandler {

    int first=0;
    Intent intent;
    Fragment1 f1;
    Fragment2 f2;
    Fragment3 f3;
    NavigationView navigationView ;
    Button btn1;
    DrawerLayout drawerlayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        setContentView(R.layout.activity_main);
        Thread.setDefaultUncaughtExceptionHandler(this);

        TextView textView = findViewById(R.id.headTV);
        //修改字体
        Typeface typeFace =Typeface.createFromAsset(getAssets(), "rafika.ttf");
        // 应用字体
        textView.setTypeface(typeFace);

        Bmob.initialize(MainActivity.this,"a729fc2e45bb26a41415a0ca649ae855");
        f1 = new Fragment1();
        f2 = new Fragment2();
        f3 = new Fragment3();
        getFragmentManager().beginTransaction().add(R.id.mainframe, f1)
                .add(R.id.mainframe, f2)
                .add(R.id.mainframe, f3)
                .hide(f2)
                .hide(f3)
                .commit();



        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        drawerlayout= (DrawerLayout) findViewById(R.id.drawer);
        btn1 =findViewById(R.id.menu_btn);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerlayout.openDrawer(Gravity.RIGHT);
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                //在这里处理item的点击事件
                switch (item.getItemId()){
                    case R.id.kp:
                        getFragmentManager().beginTransaction().show(f1).hide(f2).hide(f3).commit();
                        break;
                    case R.id.zx:
                        getFragmentManager().beginTransaction().show(f2).hide(f1).hide(f3).commit();
                        break;
                    case R.id.sc:
                        getFragmentManager().beginTransaction().show(f3).hide(f2).hide(f1).commit();
//                        getFragmentManager().beginTransaction().replace(R.id.mainframe, f3).commit();
                        break;
                };
                drawerlayout.closeDrawers();
                return true;
            }
        });


        intent = new Intent(MainActivity.this,ChooseAty.class);
        if(first==0){
            first=1;
            startActivityForResult(intent,1);
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String value = data.getStringExtra("choose");
        f1.mviewPager.setCurrentItem(Integer.parseInt(value),false);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
//        Log.e("====", "get");
    }


//    @Override
//    protected void onPause() {
//        Log.e("====", "M onPause()");
//        super.onPause();
//    }
//
//    @Override
//    protected void onStop() {
//        Log.e("====", "M onStop()");
//        super.onStop();
//    }
//
//    @Override
//    protected void onDestroy() {
//        Log.e("====", "M onDestroy()");
//        super.onDestroy();
//    }
//
//    @Override
//    protected void onResume() {
//        Log.e("====", "M onResume()");
//        super.onResume();
//    }
//
//    @Override
//    protected void onStart() {
//        Log.e("====", "M onStart()");
//        super.onStart();
//    }
//
//    @Override
//    protected void onRestart() {
//        Log.e("====", "M onRestart()");
//        super.onRestart();
//    }
}
