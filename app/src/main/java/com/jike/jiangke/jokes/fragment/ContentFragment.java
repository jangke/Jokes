package com.jike.jiangke.jokes.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jike.jiangke.jokes.MainActivity;
import com.jike.jiangke.jokes.R;
import com.jike.jiangke.jokes.model.JokePost;

import java.util.List;

/**
 * Created by Administrator on 2015/12/31.
 */
public class ContentFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.content_layout,null);
        MainActivity activity= (MainActivity) getActivity();
        List<JokePost>list=activity.getList();
        TextView textView= (TextView) view.findViewById(R.id.tv_content);
        textView.setText(list.get(activity.getCurrentPostion()).getPost_content());
        return view;
    }
}
