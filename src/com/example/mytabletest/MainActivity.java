package com.example.mytabletest;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author daij
 * @version 1.0
 * 
 */
public class MainActivity extends Activity implements OnClickListener {
	// private String[] items = { "01", "02", "03", "04", "05", "06", "07",
	// "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
	// "20" };
	private LinearLayout llAreas;
	private int totalFloors = 30, currentFloor = 1;
	// private TextView tvWRItem1, tvWRItem2, tvWRItem3;
	private ArrayList<WashingRoomPojo> washingroomAreas = new ArrayList<WashingRoomPojo>();// 水系统
	// protected UtilDialog utilDialog;
	protected Handler handler = new Handler();
	protected ImageView tx_Back;
	// protected CustomProgressDialog dialogProgress;
	protected Animation animation;
	protected LinearLayout mainLayout;
	protected Dialog progressDialog;
	private TextView printPDF;
	private int flag = -1;
	private int flagdoc = -1;

	private CharSequence myString = "";
	private Handler handler1 = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		printPDF = (TextView) findViewById(R.id.printPDF);
		printPDF.setOnClickListener(this);

		getData();
		setViews();

		showData();
		try {
			assetsDataToSD(new File(Environment.getExternalStorageDirectory()
					+ "/aa.doc"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void assetsDataToSD(File fileName) throws IOException {
		InputStream myInput;
		OutputStream myOutput = new FileOutputStream(fileName);
		myInput = this.getAssets().open("aa.doc");
		byte[] buffer = new byte[1024];
		int length = myInput.read(buffer);
		while (length > 0) {
			myOutput.write(buffer, 0, length);
			length = myInput.read(buffer);
		}
		myOutput.flush();
		myInput.close();
		myOutput.close();
	}

	private void doScan() {
		// 获取模板文件
		File demoFile = new File(Environment.getExternalStorageDirectory()
				+ "/aa.doc");
		// 创建生成的文件
		File newFile = new File(Environment.getExternalStorageDirectory()
				+ "/bb.doc");
		if (newFile.exists()) {
			newFile.delete();
		}

		Map<String, String> map = new HashMap<String, String>();
		map.put("$QYFZR$", myString.toString());

		writeDoc(demoFile, newFile, map);
		// 查看
		// doOpenWord();
	}

	/**
	 * 调用手机中安装的可打开word的软件
	 */
	private void doOpenWord() {
		Intent intent = new Intent();
		intent.setAction("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		String fileMimeType = "application/msword";
		intent.setDataAndType(
				Uri.fromFile(new File(Environment.getExternalStorageDirectory()
						+ "/bb.doc")), fileMimeType);
		try {
			MainActivity.this.startActivity(intent);
		} catch (ActivityNotFoundException e) {
			// 检测到系统尚未安装OliveOffice的apk程序
			Toast.makeText(MainActivity.this, "未找到软件", Toast.LENGTH_LONG)
					.show();
			// 请先到www.olivephone.com/e.apk下载并安装
		}
	}

	/**
	 * demoFile 模板文件 newFile 生成文件 map 要填充的数据
	 * */
	public void writeDoc(File demoFile, File newFile, Map<String, String> map) {
		try {
			FileInputStream in = new FileInputStream(demoFile);
			HWPFDocument hdt = new HWPFDocument(in);
			// Fields fields = hdt.getFields();
			// 读取word文本内容
			Range range = hdt.getRange();
			// System.out.println(range.text());

			// 替换文本内容
			for (Map.Entry<String, String> entry : map.entrySet()) {
				range.replaceText(entry.getKey(), entry.getValue());
			}
			ByteArrayOutputStream ostream = new ByteArrayOutputStream();
			FileOutputStream out = new FileOutputStream(newFile, true);
			hdt.write(ostream);
			// 输出字节流
			out.write(ostream.toByteArray());
			out.close();
			ostream.close();
			flag = 2;
		} catch (IOException e) {
			e.printStackTrace();
			flag = 1;
		} catch (Exception e) {
			flag = 1;
			e.printStackTrace();
		}
	}

	private TextWatcher textWatcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			myString = s;// tvManMt.getText().toString().trim();
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			myString = s;// tvManMt.getText().toString().trim();
			//
		}

		@Override
		public void afterTextChanged(Editable arg0) {
			// TODO Auto-generated method stub
		}
	};

	/**
	 * 显示数据
	 */
	EditText tvManMt;

	private void showData() {

		for (int i = 0; i < washingroomAreas.size(); i++) {// 动态添加状态
			final WashingRoomPojo pojo = washingroomAreas.get(i);

			LinearLayout llWashingRoomItem = new LinearLayout(this);
			llWashingRoomItem.setLayoutParams(new RelativeLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			llWashingRoomItem = (LinearLayout) getLayoutInflater().inflate(
					R.layout.wr_area_item, null);

			TextView tvAreaNames = (TextView) llWashingRoomItem
					.findViewById(R.id.tv_wr_mt_mans);
			TextView tvAreaName = (TextView) llWashingRoomItem
					.findViewById(R.id.tv_wr_areaname);
			tvManMt = (EditText) llWashingRoomItem
					.findViewById(R.id.tv_wr_mt_man);
			if (i == 1) {
				tvManMt.setText("11011011010");
				tvManMt.setInputType(0x00000003);
				tvManMt.setOnClickListener(this);
				tvManMt.addTextChangedListener(textWatcher);

			} else {
				tvManMt.setEnabled(false);
			}
			;

			TextView tvManDc = (TextView) llWashingRoomItem
					.findViewById(R.id.tv_wr_dc_man);
			TextView tvManXBC = (TextView) llWashingRoomItem
					.findViewById(R.id.tv_wr_xbc_man);
			TextView tvManXBC1 = (TextView) llWashingRoomItem
					.findViewById(R.id.tv_wr_xbc_manl);
			TextView tvManXBC12 = (TextView) llWashingRoomItem
					.findViewById(R.id.tv_wr_xbc_manl2);
			TextView tvWomanMt = (TextView) llWashingRoomItem
					.findViewById(R.id.tv_wr_mt_woman);
			TextView tvWomanDc = (TextView) llWashingRoomItem
					.findViewById(R.id.tv_wr_dc_woman);
			TextView tvWomanXBC = (TextView) llWashingRoomItem
					.findViewById(R.id.tv_wr_xbc_woman);
			if (i == 1) {
				pojo.setWrManMT(myString);
			}
			tvAreaName.setText(pojo.wrAreaName);
			tvAreaNames.setText(pojo.wrAreaNames);
			tvManMt.setText("" + pojo.wrManMTNum);//
			tvManDc.setText("" + pojo.wrManDCNum);
			tvManXBC.setText("" + pojo.wrManXBCNum);
			tvManXBC1.setText("" + pojo.wrManXBCNum1);
			tvManXBC12.setText("" + pojo.wrManXBCNum12);
			llAreas.addView(llWashingRoomItem);
		}
	}

	/**
	 * 获取数据
	 */
	private void getData() {
		if (myString.equals("")) {
			myString = "10010010010";
		}

		washingroomAreas.add(new WashingRoomPojo().setWrAreaNames("客服姓名")
				.setWrManMT("张三").setWrManDC("车牌号").setWrManXBC("京NY2008")
				.setWrManXBC1("里程数").setWrManXBC12("1098公里"));
		washingroomAreas.add(new WashingRoomPojo().setWrAreaNames("联系电话")
				.setWrManMT(myString).setWrManDC("VNI")
				.setWrManXBC("LSVFA257845").setWrManXBC1("进店时间")
				.setWrManXBC12("2016/4/15"));
		washingroomAreas.add(new WashingRoomPojo().setWrAreaNames("车型")
				.setWrManMT("X65").setWrManDC("颜色").setWrManXBC("香槟金")
				.setWrManXBC1("预约客户").setWrManXBC12("是"));
		washingroomAreas.add(new WashingRoomPojo().setWrAreaNames("是否环检")
				.setWrManMT("是").setWrManDC("维修类别").setWrManXBC("板喷")
				.setWrManXBC1("是否洗车").setWrManXBC12("是"));

	}

	private void setViews() {

		llAreas = (LinearLayout) findViewById(R.id.wr_areas);

	}

	int resultID = -1;
	String result1 = "";
	String fileUrlpdf = Environment.getExternalStorageDirectory()
			+ "/kuangtiecheng1.pdf";

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.printPDF:
			// flag=2;
			showProgressBar();
			Toast.makeText(MainActivity.this, "请等待", Toast.LENGTH_LONG).show();
			FpdkImportPDF pdf = new FpdkImportPDF(myString);
			flag = pdf.dkzzszyfp();
			if (flag == 1) {
				doScan();
			}
			hideProgressBar();
			// flag = 2;
			if (flag == 2) {

				String fileUrldoc = Environment.getExternalStorageDirectory()
						+ "/bb.doc";
				String fileUrlpdf = Environment.getExternalStorageDirectory()
						+ "/kuangtiecheng1.pdf";
				Intent intent = new Intent(this, ShowWordActivity.class);
				intent.putExtra("filePath", fileUrldoc);
				intent.putExtra("filePathpdf", fileUrlpdf);
				startActivity(intent);

			}

			break;
		case R.id.tv_wr_mt_man:

			break;

		default:
			break;
		}

	}

	// 显示载入弹窗
	public void showProgressBar() {
		// dialogProgress =new CustomProgressDialog(this, "正在加载中");
		// dialogProgress.show();
		progressDialog = new Dialog(this, R.style.progress_dialog);
		progressDialog.setContentView(R.layout.progress_dialog_ios);
		progressDialog.setCancelable(true);
		progressDialog.getWindow().setBackgroundDrawableResource(
				android.R.color.transparent);
		TextView msg = (TextView) progressDialog
				.findViewById(R.id.id_tv_loadingmsg);
		msg.setText("加载中");
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
