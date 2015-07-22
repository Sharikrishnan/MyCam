package com.example.mycam;

import java.io.File;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class Result extends Activity {
public int cont[][]=new int[10][6];
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		Button b=(Button)findViewById(R.id.button1);
		b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			TextView t=(TextView)findViewById(R.id.textView1);
				String s;
				s="hari";
				t.setText(s);
				
			  for(int count = 0;count<2;count++)
		{
			String filename1 = "/sdcard/Campic1"
		      + String.format("/%d.jpg",count+1);
			File f = new File(filename1);
			Bitmap bmp = BitmapFactory.decodeFile(f.getAbsolutePath());
		int pix1=bmp.getPixel(85, 461);
		int pix2=bmp.getPixel(177, 500);
		cont[count][0]=Color.red(pix1);
		cont[count][1]=Color.blue(pix1);
		cont[count][2]=Color.green(pix1);
		cont[count][3]=Color.red(pix2);
		cont[count][4]=Color.blue(pix2);
		cont[count][5]=Color.green(pix2);

		}
		String s1="";
		for(int y=0;y<10;y++)
		{
			s1=s1+cont[y][0]+"\t\t"+cont[y][1]+"\t\t"+cont[y][2]+"\t----------->\t"+cont[y][3]+"\t\t"+cont[y][4]+"\t\t"+cont[y][5]+"\n";
		}
			t.setText(s1);
			}
		});
		
//for(int y=0; y<=10; y++)
	

		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.result, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	

}
