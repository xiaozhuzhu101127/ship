package com.topjet.shipper.util;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpStatus;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;


/**  
 * <pre>
 * Copyright:	Copyright (c)2014 
 * Company:		杭州龙驹信息科技有限公司
 * Author:		wanghm
 * Create at:	2014-3-28 下午4:16:45  
 * Description: 图片异步加载类
 *
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------  
 * 
 * </pre>
 */  
public class ImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;

    public ImageTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
        	//单纯图片的话，可以使用这个.openStream返回的是整个页面
            InputStream in = new java.net.URL(urldisplay).openStream();
        	mIcon11 = BitmapFactory.decodeStream(in);
//        	HttpURLConnection connection = (HttpURLConnection) new URL(urldisplay).openConnection();
//        	connection.setRequestMethod("GET");
//        	if(HttpStatus.SC_OK == connection.getResponseCode()){
//        		InputStream in = connection.getInputStream();
//        		mIcon11 = BitmapFactory.decodeStream(in);
//        	}
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }
}
