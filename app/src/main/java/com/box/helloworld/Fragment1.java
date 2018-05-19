package com.box.helloworld;
/**
 * Created by BOXinWORLD on 2018/4
 */
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
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

public class Fragment1 extends Fragment{

    int loadIndex[]={1,1,1,1,1,1};

    List<News> newsList1 = new ArrayList<>();
    List<News> newsList2 = new ArrayList<>();
    List<News> newsList3 = new ArrayList<>();
    List<News> newsList4 = new ArrayList<>();
    List<News> newsList5 = new ArrayList<>();
    List<News> newsList6 = new ArrayList<>();
    final List<View> viewList = new ArrayList<View>();

    NewsAdapter newsAdapter1;
    NewsAdapter newsAdapter2;
    NewsAdapter newsAdapter3;
    NewsAdapter newsAdapter4;
    NewsAdapter newsAdapter5;
    NewsAdapter newsAdapter6;

    VpSwipeRefreshLayout swipeRefreshLayout1;
    VpSwipeRefreshLayout swipeRefreshLayout2;
    VpSwipeRefreshLayout swipeRefreshLayout3;
    VpSwipeRefreshLayout swipeRefreshLayout4;
    VpSwipeRefreshLayout swipeRefreshLayout5;
    VpSwipeRefreshLayout swipeRefreshLayout6;

    ListView listView1;
    ListView listView2;
    ListView listView3;
    ListView listView4;
    ListView listView5;
    ListView listView6;


    TabLayout mTabLayout;
    ViewPager mviewPager;
    PagerAdapter mAdapter;
    BmobQuery<News> query;
    String[] mTitle =new String[] { "主板", "cpu", "显卡", "内存", "硬盘" ,"其他"};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1,container,false);

        mTabLayout = (TabLayout) view.findViewById(R.id.tl);
        mviewPager = (ViewPager) view.findViewById(R.id.viewpager);



        View view1 = inflater.inflate(R.layout.page,null);
        View view2 = inflater.inflate(R.layout.page,null);
        View view3 = inflater.inflate(R.layout.page,null);
        View view4 = inflater.inflate(R.layout.page,null);
        View view5 = inflater.inflate(R.layout.page,null);
        View view6 = inflater.inflate(R.layout.page,null);
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        viewList.add(view4);
        viewList.add(view5);
        viewList.add(view6);



//        new Thread(new Runnable() {
//            @Override
//            public void run() {
                initNews(query,newsList1,"1");
                initNews(query,newsList2,"2");
                initNews(query,newsList3,"3");
                initNews(query,newsList4,"4");
                initNews(query,newsList5,"5");
                initNews(query,newsList6,"6");
//            }
//        }).start();



        mAdapter = new PagerAdapter() {
            @Override
            public int getItemPosition(Object object) {
                return POSITION_NONE;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTitle[position];
            }
            @Override
            public int getCount() {
                return mTitle.length;
            }
            @Override
            public Object instantiateItem(View container, int position) {

                mviewPager.addView(viewList.get(position));
                return viewList.get(position);

            }
            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                ((ViewPager) container).removeView((View) object);
            }
            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        };

        mviewPager.setAdapter(mAdapter);

        listView1 = (ListView) view1.findViewById(R.id.news_lv);
        listView2 = (ListView) view2.findViewById(R.id.news_lv);
        listView3 = (ListView) view3.findViewById(R.id.news_lv);
        listView4 = (ListView) view4.findViewById(R.id.news_lv);
        listView5 = (ListView) view5.findViewById(R.id.news_lv);
        listView6 = (ListView) view6.findViewById(R.id.news_lv);

        newsAdapter1 = new NewsAdapter(getActivity(),newsList1);
        newsAdapter2 = new NewsAdapter(getActivity(),newsList2);
        newsAdapter3 = new NewsAdapter(getActivity(),newsList3);
        newsAdapter4 = new NewsAdapter(getActivity(),newsList4);
        newsAdapter5 = new NewsAdapter(getActivity(),newsList5);
        newsAdapter6 = new NewsAdapter(getActivity(),newsList6);

        swipeRefreshLayout1=view1.findViewById(R.id.srl);
        swipeRefreshLayout2=view2.findViewById(R.id.srl);
        swipeRefreshLayout3=view3.findViewById(R.id.srl);
        swipeRefreshLayout4=view4.findViewById(R.id.srl);
        swipeRefreshLayout5=view5.findViewById(R.id.srl);
        swipeRefreshLayout6=view6.findViewById(R.id.srl);

        listView1.setAdapter(newsAdapter1);
        setListener(listView1,newsList1,newsAdapter1,swipeRefreshLayout1,"1");
        listView2.setAdapter(newsAdapter2);
        setListener(listView2,newsList2,newsAdapter2,swipeRefreshLayout2,"2");
        listView3.setAdapter(newsAdapter3);
        setListener(listView3,newsList3,newsAdapter3,swipeRefreshLayout3,"3");
        listView4.setAdapter(newsAdapter4);
        setListener(listView4,newsList4,newsAdapter4,swipeRefreshLayout4,"4");
        listView5.setAdapter(newsAdapter5);
        setListener(listView5,newsList5,newsAdapter5,swipeRefreshLayout5,"5");
        listView6.setAdapter(newsAdapter6);
        setListener(listView6,newsList6,newsAdapter6,swipeRefreshLayout6,"6");





        initsrl(swipeRefreshLayout1,newsList1,newsAdapter1,"1");
        initsrl(swipeRefreshLayout2,newsList2,newsAdapter2,"2");
        initsrl(swipeRefreshLayout3,newsList3,newsAdapter3,"3");
        initsrl(swipeRefreshLayout4,newsList4,newsAdapter4,"4");
        initsrl(swipeRefreshLayout5,newsList5,newsAdapter5,"5");
        initsrl(swipeRefreshLayout6,newsList6,newsAdapter6,"6");




        mTabLayout.setupWithViewPager(mviewPager);

        return view;
    }

    void initNews(BmobQuery<News> query, final List<News> List, final String type){
        query=new BmobQuery<>();
        query.addWhereEqualTo("newsType",type);
        query.order("-createdAt");
        query.setLimit(6);
        query.findObjects(new FindListener<News>() {
            @Override
            public void done(List<News> list, BmobException e) {
                if(e==null){
                    List.clear();
                    List.addAll(list);
                    int i=Integer.parseInt(type);
                    loadIndex[i-1]=1;
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
                        mAdapter.notifyDataSetChanged();
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
        final int i=Integer.parseInt(type);


        query=new BmobQuery<>();
        query.addWhereEqualTo("newsType",type);
        query.order("-createdAt");
        query.setLimit(6);
        query.setSkip(loadIndex[i-1]*6);
        query.findObjects(new FindListener<News>() {
            @Override
            public void done(List<News> list, BmobException e) {
                if(e==null){
                    List.addAll(list);
                    loadIndex[i-1]++;
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
                    mAdapter.notifyDataSetChanged();
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
