package com.jike.jiangke.jokes;

import android.util.Log;
import android.os.Handler;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


/**
 * 提供获取网络数据，进行缓存的工具类
 */
public class Utils {

    /**
     * 解析服务器返回的数据
     *
     * @param list 解析出来的数据存入集合
     */
    public static void handleResponse(final List<JokePost> list, final Handler handler) {
//        HttpUtils httpUtils = new HttpUtils();
//
//        httpUtils.send(HttpRequest.HttpMethod.GET, "http://jikejiangke123.sinaapp.com/latestposts.php",
//                new RequestCallBack<String>() {
//                    @Override
//                    public void onSuccess(ResponseInfo<String> responseInfo) {
//                        String res = responseInfo.result;
//                        Log.d("MainActivity", res);
//                        parseJSONWithJSONObject(res, list);
//                        handler.sendEmptyMessage(0);
//
//                    }
//
//                    @Override
//                    public void onFailure(HttpException e, String s) {
//                        Log.d("MainActivity", s);
//                    }
//                });
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
            handler.sendEmptyMessage(0);


        } catch (Exception e) {
            e.printStackTrace();
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
