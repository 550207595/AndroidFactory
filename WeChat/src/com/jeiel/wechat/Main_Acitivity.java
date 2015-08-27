public class MainActivity extends Activity{
	private List<String> msgList=new ArrayList<String>();
	@override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.id.activity_main);
		intiMsgList();
		ListView msgListView=(ListView)findViewById(R.id.msg_list_view);
		MsgAdapter adapter=new MsgAdapter(this,R.layout.msg_item,msgList);
		msgListView.setAdapter(adapter);
		Button sendBtn=(Button)findViewById(R.id.send_btn);
		EditText msgText=(EditText)findViewById(R.id.msg_text);
		sendBtn.setOnClickedListener(new OnClickedListener(){
			@override
			public void onClicked(View v){
				Msg msg=new Msg();
				msg.setContent(msgText.getText());
				msg.setType(Msg.SEND);
				msgList.add(msg);
				adapter.notifyDataSetChanged();
				msgListView.setSelection(msgList.size());
				msgText.setText("");
			}
		});
	}

	public void initMsgList(){
		Msg msg1=new Msg();
		msg1.setContent("msg1");
		msg1.setType(Msg.RECEIVE);
		msgList.add(msg1);
		Msg msg2=new Msg();
		msg2.setContent("msg2");
		msg2.setType(Msg.SEND);
		msgList.add(msg2);
		Msg msg3=new Msg();
		msg3.setContent("msg3");
		msg3.setType(Msg.RECEIVE);
		msgList.add(msg3);
	}
}