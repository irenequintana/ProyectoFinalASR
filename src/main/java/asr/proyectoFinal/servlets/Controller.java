package asr.proyectoFinal.servlets;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import asr.proyectoFinal.dao.CloudantPalabraStore;
import asr.proyectoFinal.dominio.Palabra;
import asr.proyectoFinal.servicios.TextoVoz;
import asr.proyectoFinal.servicios.Traductor;
import asr.proyectoFinal.servicios.VozTexto;

/**
 * Servlet implementation class Controller
 */
@WebServlet(urlPatterns = {"/listar","/translate"})
@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB 
				maxFileSize=1024*1024*10,      // 10MB
				maxRequestSize=1024*1024*50)   // 50MB
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	CloudantPalabraStore store = new CloudantPalabraStore();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("GET");
		PrintWriter out = response.getWriter(); // Tiene que responder a listar.jsp
		out.println("<html><head><meta charset=\"ISO-8859-1\"></head><body style=\"background-color:lightblue\">");
		
		/*System.out.println(request.getServletPath());*/
		
		// En Listar quiero mostrar las palabras que ya han sido buscadas alguna vez
		// Y en Insertar quiero recoger una palabra en audio en español, transformarlo a texto, traducirlo al francés y reproducirlo en audio 
		if(request.getServletPath().equals("/listar"))
		{
				if(store.getDB() == null)
					out.println("No ha habido ninguna búsqueda todavía");
				else
					request.setAttribute("palabras", mapa);
					request.getRequestDispatcher("listar.jsp").forward(request,response);
					// Ir a listar.jsp y pasarle el mapa
					/*out.println("<b>Palabras buscadas:</b><br />" + store.getAll().toString().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\,", "</br>"));*/
		}
		//out.println("</body></html>");
		
	}
	
	String savedFile = "";
	Map<String, String> mapa = new HashMap<String, String>();

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("POST");
		PrintWriter out = response.getWriter();
		// gets absolute path of the web application
        String appPath = request.getServletContext().getRealPath("");
        // constructs path of the directory to save uploaded file
        String savePath = appPath + "audio";
        System.out.println(savePath);
        // creates the save directory if it does not exists
        File fileSaveDir = new File(savePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdir();
        }
        
        for (Part part : request.getParts()) {
        	if(part.getName().equals("audio")){
	            String fileName = extractFileName(part);
	            // refines the fileName in case it is an absolute path
	            fileName = new File(fileName).getName();
	            savedFile=savePath + File.separator + fileName;
	            part.write(savedFile);
        	}
        }
        String palabraEspañol = VozTexto.speechToText(savedFile);
        String palabraFrances = Traductor.translate(palabraEspañol);
        Palabra palabra = new Palabra();
        if(palabraFrances==null)
		{
			out.println("usage: /insertar?palabra=palabra_a_traducir");
		}
		else
		{
			if(store.getDB() == null) 
			{
				out.println(String.format("Palabra: %s", palabra));
			}
			else
			{
				if(!(palabraFrances.equals("Répétez s'il vous plaît"))) {
					palabra.setEspanol(palabraEspañol);
					palabra.setFrances(palabraFrances);
					store.persist(palabra);
				}
			    //No mostrar la palabra buscada y reproducir el audio
				//out.println(String.format("Almacenada la palabra: %s", palabra.getName()));
				//audio_a_reproducir = TextoVoz.textToSpeech(palabra);
			}
		}
        for(Palabra p : store.getAll()) {
        	mapa.put(p.getEspanol(), p.getFrances());
        }
        out.println(TextoVoz.textToSpeech(palabraFrances,savePath));
	}
	
	/**
     * Extracts file name from HTTP header content-disposition
     */
    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length()-1);
            }
        }
        return "";
    }


}
