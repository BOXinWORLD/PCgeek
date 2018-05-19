package com.box.helloworld;
/**
 * Created by BOXinWORLD on 2018/4
 */
import android.app.Fragment;
import android.content.Intent;
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
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class Fragment2 extends Fragment{
    ListView listView1;
    NewsAdapter newsAdapter1;
    VpSwipeRefreshLayout swipeRefreshLayout1;
    List<News> newsList1 = new ArrayList<>();
    BmobQuery<News> query;
    int loadIndex=1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2, container,false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                initNews(query,newsList1,"2");
                newsAdapter1.notifyDataSetChanged();
            }
        }).start();
        listView1 = (ListView) view.findViewById(R.id.news_lv2);
        newsAdapter1 = new NewsAdapter(getActivity(),newsList1);
        swipeRefreshLayout1=view.findViewById(R.id.srl2);
        listView1.setAdapter(newsAdapter1);
        setListener(listView1,newsList1,newsAdapter1,swipeRefreshLayout1,"2");
        initsrl(swipeRefreshLayout1,newsList1,newsAdapter1,"2");
        return view;
    }

    void initNews(BmobQuery<News> query, final List<News> List, String type){
        query=new BmobQuery<>();
        query.addWhereEqualTo("newsStyle",type);
        query.order("-createdAt");
        query.setLimit(6);
        query.findObjects(new FindListener<News>() {
            @Override
            public void done(List<News> list, BmobException e) {
                if(e==null){
                    List.clear();
                    List.addAll(list);
                    loadIndex=1;
//                    Toast.makeText(MainActivity.this,list.get(0).getDesc(),Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getActivity(),"刷新失败，请检查网络设置",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    void initsrl(final VpSwipeRefreshLayout swipeRefreshLayout, List<News> list, NewsAdapter newsAdapter22, final String type){
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

                initNews(query,List,type);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        newsAdapter.notifyDataSetChanged();
                        Toast.makeText(getActivity(), "刷新完成", Toast.LENGTH_SHORT).show();
                        // 加载完数据设置为不刷新状态，将下拉进度收起来
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 1500);



                // 这个不能写在外边，不然会直接收起来
                //swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    void loadmore(BmobQuery<News> query, final List<News> List,String type){
        query=new BmobQuery<>();
        query.addWhereEqualTo("newsStyle",type);
        query.order("-createdAt");
        query.setLimit(6);
        query.setSkip(loadIndex*6);
        query.findObjects(new FindListener<News>() {
            @Override
            public void done(List<News> list, BmobException e) {
                if(e==null){
                    List.addAll(list);
                    loadIndex++;
//                    Toast.makeText(MainActivity.this,list.get(0).getDesc(),Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getActivity(),"加载失败，请检查网络设置",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    void setListener(ListView listview, final List<News> newsList, final NewsAdapter newsAdapter,final VpSwipeRefreshLayout swipeRefreshLayout,final String type){
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News news = newsList.get(position);
                Intent intent = new Intent(getActivity(),NewsDisplayActvivity.class);
                intent.putExtra("news",news);
                startActivity(intent);
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
                    loadmore(query,newsList,type);
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
}
