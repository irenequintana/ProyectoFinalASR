package asr.proyectoFinal.servicios;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ibm.watson.developer_cloud.language_translator.v2.LanguageTranslator;
import com.ibm.watson.developer_cloud.language_translator.v2.model.TranslateOptions;
import com.ibm.watson.developer_cloud.language_translator.v2.model.TranslationResult; 

public class Traductor 
{
	public static String translate(String palabra) 
	{ 
		LanguageTranslator service = new LanguageTranslator(); 
		service.setUsernameAndPassword("aedf5f01-b570-4da9-89c7-a4223b6c9bd5", "7IvpuP2EPcEK"); 
		TranslateOptions translateOptions = new TranslateOptions.Builder() 
				.addText(palabra) 
				.modelId("es-fr") 
				.build(); 
		TranslationResult translationResult = service.translate(translateOptions).execute();
		System.out.println(translationResult); 
		String traduccionJSON = translationResult.toString(); 
		JsonParser parser = new JsonParser(); 
		JsonObject rootObj = parser.parse(traduccionJSON).getAsJsonObject(); 
		//String wordCount = rootObj.get("word_count").getAsString(); 
		JsonArray traducciones = rootObj.getAsJsonArray("translations"); 
		String traduccionPrimera = palabra; 
		if(traducciones.size()>0) traduccionPrimera = traducciones.get(0).getAsJsonObject().get("translation").getAsString(); 
		return traduccionPrimera;
	}
}
