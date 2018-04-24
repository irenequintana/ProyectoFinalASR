package asr.proyectoFinal.servicios;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import com.ibm.watson.developer_cloud.text_to_speech.v1.TextToSpeech;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.AudioFormat;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.Voice;

public class TextoVoz 
{
	public static String textToSpeech(String palabra,String savepath) 
	{  		
		TextToSpeech service = new TextToSpeech();
		service.setUsernameAndPassword("27d21424-d509-489c-bb64-817e70f7df79", "MGs3CljMQZEs");
				
		String audioName="";
		try {
			  String text = palabra;
			  InputStream in = service.synthesize(text, Voice.FR_RENEE, AudioFormat.WEBM).execute();
			  audioName = "palabra_traducida.webm";
			  System.out.println("PATH: "+savepath+"/"+audioName);
			  OutputStream out = new FileOutputStream(savepath+File.separator+audioName);
			  byte[] buffer = new byte[1024];
			  int length;
			  while ((length = in.read(buffer)) > 0) {
			    out.write(buffer, 0, length);
			  }
			  out.close();
			  in.close();
			}
			catch (Exception e) {
			  e.printStackTrace();
			}
		
		String audio = "audio/"+audioName; //out tiene que ser el audio resultado
		 
		return audio;
	}
}