package com.jeiel.simplenewsclient;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class NewsTitleFragment extends Fragment implements AdapterView.OnItemClickListener{
    private ListView newsTitleListView;
    private List<News> newsList;
    private NewsAdapter adapter;
    private boolean isTwoPane;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        newsList=getNews();
        adapter=new NewsAdapter(activity,R.layout.news_item,newsList);
    }

    public List<News> getNews(){
        List<News> list=new ArrayList<News>();
        list.add(new News("news1","content1"));
        list.add(new News("news2","content2"));
        list.add(new News("news3news3news3news3news3news3news3news3news3news3",
                "content3content3content3content3content3content3content3content3"));
        return list;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.news_title_frag,container,false);
        newsTitleListView=(ListView)view.findViewById(R.id.news_title_list_view);
        newsTitleListView.setAdapter(adapter);
        newsTitleListView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getActivity().findViewById(R.id.news_content_fragment)!=null){
            isTwoPane=true;
        }else{
            isTwoPane=false;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        News news=newsList.get(position);
        if(isTwoPane){
            NewsContentFragment newsContentFragment=(NewsContentFragment)getFragmentManager().
                    findFragmentById(R.id.news_content_fragment);
            newsContentFragment.refresh(news.getTitle(),news.getContent());
        }else{
            NewsContentActivity.actionStart(getActivity(),news.getTitle(),news.getContent());
        }
    }
}
