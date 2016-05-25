package com.example.mytabletest;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

@SuppressLint("NewApi")
public class ActivityDetailWebService extends Activity implements
		OnClickListener {

	private WebView webservice;
	private String TravelID;
	private ProgressBar progress;
	private TextView Title;
	private String title;
	private String url = Environment.getExternalStorageDirectory()
			+ "/kuangtiecheng1.jpg";;

	private Options getBitmapOption(int inSampleSize) {
		System.gc();
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPurgeable = true;
		options.inSampleSize = inSampleSize;
		return options;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webservice);
		Title = (TextView) findViewById(R.id.middle_tv);
		// title = getIntent().getStringExtra("title");
		url = getIntent().getStringExtra("url");
		Title.setText("预览");
		findViewById(R.id.right_tv).setOnClickListener(this);
		webservice = (WebView) findViewById(R.id.webview_webservice);
		// progress = (ProgressBar) findViewById(R.id.myProgressBar_webservice);

		webservice.getSettings().setJavaScriptEnabled(true);
		// webservice.getSettings().supportZoom();
		// // webservice.getSettings().getDisplayZoomControls();
		// webservice.getSettings().setSupportZoom(true);
		// webservice.invokeZoomPicker();
		// webservice.zoomIn();
		// webservice.setWebChromeClient(new WebChromeClient());
		// // webservice.setDownloadListener(new DownloadListener() {
		// //
		// // @Override
		// // public void onDownloadStart(String url, String userAgent,
		// // String contentDisposition, String mimetype,
		// // long contentLength) {
		// //
		// // // 调用系统浏览器下载
		// // Uri uri = Uri.parse(url);
		// //// Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		// //// startActivity(intent);
		// // Intent intent = new Intent();
		// // intent.setAction(android.content.Intent.ACTION_VIEW);
		// // intent.setDataAndType(Uri.fromFile(new
		// File(url)),"application/pdf");
		// // startActivity(intent);
		// // }
		// // });
		webservice.loadUrl("file://" + url);
		//
		//
		// // webservice.setWebChromeClient(new WebChromeClient() {
		// //
		// // @Override
		// // public void onProgressChanged(WebView view, int newProgress) {
		// // // TODO Auto-generated method stub
		// // if (newProgress == 100) {
		// // progress.setVisibility(View.INVISIBLE);
		// // } else {
		// // if (View.INVISIBLE == progress.getVisibility()) {
		// // progress.setVisibility(View.VISIBLE);
		// // }
		// // progress.setProgress(newProgress);
		// // }
		// // super.onProgressChanged(view, newProgress);
		// // }
		// //
		// // });
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}
}
