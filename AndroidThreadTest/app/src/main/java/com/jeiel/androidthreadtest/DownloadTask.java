package com.jeiel.androidthreadtest;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class DownloadTask extends AsyncTask<Void, Integer, Boolean> {
	private ProgressDialog progressDialog;
	private Context context;
	private int total = 500;
	private int progress = 0;

	public DownloadTask(Context context, ProgressDialog progressDialog){
		this.context = context;
		this.progressDialog = progressDialog;
		total = 500;
		progress = 0;
	}

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

	private int doDownload(){
		return (++progress)/total;
	}

	@Override
	protected void onProgressUpdate(Integer... values){
		progressDialog.setMessage("Download " + values[0] + "%");
	}

	@Override
	protected void onPostExecute(Boolean result){
		progressDialog.dismiss();
		if(result){
			Toast.makeText(context, "Download succeeded", Toast.LENGTH_SHORT).show();
		}else{
			Toast.makeText(context, "Download failed", Toast.LENGTH_SHORT).show();
		}
	}
}