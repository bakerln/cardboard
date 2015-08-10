package com.android.opengl;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_LINES;
import static android.opengl.GLES20.GL_POINTS;
import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform4f;
import static android.opengl.GLES20.glUniform4fv;
import static android.opengl.GLES20.glUniformMatrix4fv;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glVertexAttribPointer;
import static android.opengl.GLES20.glViewport;
import static android.opengl.Matrix.multiplyMM;
import static android.opengl.Matrix.orthoM;
import static android.opengl.Matrix.perspectiveM;
import static android.opengl.Matrix.rotateM;
import static android.opengl.Matrix.setIdentityM;
import static android.opengl.Matrix.setLookAtM;
import static android.opengl.Matrix.translateM;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.text.Normalizer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.android.opengl.object.Mallet;
import com.android.opengl.object.Puck;
import com.android.opengl.object.Table;
import com.android.opengl.programs.ColorShaderProgram;
import com.android.opengl.programs.OtherColorShaderProgram;
import com.android.opengl.util.Geometry.Point;
import com.android.opengl.util.ShaderHelper;
import com.android.opengl.util.TextResourceReader;
import com.android.opengl.util.Geometry;

import android.R.integer;
import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;

public class OpenGLProjectRenderer implements Renderer{
	private final Context context;
	private int program;

	private final float[] projectionMatrix = new float[16];
	private final float[] modelMatrix = new float[16];
	
	private final float[] viewMatrix = new float[16];
	private final float[] viewProjectionMatrix = new float[16];
	private final float[] modelViewProjectionMatrix = new float[16];
	
	private Table table;
	private Puck puck;
	private Mallet mallet;
	
	//追踪木槌的当前位置
	private boolean malletPressed = false;
	private Point blueMalletPosition;
	
	private ColorShaderProgram colorProgram;
	private OtherColorShaderProgram otherColorProgram;
	
	public OpenGLProjectRenderer(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// TODO Auto-generated method stub
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		table = new Table();
		colorProgram = new ColorShaderProgram(context);
		otherColorProgram = new OtherColorShaderProgram(context);
		mallet = new Mallet(0.08f, 0.15f, 32);
		puck = new Puck(0.06f, 0.02f, 32);
		blueMalletPosition = new Point(0f, mallet.height/2f, 0.4f);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// TODO Auto-generated method stub
		glViewport(0, 0, width, height);
//		final float[] temp = new float[16];
		perspectiveM(projectionMatrix, 0, 45, (float)width/(float)height, 1f, 10f);
		setLookAtM(viewMatrix, 0, 0f, 1.2f, 2.2f, 0f, 0f, 0f, 0f, 1f, 0f);
		
//		setIdentityM(modelMatrix, 0);
//		translateM(modelMatrix, 0, 0f, 0f, -2.5f);
//		rotateM(modelMatrix, 0, -60f, 1f, 0f, 0f);
//	//	最后传入矩阵
//		multiplyMM(temp, 0, projectionMatrix, 0, modelMatrix, 0);
//		System.arraycopy(temp, 0, projectionMatrix, 0, temp.length);
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		// TODO Auto-generated method stub
		glClear(GL_COLOR_BUFFER_BIT);
		multiplyMM(viewProjectionMatrix, 0, projectionMatrix, 0, viewMatrix, 0);
		
		positionTableInScene();
		colorProgram.useProgram();
		colorProgram.setUniforms(modelViewProjectionMatrix);
		table.bindData(colorProgram);
		table.draw();
		
		positionObjectInScene(0f,mallet.height / 2f, -5f);
		otherColorProgram.useProgram();
		otherColorProgram.setUniforms(modelViewProjectionMatrix, 1f, 0f, 0f);
		mallet.bindData(otherColorProgram);
		mallet.draw();
		
		positionObjectInScene(0.1f, mallet.height / 2f, 0.8f);
		otherColorProgram.setUniforms(modelViewProjectionMatrix, 0f, 0f, 1f);
		mallet.draw();
		
		positionObjectInScene(0f, puck.height / 2f, 0f);
		otherColorProgram.useProgram();
		otherColorProgram.setUniforms(modelViewProjectionMatrix, 0f, 0f, 1f);
		puck.bindData(otherColorProgram);
		puck.draw();
	}
	private void positionTableInScene(){
		setIdentityM(modelMatrix, 0);
		rotateM(modelMatrix, 0, -90f, 1f, 0f, 0f);
		multiplyMM(modelViewProjectionMatrix, 0
				, viewProjectionMatrix, 0
				, modelMatrix, 0);
	}
	private void positionObjectInScene(float x, float y, float z){
		setIdentityM(modelMatrix, 0);
		translateM(modelMatrix, 0, x, y, z);
		multiplyMM(modelViewProjectionMatrix, 0
				, viewProjectionMatrix, 0
				, modelMatrix, 0);
		
	}
	
//	public void handleTouchPress(float normalizedX, float normalizedY){
//		Ray ray = convertNormalized2DPointToRay(normalizedX,normalizedY);
//		
//		//now test if this ray intersects with the mallet by creating a
//		//bounding sphere that wraps the mallet.
//		Sphere malletBoundingShpere = new Sphere(new Point(
//				blueMalletPosition.x, 
//				blueMalletPosition.y, 
//				blueMalletPosition.z),
//				mallet.height / 2f);
//		
//		malletPressed = Geometry.intersects(malletBoundingShpere,ray);
//	}
//	public void handleTouchDrag(float normalizedX, float normalizedY){
//		
//	}
}
