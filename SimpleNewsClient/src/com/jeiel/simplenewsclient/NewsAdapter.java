public class NewsAdapter extends ArrayAdapter<News>{
	private int resourceId;
	public NewsAdapter(Context context, int textViewResourceId, List<News> objects){
		super(context,textViewResourceId,objects);
		resourceId=textviewresourceid;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		News news=getItem(position);
		View view;
		ViewHolder viewHolder;
		if(convertView==null){
			view=LayoutInflater.from(getContext()).inflate(resourceId,null);
			viewHolder=new ViewHolder();
			viewHolder.newsTitleText=(TextView)view.findViewById(R.id.title_text);
			view.setTag(viewHolder);
		}else{
			view=convertView;
			viewHolder=(ViewHolder)view.getTag();
		}
		viewHolder.newsTitleText.setText(news.getTitle());
		return view;
	}

	class ViewHolder{
		TextView newsTitleText;
	}
}