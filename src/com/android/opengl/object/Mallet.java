package com.android.opengl.object;

import java.util.List;

import com.android.opengl.data.VertexArray;
import com.android.opengl.programs.ColorShaderProgram;
import com.android.opengl.programs.OtherColorShaderProgram;
import com.android.opengl.util.Geometry.Point;

public class Mallet {
	private static final int POSITION_COMPONENT_COUNT = 3;
	public final float radius;
	public final float height;
	
	private final VertexArray vertexArray;
	private final List<DrawCommand> drawList;
	
	public Mallet(float radius, float height, int numPointsAroundMallet){
		GeneratedData generatedData = ObjectBuilder.createMallet(
				new Point(0f, 0f, 0f), radius, height, numPointsAroundMallet);
		this.radius = radius;
		this.height = height;
		vertexArray = new VertexArray(generatedData.vertexData);
		drawList = generatedData.drawList;
	}
	public void bindData(OtherColorShaderProgram colorProgram){
		vertexArray.setVertexAttribPointer(
				0, colorProgram.getPositionAttributeLocation()
				, POSITION_COMPONENT_COUNT, 0);
	}
	public void draw(){
		for(DrawCommand drawCommand : drawList){
			drawCommand.draw();
		}
	}
}
