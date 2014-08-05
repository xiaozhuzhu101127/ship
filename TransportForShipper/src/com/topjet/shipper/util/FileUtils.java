package com.topjet.shipper.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Environment;

/**
 * 提供文件操作
 * @author Administrator
 *
 */



public final class FileUtils {
	private static final int BUFFER_SIZE = 8 * 1024; 
	private FileUtils() {
	}
	
	
	public  static File violateImagePath = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "violateImage");

	public  static File violateImage = new File(violateImagePath, "violate.jpg");
	
	public static void copyStream(InputStream is, OutputStream os) throws IOException {
		byte[] bytes = new byte[BUFFER_SIZE];
		while (true) {
			int count = is.read(bytes, 0, BUFFER_SIZE);
			if (count == -1) {
				break;
			}
			os.write(bytes, 0, count);
		}
	}
	
	
	  /**
     * 读取图片属性：旋转的角度
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
public static Bitmap getViolatePicture(Context context,File file ){
	
    Bitmap bitmap =null;  
//    int scallType = 0;  
    try {   
        ExifInterface exifInterface = new ExifInterface(file.getPath());   
        int result = exifInterface.getAttributeInt(   
                ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);   
        int rotate = 0;   
        switch(result) {   
        case ExifInterface.ORIENTATION_ROTATE_90:   
            rotate = 180;   
            break;   
        case ExifInterface.ORIENTATION_ROTATE_180:   
            rotate = 180;   
            break;   
        case ExifInterface.ORIENTATION_ROTATE_270:   
            rotate = 270;   
            break;   
        default:   
            break;   
        }   
        BitmapFactory.Options options = new BitmapFactory.Options();  
        options.inPreferredConfig = Bitmap.Config.RGB_565;      
        options.inJustDecodeBounds = true;  
        BitmapFactory.decodeFile(file.getPath(), options);  
        if (options.outWidth < 0 || options.outHeight < 0) {  
            return null;  
        }    
        options.inJustDecodeBounds = false;  
        bitmap=  BitmapFactory.decodeFile(file.getPath(), options);  
        if(rotate > 0) {   
            Matrix matrix = new Matrix();   
            matrix.setRotate(rotate);   
            Bitmap rotateBitmap = Bitmap.createBitmap(   
                    bitmap, 0, 0, options.outWidth, options.outHeight, matrix, true);   
            if(rotateBitmap != null) {   
                bitmap.recycle();   
                bitmap = rotateBitmap;   
            }   
        }   
    
    } catch (IOException e) {   
        e.printStackTrace();   
    }   
    return bitmap;
}


	
	
//	public static void savePhoto(Context context,  String picName ,Bitmap bitmap){
//		
//		try {
//			FileOutputStream fos = null;
//			File file = null;
//			file = context.getFileStreamPath(picName);  
//			fos = new FileOutputStream(file);
//			// save picture to local file
//			picture.compress(Bitmap.CompressFormat.JPEG, 90, fos);
//			fos.flush();
//			fos.close();
//			} catch (FileNotFoundException e) {
//			e.printStackTrace();
//			} catch (IOException e) {
//			e.printStackTrace();
//			}
//	}
	
	
	 public static byte[] read(InputStream in) throws Exception {  
		  ByteArrayOutputStream out = new ByteArrayOutputStream();  
		  if (in != null) {  
		   byte[] buffer = new byte[1024];  
		   int length = 0;  
		   while ((length = in.read(buffer)) != -1) {  
		    out.write(buffer, 0, length);  
		   }  
		   out.close();  
		   in.close();  
		   return out.toByteArray();  
		  }  
		  return null;  
		 }  
	 
	 
	 public static byte[] Bitmap2Bytes(Bitmap bm) {
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
		         bm.compress(Bitmap.CompressFormat.JPEG, 60, baos);
		         return baos.toByteArray();
	   }

}
