package com.example.mytabletest;

import java.util.HashMap;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * 获取用户个人信息
 * 
 * @author chenkeliang
 * 
 */
public class AsyPrint extends AsyncTask<Integer, Integer, String> {

	String resultUrl = "";
	private Activity act;
	private static String tempUrl = "http://10.104.13.250:8080//netprint/app/print";
	private String fileUrl;

	public AsyPrint(Activity act, String fileUrl) {
		this.act = act;
		this.fileUrl = fileUrl;
	}

	@Override
	protected String doInBackground(Integer... params) {
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("fileName", "kuangtiecheng1.pdf");
		// param.put("userType", 1+"");
		try {
			resultUrl = NetTool.sendPostRequest(tempUrl, param, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(act, "异常", Toast.LENGTH_LONG).show();
		}

		return resultUrl;
	}

	@Override
	protected void onPostExecute(String result) {
		// JSONObject loginResult;
		// String myPhotos = "";
		// try {
		// loginResult = new JSONObject(resultUrl);
		// myPhotos = loginResult.getString("picurl");
		// } catch (JSONException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

	// private String JsonArrayToList(String jsonString)
	// throws Exception {
	// Gson gson = new Gson();
	// BeanPersonInfo beenInfo = new BeanPersonInfo();;
	// int len = jsonString.length();
	// if (jsonString != "") {
	// if (!(jsonString.equals(-1))) {
	// beenInfo = gson.fromJson(jsonString,
	// new TypeToken<BeanPersonInfo>() {
	// }.getType());
	// }
	// }
	// return beenInfo;
	// }

}
