public class MainActivity extends Activity implements OnClickListener{
	private Button play;
	private Button pause;
	private Button replay;
	private VideoView videoView;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		play = (Button) findViewById(R.id.play);
		pause = (Button) findViewById(R.id.pause);
		replay = (Button) findViewById(R.id.replay);
		videoView = (VideoView) findViewById(R.id.video_view);
		play.setOnClickListener(this);
		pause.setOnClickListener(this);
		replay.setOnClickListener(this);
		initVideoPath();
	}

	private void initVideoPath(){
		File file = new File(Environment.getExternalStorageDirectory(), "movie.3gp");
		videoView.setVideoPath(file.getPath());
	}

	@Override
	public void onClick(View v){
		switch(v.getId()){
		case R.id.play:
			if(!videoView.isPlaying()){
				videoView.play();
			}
			break;
		case R.id.pause:
			if(videoView.isPlaying){
				videoView.pause();
			}
			break;
		case R.id.replay:
			if(videoView.isPlaying()){
				videoView.resume();
			}
			break;
		default:
			break;
		}
	}

	@Override
	protected void onDestroy(){
		super.onDestroy();
		if(videoView != null){
			videoView.suspend();
		}
	}
}