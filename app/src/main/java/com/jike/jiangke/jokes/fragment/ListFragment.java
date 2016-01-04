package com.jike.jiangke.jokes.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jike.jiangke.jokes.model.JokePost;
import com.jike.jiangke.jokes.MainActivity;
import com.jike.jiangke.jokes.R;
import com.jike.jiangke.jokes.utils.Utils;
import com.jike.jiangke.jokes.view.RefreshableView;

import java.util.List;

/**
 * 该Fragment展示所有文章的列表，点击列表条目，跳转到内容界面
 */
public class ListFragment extends Fragment {
    MainActivity activity;
    //fagment主界面
    View view;
    public static final String TAG = "MainActivity";
    //下拉刷新控件
    RefreshableView refreshableView;
    //展示文章的列表
    ListView listView;
    MyAdapter adapter;
    //所有文章的集合
    List<JokePost> jokePosts;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.d(TAG, jokePosts.size() + "");
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initdata();
        initView();
        initListener();
        return view;
    }

    private void initdata() {
        activity = (MainActivity) getActivity();
        jokePosts = activity.getList();


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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                activity.setCurrentPostion(position);
                getFragmentManager().beginTransaction()
                        .replace(R.id.content, new ContentFragment())
                        .addToBackStack(null).commit();
            }
        });
    }

    /**
     * 初始化控件
     */
    private void initView() {
        view = activity.getView();
        refreshableView = (RefreshableView) view.findViewById(R.id.refreshable_view);
        listView = (ListView) view.findViewById(R.id.list_view);
        adapter = new MyAdapter();
        listView.setAdapter(adapter);
    }

    /**
     * 配置列表显示内容的适配器，对listview进行优化
     */
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
                view = View.inflate(activity, R.layout.list_item, null);
                viewHoder = new ViewHoder();
                viewHoder.tv_title = (TextView) view.findViewById(R.id.tv_title);
                viewHoder.tv_updatetime = (TextView) view.findViewById(R.id.tv_updatetime);
                viewHoder.iv_topost = (ImageView) view.findViewById(R.id.iv_topost);
                view.setTag(viewHoder);
            }
            viewHoder.tv_title.setText(jokePosts.get(position).getPost_title());
            viewHoder.tv_updatetime.setText(jokePosts.get(position).getPost_date());


            return view;
        }

        class ViewHoder {
            public TextView tv_title;
            public TextView tv_updatetime;
            public ImageView iv_topost;
        }
    }
}
