package com.jike.jiangke.jokes.utils;

import android.os.Handler;
import android.util.Log;

import com.jike.jiangke.jokes.MyApplication;
import com.jike.jiangke.jokes.model.JokePost;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


/**
 * 提供获取网络数据，进行缓存的工具类
 */
public class Utils {
    public static final String CACHE_NAME="cache.txt";

    /**
     * 解析服务器返回的数据
     *
     * @param list 解析出来的数据存入集合
     */
    public static void handleResponse(final List<JokePost> list, final Handler handler) {
        HttpURLConnection connection =null;

        try {


            URL url=new URL("http://jikejiangke123.sinaapp.com/latestposts.php");
            connection= (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);
            InputStream in=connection.getInputStream();
            BufferedReader reader=new BufferedReader(new InputStreamReader(in));
            String line=null;
            StringBuilder builder=new StringBuilder();
            while ((line= reader.readLine())!=null){
                builder.append(line);
            }
            parseJSONWithJSONObject(builder.toString(),list);
            writeContentToCache(builder.toString());
            handler.sendEmptyMessage(0);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 把从服务器或的的数据写入缓存
     * @param content
     */

    private static void writeContentToCache(String content) {

        File file = new File(MyApplication.getContext().getCacheDir(), Utils.CACHE_NAME);
        if(file.exists()){
            file.delete();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        BufferedWriter writer=null;
        try {
            writer=new BufferedWriter(new FileWriter(file));
            writer.write(content);
            writer.flush();
            Log.d("MainActivity","缓存成功");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(writer!=null){
                try {
                    writer.close();
                } catch (IOException e) {


                }
            }
        }
    }

    /**
     * 解析json数据封装成对象存public入集合
     * @param res
     * @param list
     */

    public static void parseJSONWithJSONObject(String res, List<JokePost> list) {
        if(list!=null){
            list.clear();
        }
        try {


            JSONArray array = new JSONArray(res);
            for (int i = 0; i < array.length(); i++) {
                JSONObject post = array.getJSONObject(i);
                String post_date = post.getString("post_date");
                String post_content = post.getString("post_content");
                String post_title = post.getString("post_title");
                JokePost jokePost = new JokePost(post_date, post_content, post_title);
                if (list != null) {
                    list.add(jokePost);
                }

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
