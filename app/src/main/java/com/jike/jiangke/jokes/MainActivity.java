package com.jike.jiangke.jokes;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.jike.jiangke.jokes.fragment.ListFragment;
import com.jike.jiangke.jokes.model.JokePost;
import com.jike.jiangke.jokes.utils.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 主actiivity
 */
public class MainActivity extends AppCompatActivity {
    //listFragment显示的view其中包含下拉刷新控件保存在Activity中，是为了重建时恢复数据
    private View view;
    private List<JokePost> list;
    //保存listFragment点击的条目位置，方便在ContentFragment中获取
    private int currentPostion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initdata();
        initView();
        getFragmentManager().beginTransaction().add(R.id.content, new ListFragment()).commit();

    }

    //初始化View，显示Activity内容
    private void initView() {
        setContentView(R.layout.main_layout);
        view = View.inflate(this, R.layout.list_fragment, null);

    }

    //创建保存文章的集合
    private void initdata() {
        list = new ArrayList<>();
        String content = getContentFromCache();
        if (content != null) {
            Log.d("MainActivity", "读取缓存成功");
            Utils.parseJSONWithJSONObject(content, list);
        }
    }

    public int getCurrentPostion() {
        return currentPostion;
    }

    public void setCurrentPostion(int currentPostion) {
        this.currentPostion = currentPostion;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public List<JokePost> getList() {
        return list;
    }

    public void setList(List<JokePost> list) {
        this.list = list;
    }

    /**
     * 如果有缓存文件从缓存文件中获取数据
     *
     * @return
     */
    private String getContentFromCache() {

        String content = null;
        File file = new File(getCacheDir(), Utils.CACHE_NAME);
        if (file.exists()) {
            BufferedReader reader = null;
            String line = null;
            StringBuilder builder = new StringBuilder();
            try {
                reader = new BufferedReader(new FileReader(file));
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
                content = builder.toString();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return content;
    }

    /**
     * 重写onBackPressed()判断fragment返回栈中是否有内容
     */
    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount()>0){
            getFragmentManager().popBackStackImmediate();
        }else {
            super.onBackPressed();
        }
    }
}
