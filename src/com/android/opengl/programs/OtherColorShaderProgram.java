package com.android.opengl.programs;

import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform4f;
import static android.opengl.GLES20.glUniformMatrix4fv;

import com.android.opengl.R;

import android.content.Context;

public class OtherColorShaderProgram extends ShaderProgram{
	private final int u_MatrixLocation;
	private final int u_ColorLocation;
	
	private final int a_PositionLoacation;

	public OtherColorShaderProgram(Context context) {
		super(context, R.raw.vertex_shader
				, R.raw.fragment_shader);
		// TODO Auto-generated constructor stub
		u_MatrixLocation = glGetUniformLocation(program, U_MATRIX);
		u_ColorLocation = glGetUniformLocation(program, U_COLOR);
		
		a_PositionLoacation = glGetAttribLocation(program, A_POSITION);
	}
	public void setUniforms(float[] matrix, float r, float g, float b){
		glUniformMatrix4fv(u_MatrixLocation, 1, false, matrix,0);
		glUniform4f(u_ColorLocation, r, g, b, 1f);
	}
	public int getPositionAttributeLocation(){
		return a_PositionLoacation;
	}
}
