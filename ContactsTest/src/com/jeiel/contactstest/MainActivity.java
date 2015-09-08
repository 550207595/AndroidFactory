public class MainActivity extends Activity{
	ListView contactsListView;
	ArrayAdapter<String> adapter;
	List<String> contactsList=new ArrayList<String>();
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		contactsListView=(ListView)findViewById(R.id.contacts_list_view);
		adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,contactsList);
		contactsListView.setAdapter(adapter);
		readContacts();
	}

	public void readContacts(){
		Cursor cursor=null;
		try{
			cursor=getContentResolver().query(
				ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
				null,null,null,null);
			while(cursor.moveToNext()){
				String displayName=cursor.getString(cursor.getColumIndex(
					ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
				String number=cursor.getString(cursor.getColumIndex(
					ContactsContract.CommonDataKinds.Phone.NUMBER));
				contactsList.add(displayName+"\n"+number);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}