package com.android.opengl.programs;

import static android.opengl.GLES20.glUseProgram;

import com.android.opengl.util.ShaderHelper;
import com.android.opengl.util.TextResourceReader;

import android.content.Context;

public class ShaderProgram {
	//Uniform constants
	protected static final String U_MATRIX = "u_Matrix";
	protected static final String U_COLOR = "u_Color";
	//Attribute constants
	protected static final String A_POSITION = "a_Position";
	protected static final String A_COLOR = "a_Color";
	//Shader program
	protected final int program;
	protected ShaderProgram(Context context, int vertexShaderResourceId
			,int fragmentShaderResourceId){
		program = ShaderHelper.buildProgram(
				TextResourceReader.readTextFileFromResource
				(context, vertexShaderResourceId), 
				TextResourceReader.readTextFileFromResource
				(context, fragmentShaderResourceId));
	}
	public void useProgram(){
		glUseProgram(program);
	}
}
