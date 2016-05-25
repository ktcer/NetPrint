
package com.example.mytabletest;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.widget.Toast;



public class Tools {
	/**
	 * 
	 * @param fileName
	 * @return byte[]
	 */
	public static  byte[] readFile(String fileName)
	{
		FileInputStream fis=null;
		ByteArrayOutputStream baos=null;
		byte[] data=null;
		try {
			fis=new FileInputStream(fileName);
			byte[] buffer=new byte[8*1024];
			int readSize=-1;
			baos=new ByteArrayOutputStream();
			while((readSize=fis.read(buffer))!=-1)
			{
				baos.write(buffer, 0, readSize);
			}
			return baos.toByteArray();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally
		{
			try {
				if (fis!=null)
				{
					fis.close();
				}
				if (baos!=null)
					baos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return data;
		
	}
	
	//���� /mnt/sdcard/tarenaEnglish/book/1/1.txt 
	    //mnt/sdcard/tarenaEnglish/db/word.db
	   //mnt/sdcard/tarenaEnglish/sound/
	/**
	 * 
	 * @param data ���
	 * @param path ·��
	 * @param fileName �ļ���
	 * @return true�ɹ� falseʧ��
	 */
	public static boolean writeToSdcard(byte[]data,String path,String fileName)
	{
		boolean isSuccess=false;
		FileOutputStream fos=null;
		try {
			//�ж���û��sdCard
	//		String state=android.os.Environment.getExternalStorageState();
			//true,��ʾ��sdcard
//			if (android.os.Environment.MEDIA_MOUNTED.equals(state))
//			{
				//�ж���û���ļ���
				//path=/mnt/sdcard/tarenaEnglish/book/1
				File filePath=new File(path);
				if(!filePath.exists())
				{
					//�����ļ���
					filePath.mkdirs();
				}
				
				//�ж���û��ͬ����ļ�
				File file=new File(path+fileName);
				//�еĻ���ɾ��
				if (file.exists())
				{
					file.delete();
				}
				//д�ļ�
				fos=new FileOutputStream(file);
				fos.write(data);
				fos.flush();
				return true;
	//		}	
			 
		} catch (Exception e) {
			return false;
			// TODO: handle exception
		}finally
		{
			try {
				if (fos!=null)
					fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	//	return false;
	}
	/**
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNull(String str)
	{
		if (null==str||"".equals(str)||"null".equals(str))
		{
			return true;
		}
		return false;
	}
	/**
	 * 
	 * @param context
	 * @param str
	 */
	public static void showInfo(Context context,String str)
	{
		//һ��
		Toast.makeText(context, str, Toast.LENGTH_LONG).show();
//�Ժ�Ҫ�ĳ� toast.xml.
		//		Toast toast=new Toast(context);
//		toast.setView(view);
	}

}
