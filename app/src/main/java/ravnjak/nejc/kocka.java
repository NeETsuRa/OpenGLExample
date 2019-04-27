package ravnjak.nejc;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

public class kocka {
	float r=0f;
	float g=0f;
	float b=0f;
	float a=0f;

	private float tocke[]={
			1, 1, -1,
			1, -1, -1,
			-1, -1, -1,
			-1, 1, -1,
			1, 1, 1,
			1, -1, 1,
			-1, -1, 1,
			-1, 1, 1
	};
	
	private FloatBuffer bufferTock;
	
	private short[] index={
			3,4,0,   0,4,1,   3,0,1,
			3,7,4,   7,6,4,   7,3,6,
			3,1,2,   1,6,2,   6,3,2,
			1,4,5,   5,6,1,   6,5,4
	};
	
	private ShortBuffer pBuff;
	
	public kocka(){
		ByteBuffer bBuff  = ByteBuffer.allocateDirect(tocke.length*4);
		bBuff.order(ByteOrder.nativeOrder());
		bufferTock=bBuff.asFloatBuffer();
		bufferTock.put(tocke);
		bufferTock.position(0);
		
		ByteBuffer pbBuff  = ByteBuffer.allocateDirect(index.length*2);
		pbBuff.order(ByteOrder.nativeOrder());
		pBuff=pbBuff.asShortBuffer();
		pBuff.put(index);
		pBuff.position(0);
		
	}
	

	
	public void draw (GL10 gl){
		/*FloatBuffer global_ambient = FloatBuffer.allocate(4);
	    global_ambient.put(0.5f);
	    global_ambient.put(0.5f);
	    global_ambient.put(0.5f);
	    global_ambient.put(1.0f);

	    FloatBuffer position = FloatBuffer.allocate(4);
	    position.put(10f);
	    position.put(10f);
	    position.put(10f);
	    position.put(1f);
	    
		gl.glEnable(GL10.GL_LIGHTING);
        gl.glEnable(GL10.GL_LIGHT0);
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT_AND_DIFFUSE,global_ambient);
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, position);*/
		
		gl.glFrontFace(GL10.GL_CW);
		gl.glCullFace(GL10.GL_CULL_FACE);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, bufferTock);
		gl.glColor4f(r, g, b, a);
		gl.glDrawElements(GL10.GL_TRIANGLES, index.length, GL10.GL_UNSIGNED_SHORT, pBuff);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		
	}
	
	
}
