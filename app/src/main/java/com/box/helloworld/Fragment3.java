package com.box.helloworld;
/**
 * Created by BOXinWORLD on 2018/4
 */
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import cn.bmob.v3.BmobQuery;


public class Fragment3 extends Fragment{
    ListView listView1;
    NewsAdapter newsAdapter1;
    VpSwipeRefreshLayout swipeRefreshLayout1;
    List<News> newsList1 = new ArrayList<>();
    List<News> newsList0 = new ArrayList<>();
    BmobQuery<News> query;
    int loadIndex=1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment3, container,false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                initNews(newsList1);
                newsAdapter1.notifyDataSetChanged();
            }
        }).start();
        listView1 = (ListView) view.findViewById(R.id.news_lv3);
        newsAdapter1 = new NewsAdapter(getActivity(),newsList1);
        swipeRefreshLayout1=view.findViewById(R.id.srl3);
        listView1.setAdapter(newsAdapter1);
        setListener(listView1,newsList1,newsAdapter1,swipeRefreshLayout1,"2");
        initsrl(swipeRefreshLayout1,newsList1,newsAdapter1);
//        newsAdapter1.notifyDataSetChanged();
        return view;
    }

    void initNews(List<News> List){
        loadIndex=1;
        MySqliteHelper helper=DBmanager.getHelper(getActivity());
        SQLiteDatabase db =helper.getWritableDatabase();
        Cursor c=db.rawQuery("select * from News order by ntime DESC", null);
        int count=c.getCount();
        if(count!=0){
            newsList0.clear();
            List.clear();
            while(c.moveToNext()) {
                News news = new News(c.getString(c.getColumnIndex("ntitle")),
                        c.getString(c.getColumnIndex("nurl")),
                        c.getString(c.getColumnIndex("ndesc")),
                        c.getString(c.getColumnIndex("nfrom")));
                newsList0.add(news);
            }
            if(count>=6){
                for(int i=0;i<6;i++){
                    List.add(newsList0.get(i));
                }
            }else{
                List.addAll(newsList0);
            }
        }else{
            Toast.makeText(getActivity(),"还没有收藏",Toast.LENGTH_LONG).show();
        }
        c.close();
        db.close();
    }

    void initsrl(final VpSwipeRefreshLayout swipeRefreshLayout, List<News> list, NewsAdapter newsAdapter22){
        final NewsAdapter newsAdapter=newsAdapter22;
        final List<News> List=list;
// 设置下拉进度的背景颜色，默认就是白色的
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
// 设置下拉进度的主题颜色
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary);

// 下拉时触发SwipeRefreshLayout的下拉动画，动画完毕之后就会回调这个方法
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 开始刷新，设置当前为刷新状态，经测试这里可以省略

                initNews(List);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        newsAdapter.notifyDataSetChanged();
                        Toast.makeText(getActivity(), "刷新完成", Toast.LENGTH_SHORT).show();
                        // 加载完数据设置为不刷新状态，将下拉进度收起来
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 1500);

            }
        });

    }

    void loadmore(final List<News> List){
        int count=newsList0.size();
        if(count>loadIndex*6){
            if(count>=(loadIndex+1)*6){
                for(int i=0;i<6;i++){
                List.add(newsList0.get(i+loadIndex*6));
                }
            }else{
               List.clear();
               List.addAll(newsList0);
            }
            loadIndex++;
        }else{
            Toast.makeText(getActivity(),"没有更多了",Toast.LENGTH_LONG).show();
        }


    }

    void setListener(ListView listview, final List<News> newsList, final NewsAdapter newsAdapter,final VpSwipeRefreshLayout swipeRefreshLayout,final String type){
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News news = newsList.get(position);
                Intent intent = new Intent(getActivity(),NewsDisplayActvivity.class);
                intent.putExtra("news",news);
                startActivityForResult(intent,1);
            }
        });
        final View footerView = getActivity().getLayoutInflater().inflate(R.layout.refresh_footview_layout, null);
        listview.addFooterView(footerView);
        listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int visibleLastIndex;//用来可显示的最后一条数据的索引

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (newsAdapter.getCount() == visibleLastIndex && scrollState == SCROLL_STATE_IDLE) {
                    /*在此处加载更多数据*/
                    loadmore(newsList);
                    newsAdapter.notifyDataSetChanged();

                    Toast.makeText(getActivity(), "加载完成", Toast.LENGTH_SHORT).show();
                    footerView.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);
                }else {
                    footerView.setVisibility(View.VISIBLE);
//            Toast.makeText(this, "加载更多...", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                visibleLastIndex = firstVisibleItem + visibleItemCount - 1;//减去最后一个加载中那条
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
                initNews(newsList1);
                newsAdapter1.notifyDataSetChanged();
    }
//    @Override
//    public void onResume() {
//        super.onResume();
//        swipeRefreshLayout1.post(new Runnable() {
//            @Override
//            public void run() {
//                swipeRefreshLayout1.setRefreshing(true);
//            }
//        });
//        initNews(newsList1);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                newsAdapter1.notifyDataSetChanged();
//                Toast.makeText(getActivity(), "刷新完成", Toast.LENGTH_SHORT).show();
//                // 加载完数据设置为不刷新状态，将下拉进度收起来
//                swipeRefreshLayout1.setRefreshing(false);
//            }
//        }, 1500);
//    }
}
