package com.android.opengl.util;

import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_VERTEX_SHADER;
import static android.opengl.GLES20.glAttachShader;
import static android.opengl.GLES20.glCompileShader;
import static android.opengl.GLES20.glCreateProgram;
import static android.opengl.GLES20.glCreateShader;
import static android.opengl.GLES20.glDeleteProgram;
import static android.opengl.GLES20.glDeleteShader;
import static android.opengl.GLES20.glGetProgramiv;
import static android.opengl.GLES20.glGetShaderiv;
import static android.opengl.GLES20.glLinkProgram;
import static android.opengl.GLES20.glShaderSource;
import android.R.integer;
import android.util.Log;

public class ShaderHelper {
	private static final String TAG = "ShaderHelper";
	
	public static int compileVertexShader(String shaderCode){
		return compileShader(GL_VERTEX_SHADER, shaderCode);
	}
	public static int compileFragmentShader(String shaderCode){
		return compileShader(GL_FRAGMENT_SHADER, shaderCode);
	}
	
	private static int compileShader(int type, String shaderCode){
		//typeȷ����ʲô���͵���ɫ��
		final int shaderObjectId = glCreateShader(type);
		//����ɫ������ӵ���ɫ��������
		glShaderSource(shaderObjectId, shaderCode);
		//������ɫ��
		glCompileShader(shaderObjectId);
		return shaderObjectId;
	}
	
	public static int linkProgram(int vertexShaderId, int fragmentShaderId){
		final int programObjectId = glCreateProgram();
		//������ɫ�������������
		glAttachShader(programObjectId, vertexShaderId);
		glAttachShader(programObjectId, fragmentShaderId);
		//���ӳ���
		glLinkProgram(programObjectId);
		return programObjectId;
	} 
	//���ؽ���ɫ�����Ӻõ�program
	public static int buildProgram(String vertexShaderSource
			, String fragmentShaderSource){
		int program;
		int vertexShader = compileVertexShader(vertexShaderSource);
		int fragmentShader = compileFragmentShader(fragmentShaderSource);
		program = linkProgram(vertexShader, fragmentShader);
		return program;
	}
}
