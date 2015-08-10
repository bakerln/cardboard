package com.android.opengl.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.R.integer;
import android.content.Context;
import android.content.res.Resources;

public class TextResourceReader {
	public static String readTextFileFromResource(Context context
			, int resourceId){
		StringBuilder body = new StringBuilder();
		
		try{
			InputStream inputStream = 
					context.getResources().openRawResource(resourceId);
			InputStreamReader inputStreamReader = 
					new InputStreamReader(inputStream);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			
			String nextLine;
			
			while ((nextLine = bufferedReader.readLine()) != null){
				body.append(nextLine);
				body.append('\n');
			}
		}catch(IOException e){
			// TODO
			
		}catch (Resources.NotFoundException nfe) {
			// TODO: handle exception
			
		}
		return body.toString();
		
	}
}
