public class MsgAdapter extends MsgAdapter<Msg>{
	int resourceId;
	public MsgAdapter(Context context, int textViewResourceId, List<Msg> objects){
		super(context, textViewResourceId,objects);
		resourceId=textViewResourceId;
	}

	@override
	public View getView(int position, View convertView,GroupView parent){
		Msg msg=getItem(position);
		View view;
		ViewHolder viewHolder;
		if(convertView==null){
			view=LayoutInflater.from(getContext()).inflate(resourceId,null);
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
			viewHolder.rightMsgLayout.setVisibility(View.VISIBALE);
			viewHolder.rightMsgText.setText(msg.getContent());
		}else if(msg.getType()==Msg.RECEIVE){
			viewHolder.rightMsgLayout.setVisibility(View.GONE);
			viewHolder.leftMsgLayout.setVisibility(View.VISIBALE);
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