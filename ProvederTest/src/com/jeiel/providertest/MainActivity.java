public class MainActivity extends Activity{

	private String newId;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button addBtn = (Button)findViewById(R.id.add_btn);
		Button queryBtn = (Button)findViewById(R.id.query_btn);
		Button updateBtn = (Button)findViewById(R.id.update_btn);
		Button deleteBtn = (Button)findViewById(R.id.delete_btn);

		addBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				Uri uri = Uri.parse("content://com.jeiel.databasetest.provider/Book");
				ContentValues values = new ContentValues();
				values.put("name", "A Clash of Kings");
				values.put("author", "George Martin");
				values.put("pages", 1040);
				values.put("price", 22.85);
				Uri newUri = getContentResolver().insert(uri, values);
				newId = newUri.getPathSegments().get(1);
			}
		});

		queryBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				Uri uri = Uri.parse("content://com.jeiel.databasetest.provider/Book");
				Cursor cursor = getContentResolver().query(uri, null, null, null, null);
				if(cursor != null){
					while(cursor.moveToNext()){
						String name = cursor.getString(cursor.getColumnIndex("name"));
						String author = cursor.getString(cursor.getColumnIndex("author"));
						int pages = cursor.getString(cursor.getColumnIndex("pages"));
						double price = cursor.getDouble(cursor.getColumnIndex("price"));
						Log.d("MainActivity", "book name is " + name);
						Log.d("MainActivity", "book author is " + author);
						Log.d("MainActivity", "book pages is " + pages);
						Log.d("MainActivity", "book price is " + price);
					}
					cursor.close();
				}
			}
		});

		updateBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				Uri uri = Uri.parse("content://com.jeiel.databasetest.provider/Book/"+newId);
				ContentValues values = new Contentvalues();
				values.put("name", "A Storm of Swords");
				values.put("pages", 1216);
				values.put("price", 24.05);
				getContentResolver().update(uri, values, null, null);
			}
		});

		deleteBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				Uri uri = Uri.parse("content://com.jeiel.databasetest.provider/Book/"+newId);
				getContentResolver().delete(uri, null, null);
			}
		});
	}
}