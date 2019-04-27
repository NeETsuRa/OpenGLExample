package ravnjak.nejc;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

public class trikotnik {

	private float tocke[]={
			0f,1f,
			1f,-1f,
			-1f,-1f			
	};
	
	private FloatBuffer bufferTock;
	
	private short[] index={0,1,2};
	
	private ShortBuffer pBuff;
	
	public trikotnik(){
		ByteBuffer bBuff  = ByteBuffer.allocateDirect(tocke.length*40);
		bBuff.order(ByteOrder.nativeOrder());
		bufferTock=bBuff.asFloatBuffer();
		bufferTock.put(tocke);
		bufferTock.position(0);
		
		ByteBuffer pbBuff  = ByteBuffer.allocateDirect(index.length*20);
		pbBuff.order(ByteOrder.nativeOrder());
		pBuff=pbBuff.asShortBuffer();
		pBuff.put(index);
		pBuff.position(0);
		
	}
	
	public void draw (GL10 gl){
		gl.glFrontFace(GL10.GL_CW);
		gl.glCullFace(GL10.GL_CULL_FACE);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, bufferTock);
		gl.glColor4f(1f, 0f, 0f, 0f);
		gl.glDrawElements(GL10.GL_TRIANGLES, index.length, GL10.GL_UNSIGNED_SHORT, pBuff);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		
	}
	
	
}
