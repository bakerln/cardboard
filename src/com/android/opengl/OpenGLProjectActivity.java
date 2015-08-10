package com.android.opengl;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class OpenGLProjectActivity extends Activity {
	private GLSurfaceView glSurfaceView;
	private boolean rendererSet = false;
	
	final OpenGLProjectRenderer openGLProjectRenderer = 
			new OpenGLProjectRenderer(this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		glSurfaceView = new GLSurfaceView(this);
		glSurfaceView.setEGLContextClientVersion(2);
		glSurfaceView.setRenderer(openGLProjectRenderer);
		rendererSet = true;
//		glSurfaceView.setOnTouchListener(new OnTouchListener(){
//			public boolean onTouch(View v,MotionEvent event){
//				if(event != null){
//					//Convert touch coordinates into normalized device
//					//coordinates,keeping in mind that Android's Y
//					//coordinates are inverted.
//					final float normalizedX = 
//							(event.getX() / (float) v.getWidth()) *2 -1;
//					final float normalizedY = 
//							-((event.getY() / (float) v.getHeight()) *2 -1);
//					if (event.getAction() == MotionEvent.ACTION_DOWN) {
//						glSurfaceView.queueEvent(new Runnable(){
//							public void run(){
//								openGLProjectRenderer.handleTouchPress(
//										normalizedX,normalizedY);
//							}
//						});
//					} else if(event.getAction() == MotionEvent.ACTION_MOVE){
//						glSurfaceView.queueEvent(new Runnable() {
//							
//							@Override
//							public void run() {
//								// TODO Auto-generated method stub
//								openGLProjectRenderer.handleTouchDrag(
//										normalizedX,normalizedY);
//							}
//						});
//					}
//						return true;
//					}else {
//						return false;
//				}
//			}
//		});
		setContentView(glSurfaceView);
	}

	
}
