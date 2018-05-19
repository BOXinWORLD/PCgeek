package com.box.helloworld;
/**
 * Created by BOXinWORLD on 2018/4
 */
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.like.LikeButton;
import com.like.OnLikeListener;

import java.sql.Timestamp;

public class NewsDisplayActvivity extends AppCompatActivity {

    private String newsUrl;
    News news;
    LikeButton lbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        setContentView(R.layout.activity_display_news);

        TextView textView = findViewById(R.id.head2);
        //修改字体
        Typeface typeFace =Typeface.createFromAsset(getAssets(), "rafika.ttf");
        // 应用字体
        textView.setTypeface(typeFace);

        //利用Serializable接口 intent传递对象
        news = (News) getIntent().getSerializableExtra("news");
        newsUrl = news.getNewsUrl();
        WebView webView = (WebView) findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(newsUrl);

        lbtn=findViewById(R.id.starbtn);
        final MySqliteHelper helper=DBmanager.getHelper(NewsDisplayActvivity.this);
        SQLiteDatabase db =helper.getWritableDatabase();
        Cursor c=db.query("News",null,"nurl=?",new String[]{newsUrl}
                               ,null,null,null);
        if(c.getCount()!=0)lbtn.setLiked(true);
        c.close();
        db.close();
        lbtn.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                SQLiteDatabase db =helper.getWritableDatabase();
                String t = new Timestamp(System.currentTimeMillis()).toString();
                ContentValues values = new ContentValues();
                values.put("nurl",newsUrl);
                values.put("ntitle",news.getNewsTitle());
                values.put("nfrom",news.getNewsFrom());
                values.put("ndesc", news.getDesc());
                values.put("ntime", t);
                long result = db.insert("News", null, values);
                if (result > 0) {
//                    Log.e("====", "插入完成");
                    Toast.makeText(NewsDisplayActvivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(NewsDisplayActvivity.this, "收藏失败", Toast.LENGTH_SHORT).show();
                }
                db.close();
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                SQLiteDatabase db =helper.getWritableDatabase();
                int result2=db.delete("News","nurl=?",new String[]{newsUrl});
                if(result2>0){
//                    Log.e("====", "删除完成");
                    Toast.makeText(NewsDisplayActvivity.this, "取消收藏", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(NewsDisplayActvivity.this, "取消失败", Toast.LENGTH_SHORT).show();
                }
                db.close();
            }
        });

    }


}
