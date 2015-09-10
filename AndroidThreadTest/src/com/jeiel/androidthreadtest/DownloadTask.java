public class DownloadTask extends AsynTask<Void, Integer, Boolean>{
	/*private ProgressDialog progressDialog;
	private Context context;

	public DownloadTask(Context context, ProgressDialog progressDialog){
		this.context = context;
		this.progressDialog = progressDialog;
	}*/

	@Override
	protected void onPreExecute(){
		progressDialog.show();
	}

	@Override
	protected Boolean doInBackground(Void... params){
		try{
			while(true){
				int downloadPercent = doDownload();
				publishProgress(downloadPercent);
				if(downloadPercent >= 100){
					break;
				}
			}
		}catch(Exception e){
			return false;
		}
		return true;
	}

	@Override
	protected void onPregressUpdate(Integer... values){
		progressDialog.setMessage("Download " + values[0] + "%");
	}

	@Override
	protected void onPostExecute(Boolean result){
		progressDialog.dismiss();
		if(result){
			Toast.makeText(context, "Download succeeded", Toast.LENGTH_SHORT).show()
		}else{
			Toast.makeText(context, "Download failed", Toast.LENGTH_SHORT).show()
		}
	}
}