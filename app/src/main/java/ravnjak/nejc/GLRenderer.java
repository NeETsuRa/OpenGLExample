package ravnjak.nejc;



import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import android.os.SystemClock;

public class GLRenderer implements Renderer {

	//private krogla [] krogle;
	private trikotnik t;
	private kocka c;
	private krog [] krog;
	public volatile float x;
	public volatile float y;
	public volatile float dx=0;
	public volatile float dy=0;
	float kotx=0;
	float koty=0;
	float kot=0;
	float a;
	float b;
	float d;
	float radij=2f;
	int ris=0;
	
	double hei;
	double wi;
	
	public GLRenderer() {
		/*krogle=new krogla[10];
		for (int i=0; i<krogle.length; i++){
			krogle[i]=new krogla(radij, 2, 20);
		}
		*/
		krog=new krog[10];
		for (int i=0; i<krog.length; i++){
			krog[i]=new krog(radij, 20);
		}

		t=new trikotnik();
		c=new kocka();
		
		// TODO Auto-generated constructor stub
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// TODO Auto-generated method stub

		gl.glDisable(GL10.GL_DITHER);
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
		gl.glClearColor(.6f, .4f, 3f, 0f);
		gl.glShadeModel(GL10.GL_SMOOTH);
		gl.glClearDepthf(1f);
		
	    gl.glEnable(GL10.GL_DEPTH_TEST);
	    gl.glDepthFunc(GL10.GL_LEQUAL);
	    gl.glHint(GL10.GL_POLYGON_SMOOTH_HINT, GL10.GL_NICEST);

		
	}
	
	 private void risit(GL10 gl){
		
		
		gl.glLoadIdentity();
		GLU.gluLookAt(gl, 0, 0, -10, 0, 0, 0, 0, 2, 0);
		gl.glTranslatef(7f-(x/20), 5f-(y/28), 0f);
	    gl.glRotatef(kotx, 1f, .0f, .0f);
		t.draw(gl);
		
	}
	
	
	
	 private void risic(GL10 gl){
		c.b=1f;
		gl.glLoadIdentity();
		GLU.gluLookAt(gl, 0, 0, -10, 0, 0, 0, 0, 2, 0);
		
		gl.glTranslatef(4f, 0f, 0f);
		gl.glRotatef(kotx, 1f, .0f, .0f);
		gl.glRotatef(koty, 0f, .1f, .0f);
		gl.glScalef(1f, 1f, 1f);
	    c.draw(gl);
		
	}
	
	
	private void risik(GL10 gl, krogla h){
		gl.glLoadIdentity();
		GLU.gluLookAt(gl, 0, 0, -10, 0, 0, 0, 0, 2, 0);
		
		
		if (h.vn>20)
			h.vz *=-1; 
		if (h.vn<0)
			h.vz *=-1;
		h.vn+=h.vz*.05f;
		
		if (h.gd>(hei/100))
			h.vy*=-1;
		if (h.gd<(-hei/50))
			h.vy*=-1;
		h.gd+=h.vy*.05f;
		
		if (h.ld>(wi/50))
			h.vx*=-1;
		if (h.ld<(-wi/50))
			h.vx*=-1;
		h.ld+=h.vx*.05f;
		//nastopilTrk(krogle,h);
	    gl.glTranslatef(h.ld, h.gd, h.vn);
		//gl.glTranslatef(a, b, d);
		Long time = SystemClock.uptimeMillis()%40000L;
	  	kot=h.vy*h.vx*h.vz*.08f*(time);
	   // gl.glRotatef(kot, 1f, 1f, .0f);
		h.draw(gl);
	}
	
	private void nastopilTrk(krogla[] krog, krogla x) {
		// TODO Auto-generated method stub
		for (int i=0; i<krog.length; i++){
			if (razdalja(krog[i], x)<=(krog[i].ra+x.ra)){
				if ((x.vx<=0 && krog[i].vx>0)||(x.vx>0 && krog[i].vx<0)){
					//trk v x smeri
					double v;
					v=(((x.vx*(x.m-krog[i].m))+2*krog[i].m*krog[i].vx)/(x.m+krog[i].m ));
					x.vx=(float) v;
				}
				if ((x.vy<=0 && krog[i].vy>0)||(x.vy>0 && krog[i].vy<0)){
					//trk v y smeri
					double v;
					v=(((x.vy*(x.m-krog[i].m))+2*krog[i].m*krog[i].vy)/(x.m+krog[i].m ));
					x.vy=(float) v;
				}
				if ((x.vz<=0 && krog[i].vz>0)||(x.vz>0 && krog[i].vz<0)){
					//trk v z smeri
					double v;
					v=(((x.vz*(x.m-krog[i].m))+2*krog[i].m*krog[i].vz)/(x.m+krog[i].m ));
					x.vz=(float) v;
				}
				
			}
			
		}
		
		
	}


