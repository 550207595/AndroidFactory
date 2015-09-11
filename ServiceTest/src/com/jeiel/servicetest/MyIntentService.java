public class MyIntentService extends IntentService{
	public MyIntentService(){
		super("MyIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent){
		Log.d("MyIntentService", "Thread id is " + Thread.currentThread().getId());
	}

	@Override
	protected void onDestroy(){
		super.onDestroy();
		Log.d("MyIntentService", "onDestroy executed");
	}
}