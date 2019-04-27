package ravnjak.nejc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class OpenGlDiplomaActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        startActivity(new Intent("ravnjak.nejc"));
    }
    public void poskus (View v){
    	Intent myIntent=new Intent(OpenGlDiplomaActivity.this,OpenGlDiploma.class);

    	myIntent.putExtra("a", (int)1);
    	myIntent.putExtra("b", (int)2);

    	startActivity(myIntent);

    }
}