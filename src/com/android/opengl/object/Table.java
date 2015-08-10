package com.android.opengl.object;

import static android.opengl.GLES20.GL_LINES;
import static android.opengl.GLES20.GL_POINTS;
import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.glDrawArrays;
import static com.android.opengl.Constants.*;

import com.android.opengl.data.VertexArray;
import com.android.opengl.programs.ColorShaderProgram;

public class Table {
	private static final int POSITION_COMPONENT_COUNT = 2;
	private static final int COLOR_COMPONENT_COUNT = 3;
	private static final int STRIDE = 
			(POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) * BYTES_PER_FLOAT;
	private static final float[] VERTEX_DATA = {
		0f, 0f,         1f, 1f, 1f,
		-0.5f,-0.8f,    0.7f,0.7f,0.7f,
		0.5f,-0.8f,     0.7f,0.7f,0.7f,
		0.5f,0.8f,      0.7f,0.7f,0.7f,
		-0.5f,0.8f,     0.7f,0.7f,0.7f,
		-0.5f,-0.8f,    0.7f,0.7f,0.7f, //table
		
		-0.5f,0f,  		1f,0f,0f,
		0.5f,0f,  		1f,0f,0f,//line
		0f,-0.4f, 		0f,0f,1f,
		0f,0.4f, 		1f,0f,0f
	};
	private final VertexArray vertexArray;
	//initial
	public Table(){
		vertexArray = new VertexArray(VERTEX_DATA);
	}
	
	public void bindData(ColorShaderProgram textureProgram){
		vertexArray.setVertexAttribPointer(0
				, textureProgram.getPositionAttributeLocation()
				, POSITION_COMPONENT_COUNT, STRIDE);
		vertexArray.setVertexAttribPointer(POSITION_COMPONENT_COUNT
				, textureProgram.getColorAttributeLocation()
				, COLOR_COMPONENT_COUNT, STRIDE);
	}
	
	public void draw(){
		glDrawArrays(GL_TRIANGLE_FAN, 0, 6);
		glDrawArrays(GL_LINES, 6, 2);
//		glDrawArrays(GL_POINTS, 8, 1);
//		glDrawArrays(GL_POINTS, 9, 1);
	}
}