	private float razdalja(krogla k, krogla m) {
		// TODO Auto-generated method stub
		float rezultat;
		double x;
		double y;
		double z;
		y=(k.gd-m.gd);
		x=(k.ld-m.ld);
		z=(k.vn-m.vn);
		rezultat=(float) (Math.sqrt((y*y)+(x*x)+(z*z)));
		return rezultat;
	}

	public void onDrawFrame(GL10 gl) {
		// TODO Auto-generated method stub
		 
		//GLU.gluLookAt(gl, 0, 0, -5, 0, 0, 0, 0, 0, 0);
		gl.glDisable(GL10.GL_DITHER);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT|GL10.GL_DEPTH_BUFFER_BIT);
		
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		
		GLU.gluLookAt(gl, 0, 0, -10, 0, 0, 0, 0, 2, 0);
		
		switch (ris){
			case 0: 
				/*for (int j=0; j<krogle.length; j++){
	    			risik(gl,krogle[j]);
	    			}
				 */
				break;
			
			case 1: 
				for (int j=0; j<krog.length; j++){
					risikrog(gl,krog[j]);
				}
				break;
			
			case 2: 
				risit(gl);
				break;
			
			case 3: 
				risic(gl);
				break;
		}
	   
		
	   
	   
		
		
		
	    
	    
	}
	

		
	

	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// TODO Auto-generated method stub
		hei=height;
		wi=width;
		gl.glViewport(0, 0, width, height) ;
		if (height==0)
			height=1;
		float ratio=(float)width/height;
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glFrustumf(-ratio, ratio, -1, .5f, 1, 30);
		
	}

	

	
	private void risikrog(GL10 gl, krog h){
		gl.glLoadIdentity();
		GLU.gluLookAt(gl, 0, 0, -10, 0, 0, 0, 0, 2, 0);
		
		
		if (h.vn>20)
			h.vz *=-1; 
		if (h.vn<0)
			h.vz *=-1;
		h.vn+=h.vz*.05f;
		
		if (h.gd>(hei/100))
			h.vy*=-1;
		if (h.gd<(-hei/50))
			h.vy*=-1;
		h.gd+=h.vy*.05f;
		
		if (h.ld>(wi/50))
			h.vx*=-1;
		if (h.ld<(-wi/50))
			h.vx*=-1;
		h.ld+=h.vx*.05f;
		nastopilTrk(krog,h);
	    gl.glTranslatef(h.ld, h.gd, h.vn);
		//gl.glTranslatef(a, b, d);
		Long time = SystemClock.uptimeMillis()%40000L;
	  	kot=h.vy*h.vx*h.vz*.08f*(time);
	   // gl.glRotatef(kot, 1f, 1f, .0f);
		h.draw(gl);
	}
	
	private void nastopilTrk(krog[] krog, krog x) {
		// TODO Auto-generated method stub
		for (int i=0; i<krog.length; i++){
			if (razdalja(krog[i], x)<=(krog[i].ra+x.ra)){
				if ((x.vx<=0 && krog[i].vx>0)||(x.vx>0 && krog[i].vx<0)){
					//trk v x smeri
					double v;
					v=(((x.vx*(x.m-krog[i].m))+2*krog[i].m*krog[i].vx)/(x.m+krog[i].m ));
					x.vx=(float) v;
				}
				if ((x.vy<=0 && krog[i].vy>0)||(x.vy>0 && krog[i].vy<0)){
					//trk v y smeri
					double v;
					v=(((x.vy*(x.m-krog[i].m))+2*krog[i].m*krog[i].vy)/(x.m+krog[i].m ));
					x.vy=(float) v;
				}
				if ((x.vz<=0 && krog[i].vz>0)||(x.vz>0 && krog[i].vz<0)){
					//trk v z smeri
					double v;
					v=(((x.vz*(x.m-krog[i].m))+2*krog[i].m*krog[i].vz)/(x.m+krog[i].m ));
					x.vz=(float) v;
				}
				
			}
			if (razdalja(krog[i], x)<=((krog[i].ra+x.ra)/2)){
				krog[i].gd+=((krog[i].ra+x.ra)/2);
				krog[i].ld+=((krog[i].ra+x.ra)/2);
				krog[i].vn+=((krog[i].ra+x.ra)/2);
				x.gd-=((krog[i].ra+x.ra)/2);
				x.ld-=((krog[i].ra+x.ra)/2);
				x.vn-=((krog[i].ra+x.ra)/2);
				
			}
			
		}
		
		
	}


	private float razdalja(krog k, krog m) {
		// TODO Auto-generated method stub
		float rezultat;
		double x;
		double y;
		double z;
		y=(k.gd-m.gd);
		x=(k.ld-m.ld);
		z=(k.vn-m.vn);
		rezultat=(float) (Math.sqrt((y*y)+(x*x)+(z*z)));
		return rezultat;
	}

}
