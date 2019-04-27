package ravnjak.nejc;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.Random;
import javax.microedition.khronos.opengles.GL10;

import android.util.Log;


public class krogla {
	
	final Random naklju = new Random();
	float vx=(float) ((double)naklju.nextInt(3000)/1000);
	float vy=(float) ((double)naklju.nextInt(3000)/1000);
	float vz=(float) ((double)naklju.nextInt(3000)/1000);
	float m=naklju.nextInt(100);
	float vn=naklju.nextFloat()*2;
	float gd=naklju.nextFloat()*2;
	float ld=naklju.nextFloat()*2;
		
	
	private FloatBuffer bufferTock;

	private ShortBuffer pBuff;
	
	public float ra;
	private float lokov;
	private float razdelkov;
	
	public krogla(float rad, float lok, float razd){
		ra=rad;
		lokov=lok;
		razdelkov=razd;
		
		
		ByteBuffer bBuff  = ByteBuffer.allocateDirect((int)((kolikoTock())*4*3));
		bBuff.order(ByteOrder.nativeOrder());
		bufferTock=bBuff.asFloatBuffer();
		ustvariTocke();
		bufferTock.position(0);
		
		ByteBuffer pbBuff  = ByteBuffer.allocateDirect((int)(kolikoIndeksov()*2));
		pbBuff.order(ByteOrder.nativeOrder());
		pBuff=pbBuff.asShortBuffer();
		ustvariIndekse();
		pBuff.position(0);
		
	}
	

	
	public void draw (GL10 gl){		
		gl.glFrontFace(GL10.GL_CW);
		gl.glCullFace(GL10.GL_CULL_FACE);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, bufferTock);
		gl.glDrawElements(GL10.GL_TRIANGLES, (int)kolikoIndeksov(), GL10.GL_UNSIGNED_SHORT, pBuff);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		
	}
	
	private float kolikoTock(){
		float res=(lokov+1)*(razdelkov+1);
		return res;
	};
	
	private float kolikoIndeksov(){
		float res=(lokov+1)*razdelkov*30;
		return res;
	};
	
	
	private void ustvariTocke(){
		double dAlfa=(Math.PI/lokov);
		double dBeta=(2*Math.PI)/razdelkov;
		double alfa=(Math.PI)/2;
		double beta=0;
		for(;alfa<=(Math.PI);alfa+=dAlfa){
			if (alfa==0.0)
				beta=2*Math.PI-dBeta/2;
			if (alfa>=(Math.PI-dAlfa/2)){
				beta=2*Math.PI-dBeta/2;
			}
			for(;beta<=(2*Math.PI);beta+=dBeta){
				float x= (float) (ra*Math.cos(beta)*Math.sin(alfa));
				float y= (float) (ra*Math.sin(beta)*Math.sin(alfa));
				float z= (float) (ra*Math.cos(alfa));
				bufferTock.put(x);
				bufferTock.put(y);
				bufferTock.put(z);
			}
			beta=0;
			//Log.d(String.valueOf(stevilo), "besedilo");
		}

	}
	private void ustvariIndekse(){
		short a=0;
		short b=1;
		short c=2;
		int st =0;
		
		
		
		//ustvarim zgornjo kupolo (na vrhu enak indeks nato navzdol se spreminjajo
		for (int i=0; i<razdelkov-1; i++){
			pBuff.put(a);
			st++;
			pBuff.put(b);
			if (c==razdelkov)
				c=1;
			pBuff.put(c);
			b++;
			c++;
		}
		//nadalnji indeksi (za ostali del krogle)		
		a=1;
		for (int j=0; j<(lokov+1); j++){
			a=(short) ((j*razdelkov)-razdelkov);
			b=(short) ((j+1)*razdelkov);
			c=(short) ((j*razdelkov)+1);
			pBuff.put(a);
			st++;
			pBuff.put(b);
			pBuff.put(c);
			short pa=a;
			b=c;
			c=(short) (a+1);
			pBuff.put(a);
			st++;
			pBuff.put(b);
			pBuff.put(c);
			for (int k=0; k<=((2*razdelkov))-4; k++){
				a++;
				c=(short) (b+1);
				pBuff.put(a);
				st++;
				pBuff.put(b);
				pBuff.put(c);
				b=c;
				c=a;
				c++;
				pBuff.put(a);
				st++;
				pBuff.put(b);
				pBuff.put(c);			
			}
			a++;
			c=(short) (b+1);
			pBuff.put(a);
			st++;
			pBuff.put(b);
			pBuff.put(c);
			b=(short) ((j+1)*razdelkov);
			c=pa;
			pBuff.put(a);
			st++;
			pBuff.put(b);
			pBuff.put(c);
			a++;
		}
		
		Log.d(String.valueOf(st), "real");
		Log.d(String.valueOf(kolikoIndeksov()), "racun");
		
		
		
	}
	
	
}
