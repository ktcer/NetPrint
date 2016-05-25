package com.example.mytabletest;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.EncodingUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

public class ShowWordActivity extends Activity implements OnClickListener {

	private WebView webview;
	private String htmlPath;
	String filePath = Environment.getExternalStorageDirectory()+"/bb.doc";
private String fileUrlpdf;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showword);
		TextView title = (TextView) findViewById(R.id.middle_tv);
		 title.setText("预览");
		 TextView right=(TextView)  findViewById(R.id.right_tv);
		 right.setText("打印");
		 right.setVisibility(View.VISIBLE);
		 right.setOnClickListener(this);
		webview = (WebView) findViewById(R.id.webview);
		 filePath = this.getIntent().getExtras().getString("filePath");
		 fileUrlpdf = this.getIntent().getExtras().getString("filePathpdf");
		
		if(!isExists(filePath)) {
			try {
				Log.e("文件不存在", "HTML路径 ：" + htmlPath + "| " + filePath);
				WordToHtml.convert2Html(filePath, htmlPath);
			} catch (TransformerException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			}
		}
		//Log.e("HTMl文本内容：" ,getHtmlString("file://" + htmlPath));
		//String text = getHtmlString("file://" + htmlPath);
		//System.out.println(text);
		
		
		//String t = text.replace("vnf", "<a href = 'http://www.baidu.com'>" +  "vnf" + "</a>");
		//Log.e("HTMl文本内容：" ,"========================" + t);
		//File file = new File(htmlPath);
		//print(file,t);
		
		WebSettings settings = webview.getSettings();  
        //settings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        settings.setJavaScriptEnabled(true);
        webview.loadUrl("file://"+ htmlPath);
	}
	
	public boolean isExists(String path) {
		htmlPath = path.replace(".doc", ".html");
		File file = new File(htmlPath);
		if(file.exists()) {
			file.delete();
		}
		return false;
	}
		 
	public String getHtmlString(String urlString) {
		
	 
		try {  
			URL url = null;  
			url = new URL(urlString); 
			URLConnection ucon = null; 
			ucon = url.openConnection(); 
			InputStream instr = null; 
			instr = ucon.getInputStream(); 
			BufferedInputStream bis  =  new BufferedInputStream(instr); 
			ByteArrayBuffer baf = new ByteArrayBuffer(500); 
			int current = 0;  
			while((current = bis.read()) != -1) { 
				baf.append((byte) current); 
			} 
			return EncodingUtils.getString(baf.toByteArray(), "UTF-8"); 
		} 
		catch(Exception e) { 
			return ""; 
	 
		} 
	 
	} 
	
	public void print(File file,String str) {
		
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			fw = new FileWriter(file, false);
		
			bw = new BufferedWriter(fw);
			bw.write(str);
			bw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				bw.close();
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	int resultID = -1;
	String result1 = "";
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.right_tv:
			showProgressBar();
			AsyUpFile upFile = new AsyUpFile(this, fileUrlpdf){
				 @Override
				 protected void onPostExecute(String result) {
				 // TODO Auto-generated method stub
				 super.onPostExecute(result);
				 Log.i("result", "upfile"+result);
				 if (result.equals("")) {
				
				 Toast.makeText(ShowWordActivity.this, "后台出错",
				 Toast.LENGTH_LONG).show();
				 return;
				 }
				 try {
				 JSONObject json = new JSONObject(result);
				 result1 = json.getString("detail");
				 resultID = json.getInt("resultID");
				 if (resultID==1) {
					 printpdfs();
				 } else {
				 Toast.makeText(ShowWordActivity.this, "打印失败",
				 Toast.LENGTH_LONG).show();
				 }
				 } catch (JSONException e) {
				 // TODO Auto-generated catch block
				 e.printStackTrace();
				 }
				 }
			};upFile.execute();
		
			
			break;

		default:
			break;
		}
	}
	

	
	private void printpdfs(){
		 AsyPrint prinTask = new AsyPrint( this,
				 fileUrlpdf) {
		
		 @Override
		 protected void onPostExecute(String result) {
		 // TODO Auto-generated method stub
		 super.onPostExecute(result);
		 hideProgressBar();
		 Log.i("result", "print"+result);
		 if (result.equals("")) {
		
		 Toast.makeText(ShowWordActivity.this, "后台出错",
		 Toast.LENGTH_LONG).show();
		 return;
		 }
		 try {
		 JSONObject json = new JSONObject(result);
		 result1 = json.getString("detail");
		 resultID = json.getInt("resultId");
		 if (resultID==1) {
		 Toast.makeText(ShowWordActivity.this, "打印成功",
		 Toast.LENGTH_LONG).show();
		 } else {
		 Toast.makeText(ShowWordActivity.this, "打印失败",
		 Toast.LENGTH_LONG).show();
		 }
		 } catch (JSONException e) {
		 // TODO Auto-generated catch block
		 e.printStackTrace();
		 }
		 }
		
		 };
		 prinTask.execute();
	}
	protected Dialog progressDialog;
	// 显示载入弹窗
	public void showProgressBar() {
		hideProgressBar();
		// dialogProgress =new CustomProgressDialog(this, "正在加载中");
		// dialogProgress.show();
		progressDialog = new Dialog(this, R.style.progress_dialog);
		progressDialog.setContentView(R.layout.progress_dialog_ios);
		progressDialog.setCancelable(true);
		progressDialog.getWindow().setBackgroundDrawableResource(
				android.R.color.transparent);
		TextView msg = (TextView) progressDialog
				.findViewById(R.id.id_tv_loadingmsg);
		msg.setText("打印文件发送中");
		progressDialog.show();
	}

	// 隐藏载入弹窗
	public void hideProgressBar() {
		// if(dialogProgress!=null)
		// dialogProgress.dismiss();
		if (progressDialog != null)
			progressDialog.dismiss();
	}
	
}
