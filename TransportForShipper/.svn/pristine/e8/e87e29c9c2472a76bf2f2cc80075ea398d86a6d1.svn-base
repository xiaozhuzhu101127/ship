package com.topjet.shipper.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

public class ShapeUtils {
	
	
	
	
	  public static Bitmap toRoundBitmap(Bitmap bitmap)
	    {
	        int width = bitmap.getWidth();
	        int height = bitmap.getHeight();
	        float roundPx;
	        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
	        if (width <= height)
	        {
	            roundPx = width / 2;
	            top = 0;
	            bottom = width;
	            left = 0;
	            right = width;
	            height = width;
	            dst_left = 0;
	            dst_top = 0;
	            dst_right = width;
	            dst_bottom = width;
	        }
	        else
	        {
	            roundPx = height / 2;
	            float clip = (width - height) / 2;
	            left = clip;
	            right = width - clip;
	            top = 0;
	            bottom = height;
	            width = height;
	            dst_left = 0;
	            dst_top = 0;
	            dst_right = height;
	            dst_bottom = height;
	        }
	        Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
	        Canvas canvas = new Canvas(output);
	        final int color = 0xff424242;
	        final Paint paint = new Paint();
	        final Rect src = new Rect((int) left, (int) top, (int) right,
	                (int) bottom);
	        final Rect dst = new Rect((int) dst_left, (int) dst_top,
	                (int) dst_right, (int) dst_bottom);
	        final RectF rectF = new RectF(dst);
	        paint.setAntiAlias(true);
	        canvas.drawARGB(0, 0, 0, 0);
	        paint.setColor(color);
	        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
	        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
	        canvas.drawBitmap(bitmap, src, dst, paint);
	        return output;
	    } 






	
	
	
	public static Bitmap GetRoundedCornerBitmap(Bitmap bitmap) {  

		    try {  

		        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),  

	                 bitmap.getHeight(), Config.ARGB_8888);  

		         Canvas canvas = new Canvas(output);                  

		         final Paint paint = new Paint();  

		        final Rect rect = new Rect(0, 0, bitmap.getWidth(),  

		                bitmap.getHeight());         

		         final RectF rectF = new RectF(new Rect(0, 0, bitmap.getWidth(),  

		                 bitmap.getHeight()));  

		        final float roundPx = 14;  

		       paint.setAntiAlias(true);  

		         canvas.drawARGB(0, 0, 0, 0);  

		        paint.setColor(Color.BLACK);         

		         canvas.drawRoundRect(rectF, roundPx, roundPx, paint);  

	        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));              

	  

		       final Rect src = new Rect(0, 0, bitmap.getWidth(),  

		               bitmap.getHeight());  

		            

		        canvas.drawBitmap(bitmap, src, rect, paint);     

		        return output;  

		    } catch (Exception e) {          

		        return bitmap;  

		    }  

		 } 
	
	
	
	public static Bitmap drawableToBitmap(Drawable drawable) {       

        Bitmap bitmap = Bitmap.createBitmap(

                                        drawable.getIntrinsicWidth(),

                                        drawable.getIntrinsicHeight(),

                                        drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888

                                                        : Bitmap.Config.RGB_565);

        Canvas canvas = new Canvas(bitmap);

        //canvas.setBitmap(bitmap);

        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

        drawable.draw(canvas);

        return bitmap;

}
	
	
	
	
    public  static Bitmap compressImage(Bitmap image) {  
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中  
        int options = 100;  
        while ( baos.toByteArray().length / 1024>200) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩         
            baos.reset();//重置baos即清空baos  
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中  
            options -= 10;//每次都减少10  
        }  
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中  
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片  
        return bitmap;  
    } 
    
 public  static byte[] compressImageToByte(Bitmap image) {  
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中  
        int options = 100;  
        while ( baos.toByteArray().length / 1024>200) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩         
            baos.reset();//重置baos即清空baos  
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中  
            options -= 10;//每次都减少10  
        }  
       
       return baos.toByteArray();
    }  

    
    
    public static  byte[] Bitmap2Bytes(Bitmap bm){    
        ByteArrayOutputStream baos = new ByteArrayOutputStream();      
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);      
        return baos.toByteArray();    
      }  



}
