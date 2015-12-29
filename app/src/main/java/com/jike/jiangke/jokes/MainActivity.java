package com.jike.jiangke.jokes;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jike.jiangke.jokes.view.RefreshableView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    RefreshableView refreshableView;
    ListView listView;
    MyAdapter adapter;
    List<JokePost> jokePosts;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.d(MainActivity.TAG, jokePosts.size() + "");
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initdata();
        initView();
        initListener();
    }

    private void initdata() {
        jokePosts = new ArrayList<>();


    }

    /**
     * 初始化下拉刷新控件的回调监听
     */
    private void initListener() {
        refreshableView.setOnRefreshListener(new RefreshableView.PullToRefreshListener() {
            @Override
            public void onRefresh() {
                Utils.handleResponse(jokePosts, handler);
                refreshableView.finishRefreshing();
            }
        }, 0);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        setContentView(R.layout.activity_main);
        refreshableView = (RefreshableView) findViewById(R.id.refreshable_view);
        listView = (ListView) findViewById(R.id.list_view);
        adapter = new MyAdapter();
        listView.setAdapter(adapter);
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return jokePosts.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        /**
         * 填充子View
         *
         * @param position
         * @param convertView
         * @param parent
         * @return
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            ViewHoder viewHoder = null;
            if (convertView != null) {
                view = convertView;
                viewHoder = (ViewHoder) view.getTag();
            } else {
                view = View.inflate(MainActivity.this, R.layout.list_item, null);
                viewHoder = new ViewHoder();
                viewHoder.tv_title = (TextView) view.findViewById(R.id.tv_title);
                viewHoder.tv_updatetime = (TextView) view.findViewById(R.id.tv_updatetime);
                viewHoder.iv_topost = (ImageView) view.findViewById(R.id.iv_topost);
                view.setTag(viewHoder);
            }
            viewHoder.tv_title.setText(jokePosts.get(position).getPost_title());
            viewHoder.tv_updatetime.setText(jokePosts.get(position).getPost_date());
            viewHoder.iv_topost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "嗲了那牛", Toast.LENGTH_SHORT).show();
                }
            });

            return view;
        }

        class ViewHoder {
            public TextView tv_title;
            public TextView tv_updatetime;
            public ImageView iv_topost;
        }
    }

}
