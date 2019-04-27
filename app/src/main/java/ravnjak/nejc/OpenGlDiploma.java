package ravnjak.nejc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.WindowManager;

public class OpenGlDiploma extends Activity{
GLSurface ime;
float x;
float y;
int a=3;
int b;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		
		
		ime=new GLSurface(this, a);
		//ime.setRenderer(new GLRenderer());
		//ce se bo kaj delal program pred openGL
		setContentView(ime);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		
	
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		ime.onPause();
	}
	


	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		ime.onResume();
	}
	

}

class GLSurface extends GLSurfaceView implements SensorEventListener{

	private final GLRenderer red;
	
	public GLSurface(Context context, int a) {
		super(context);
		
		SensorManager maneger = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		boolean je = maneger.getSensorList(Sensor.TYPE_ACCELEROMETER).size()>0;
		if (je){
			Sensor accelo = maneger.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
			if(! maneger.registerListener(this,accelo, SensorManager.SENSOR_DELAY_GAME))
				je=false;
			
		}
		
		red= new GLRenderer();
		setRenderer(red);
		red.ris=a;
		
		
		// TODO Auto-generated constructor stub
	}


	public boolean onTouchEvent(MotionEvent e) {
		switch (e.getAction()){
		case MotionEvent.ACTION_DOWN:
			red.y=e.getY();
			red.x=e.getX();
			break;
		case MotionEvent.ACTION_MOVE:
			red.y=e.getY();
			red.x=e.getX();
			red.dx+=e.getX();
			red.dy+=e.getY();
			break;
		case MotionEvent.ACTION_UP:
			red.y=e.getY();
			red.x=e.getX();
			break;
		
		}
		return true;
	}


	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}


	
	 public void onSensorChanged(SensorEvent arg0) {
		// TODO Auto-generated method stub
		red.kotx+=arg0.values[2];
		red.koty+=arg0.values[0];
		
		if (red.b>(red.hei/100)){
			red.b=(float) (red.hei/100);
			red.b-=arg0.values[1]/100;}
		else
			if (red.b<(-red.hei/50)){
				red.b=(float) (-red.hei/50);
				red.b-=arg0.values[1]/100;}
			else
				red.b-=arg0.values[1]/100;
		
		if (red.a>(red.wi/50)){
			red.a=(float) (red.wi/50);
			red.a+=arg0.values[0]/100;}
		else
			if (red.a<(-red.wi/50)){
				red.a=(float) (-red.wi/50);
				red.a+=arg0.values[0]/100;}
			else
				red.a+=arg0.values[0]/100;
		
		
		//red.vn+=arg0.values[2]/100;
	}
	
	
	
	
	
}
