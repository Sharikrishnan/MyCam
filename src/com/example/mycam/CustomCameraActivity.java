package com.example.mycam;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
 

public class CustomCameraActivity extends Activity implements SurfaceHolder.Callback {
    Camera camera;
    public int count=0;
    public int count1=0;
    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;
    boolean previewing = false;
    LayoutInflater controlInflater = null;
    public static final String TAG = "MyActivity";
    public final int MEDIA_TYPE_IMAGE=1; 
    static final int FOTO_MODE = 0;
    public int[][] cont=new int[10][6];
    public int[][] test1=new int[10][3];
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.main);
 
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
 
        getWindow().setFormat(PixelFormat.UNKNOWN);
        surfaceView = (SurfaceView)findViewById(R.id.camerapreview);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
 
        controlInflater = LayoutInflater.from(getBaseContext());
        View viewControl = controlInflater.inflate(R.layout.custom, null);
        LayoutParams layoutParamsControl = new LayoutParams(LayoutParams.FILL_PARENT,
                                                            LayoutParams.FILL_PARENT);
        this.addContentView(viewControl, layoutParamsControl);
       
       
     

        /*
         * called when image is stored
         */
      
        final Button cap=(Button)findViewById(R.id.Button01);
        cap.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
		//		final Parameters p=camera.getParameters();
			
			//	camera.setParameters(p);
				count1++;
				switch(count1)
				{
				case 10:	camera.takePicture(null,pictureCallBack, pictureCallBack);
						count1=0;
							break;
				default:camera.takePicture(null,pictureCallBack, pictureCallBack);
						break;
				}
				}
		});
        
   final Button rbg=(Button)findViewById(R.id.button02);
   rbg.setOnClickListener(new View.OnClickListener() {
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub


	  Intent i1=new Intent(CustomCameraActivity.this,Result.class);
	  startActivity(i1);
	}
});
    }
    final Camera.PictureCallback pictureCallBack = new Camera.PictureCallback() {

        /*
         * (non-Javadoc)
         * 
         * @see android.hardware.Camera.PictureCallback#onPictureTaken(byte[],
         * android.hardware.Camera) create new intent and store the image
         */
        public void onPictureTaken(byte[] data, Camera camera) {
            // TODO Auto-generated method stub
//final Parameters p=camera.getParameters();
            if (data != null) {
                Intent imgIntent = new Intent();
                storeByteImage(data);
  //              p.setFlashMode(Parameters.FLASH_MODE_AUTO);
    //            camera.setParameters(p);
                camera.startPreview();
                setResult(FOTO_MODE, imgIntent);
            }

        }
    };
    public boolean storeByteImage(byte[] data) {
        // Create the <timestamp>.jpg file and modify the exif data
    	 File folder = new File(Environment.getExternalStorageDirectory() + "/Campic1");
    	    boolean success = true;
    	    if (!folder.exists()) {
    	        //Toast.makeText(MainActivity.this, "Directory Does Not Exist, Create It", Toast.LENGTH_SHORT).show();
    	        success = folder.mkdir();
    	    }
    	    if (success) {
    	        //Toast.makeText(MainActivity.this, "Directory Created", Toast.LENGTH_SHORT).show();
    	    } else {
    	        //Toast.makeText(MainActivity.this, "Failed - Error", Toast.LENGTH_SHORT).show();
    	    }
    	    String filename = "/sdcard/Campic1"
                + String.format("/%d.jpg",count1);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(filename);
            try {
                fileOutputStream.write(data);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            fileOutputStream.flush();
            fileOutputStream.close();
            return true;

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

 
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if(previewing){
            camera.stopPreview();
            previewing = false;
        }
 
        if (camera != null){
            try {
                camera.setPreviewDisplay(surfaceHolder);
                camera.startPreview();
                previewing = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
      //set camera to continually auto-focus
        Camera.Parameters params = camera.getParameters();
        //*EDIT*//params.setFocusMode("continuous-picture");
        //It is better to use defined constraints as opposed to String, thanks to AbdelHady
        params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        //camera.setParameters(params);
        params.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
        camera.setParameters(params);
        camera.startPreview();

        camera.autoFocus(null);
    }
 
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        camera = Camera.open();
    }
 
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        camera.stopPreview();
        camera.release();
        camera = null;
        previewing = false;
    }
}