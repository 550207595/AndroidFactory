package com.jeiel.wechatui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class MsgAdapter extends ArrayAdapter<Msg> {
	int resourceId;
	public MsgAdapter(Context context, int textViewResourceId, List<Msg> objects){
		super(context, textViewResourceId,objects);
		resourceId=textViewResourceId;
	}

	@Override
	public View getView(int position, View convertView,ViewGroup parent){
		Msg msg=getItem(position);
		View view;
		ViewHolder viewHolder;
		if(convertView==null){
			view= LayoutInflater.from(getContext()).inflate(resourceId,null);
			viewHolder=new ViewHolder();
			viewHolder.leftMsgLayout=(LinearLayout)view.findViewById(R.id.left_msg_layout);
			viewHolder.rightMsgLayout=(LinearLayout)view.findViewById(R.id.right_msg_layout);
			viewHolder.leftMsgText=(TextView)view.findViewById(R.id.left_msg_text);
			viewHolder.rightMsgText=(TextView)view.findViewById(R.id.right_msg_text);
			view.setTag(viewHolder);
		}else{
			view=convertView;
			viewHolder=(ViewHolder)view.getTag();
		}
		if(msg.getType()==Msg.SEND){
			viewHolder.leftMsgLayout.setVisibility(View.GONE);
			viewHolder.rightMsgLayout.setVisibility(View.VISIBLE);
			viewHolder.rightMsgText.setText(msg.getContent());
		}else if(msg.getType()==Msg.RECEIVE){
			viewHolder.rightMsgLayout.setVisibility(View.GONE);
			viewHolder.leftMsgLayout.setVisibility(View.VISIBLE);
			viewHolder.leftMsgText.setText(msg.getContent());
		}
		return view;
	}

	class ViewHolder{
		LinearLayout leftMsgLayout;
		LinearLayout rightMsgLayout;
		TextView leftMsgText;
		TextView rightMsgText;
	}
}