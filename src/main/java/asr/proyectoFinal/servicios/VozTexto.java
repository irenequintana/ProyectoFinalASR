package asr.proyectoFinal.servicios;

import java.io.File;

import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.RecognizeOptions;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechResults;

public class VozTexto 
{
	public static String speechToText(String path_audio) 
	{  		
		String ext = path_audio.substring(path_audio.lastIndexOf(".")+1);
		System.out.println(path_audio);
		System.out.println(ext);
		SpeechToText service = new SpeechToText();
		service.setUsernameAndPassword("1461f74e-1571-4d9a-af22-02a6c7c417d3", "zsqNEwRoMvwv");
		
		RecognizeOptions options = new RecognizeOptions.Builder()
				  .contentType("audio/"+ext).timestamps(true)
				  .model("es-ES_BroadbandModel")
				  .interimResults(false)
				  .wordAlternativesThreshold(0.7)
				  .build();

		//audio tiene que ser "palabra_a_traducir.flac"
		SpeechResults result = service.recognize(new File(path_audio), options).execute();
		String palabra;
		if(result.getResults().size()>0)
			palabra =result.getResults().get(0).getAlternatives().get(0).getTranscript();
		else
			palabra = "Repite por favor";
		

		return palabra;
	}
}