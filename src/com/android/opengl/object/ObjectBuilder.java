package com.android.opengl.object;

import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.GL_TRIANGLE_STRIP;
import static android.opengl.GLES20.glDrawArrays;

import java.net.InterfaceAddress;
import java.util.ArrayList;
import java.util.List;

import android.util.FloatMath;

import com.android.opengl.util.Geometry.Circle;
import com.android.opengl.util.Geometry.Cylinder;
import com.android.opengl.util.Geometry.Point;

interface DrawCommand{
	void draw();
}
class GeneratedData{
	final float[] vertexData;
	final List<DrawCommand> drawList;
	
	public GeneratedData(float[] vertexData, List<DrawCommand> drawList) {
		// TODO Auto-generated constructor stub
		this.vertexData = vertexData;
		this.drawList = drawList;
	}
}

public class ObjectBuilder {
	
	private static final int FLOATS_PER_VERTEX = 3;
	private final float[] vertexData;
	private final List<DrawCommand> drawList = new ArrayList<DrawCommand>();
	private int offset = 0;
	
	
	public ObjectBuilder(int sizeInVertices){
		vertexData = new float[sizeInVertices * FLOATS_PER_VERTEX];
	}
	
	//圆柱体顶部顶点数量
	private static int sizeOfCircleInVertices(int numPoints){
		return 1 + (numPoints + 1); 
	}
	
	//圆柱体侧面顶点数量
	private static int sizeOfOpenCylinderInVertices(int numPoints){
		return (numPoints + 1) * 2;
	}
	
	private GeneratedData build(){
		return new GeneratedData(vertexData, drawList);
	}
	
	static GeneratedData createPuck(Cylinder puck,int numPoints){
		int size = sizeOfCircleInVertices(numPoints)
				+ sizeOfOpenCylinderInVertices(numPoints);
		//create vertexData
		ObjectBuilder builder = new ObjectBuilder(size);
		
		Circle puckTop = new Circle(
				puck.center.translateY(puck.height /2f)
				, puck.radius);
		builder.appendCircle(puckTop, numPoints);
		builder.appendOpenCylinder(puck,numPoints);
		return builder.build();
	}
	static GeneratedData createMallet(Point center, float radius, float height, int numPoints){
		int size = sizeOfCircleInVertices(numPoints) * 2
				+sizeOfOpenCylinderInVertices(numPoints) * 2;
		ObjectBuilder builder = new ObjectBuilder(size);
		
		float baseHeight = height * 0.25f;
		Circle baseCircle = new Circle(center.translateY(-baseHeight), radius);
		Cylinder baseCylinder = new Cylinder(
				baseCircle.center.translateY(-baseHeight /2f), radius, baseHeight);
		builder.appendCircle(baseCircle, numPoints);
		builder.appendOpenCylinder(baseCylinder, numPoints);
		
		float handleHeight = height * 0.75f;
		float handleRadius = radius / 3f;
		
		Circle handleCircle = new Circle(center.translateY(height * 0.5f), 
				handleRadius);
		Cylinder handleCylinder = new Cylinder(handleCircle.center.translateY(-handleHeight /2f)
				, handleRadius, handleHeight);
		builder.appendCircle(handleCircle, numPoints);
		builder.appendOpenCylinder(handleCylinder, numPoints);
		return builder.build();
	}
	
	private void appendCircle(Circle circle, int numPoints){
		//计算偏移量
		final int startVertex = offset / FLOATS_PER_VERTEX;
		//计算长度
		final int numVertices = sizeOfCircleInVertices(numPoints);
		
		//Center Point of fan
		vertexData[offset++] = circle.center.x;
		vertexData[offset++] = circle.center.y;
		vertexData[offset++] = circle.center.z;
		//Fan around center point
		for (int i = 0; i <= numPoints; i++) {
			float angleInRadians =
					((float) i /(float) numPoints)
					*((float) Math.PI * 2f);
			vertexData[offset++] =
					circle.center.x
					+ circle.radius * FloatMath.cos(angleInRadians);
			vertexData[offset++] = circle.center.y;
			vertexData[offset++] = circle.center.z
					+ circle.radius * FloatMath.sin(angleInRadians); 
			};
			
		drawList.add(new DrawCommand() {
			@Override
			public void draw() {
				// TODO Auto-generated method stub
				glDrawArrays(GL_TRIANGLE_FAN, startVertex, numVertices);
			}
		});
	}
	
	
	private void appendOpenCylinder(Cylinder cylinder, int numPoints){
		final int startVertex = offset /FLOATS_PER_VERTEX;
		final int numVertices = sizeOfOpenCylinderInVertices(numPoints); 
		final float yStart = cylinder.center.y - (cylinder.height / 2f);
		final float yEnd = cylinder.center.y + (cylinder.height / 2f);
		for (int i = 0; i <= numPoints; i++){
			float angleInRadians = 
					((float) i /(float) numPoints)
					* ((float) Math.PI * 2f);
			float xPosition = 
					cylinder.center.x
					+ cylinder.radius * FloatMath.cos(angleInRadians);
			float zPosition = 
					cylinder.center.z
					+ cylinder.radius * FloatMath.sin(angleInRadians);
			vertexData[offset++] = xPosition;
			vertexData[offset++] = yStart;
			vertexData[offset++] = zPosition;
			vertexData[offset++] = xPosition;
			vertexData[offset++] = yEnd;
			vertexData[offset++] = zPosition;
		};
		drawList.add(new DrawCommand() {
			@Override
			public void draw() {
				// TODO Auto-generated method stub
				glDrawArrays(GL_TRIANGLE_STRIP, startVertex, numVertices);
			}
		});
	}
	
	
}
