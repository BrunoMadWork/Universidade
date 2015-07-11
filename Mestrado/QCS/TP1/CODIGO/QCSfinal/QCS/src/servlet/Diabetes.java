package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.util.ArrayList;
 

 
public class Diabetes extends HttpServlet {
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // reading the user input
    	Voter voter=new Voter();
    	if("standard".equals(request.getParameter("operation")))
		{
    		 int field1 = Integer.parseInt(request.getParameter("field1")); //INPUTS POR ORDEM DE APARECIMENTO NA PAGINA
    		 int field2 = Integer.parseInt(request.getParameter("field2"));
    		 int field3 = Integer.parseInt(request.getParameter("field3"));
    		 int field4 = Integer.parseInt(request.getParameter("field4"));
    		 int field5 = Integer.parseInt(request.getParameter("field5"));
            
    		 
           
           System.out.println( (voter.getMealTimeInsulinDose(field1, field2, field3, field4, field5)));
           String nextJSP=("standard.jsp");
           request.setAttribute("informacao", Voter.FinalResultsInfotext);
           request.setAttribute("resultado", (voter.getMealTimeInsulinDose(field1, field2, field3, field4, field5)));
           request.getRequestDispatcher(nextJSP).forward(request, response);
         //FIM IF STANDARD
		}else if("personal".equals(request.getParameter("operation")))
		{
			int contador = Integer.parseInt(request.getParameter("contador")); //NUMERO DE TEXTBOXES "SAMPLES OF PHYSICAL ACTIVITY" CRIADAS
            int contador2 = Integer.parseInt(request.getParameter("contador2")); //NUMERO DE TEXTOBXES "SAMPLE OF DROPS IN BLOOD" CRIADAS
            
            int field1 = Integer.parseInt(request.getParameter("field1")); //INPUTS POR ORDEM DE APARECIMENTO NA PAGINA
            int field2 = Integer.parseInt(request.getParameter("field2"));
            int field3 = Integer.parseInt(request.getParameter("field3"));
            int field4 = Integer.parseInt(request.getParameter("field4"));
            int field5 = Integer.parseInt(request.getParameter("field5"));
            
            ArrayList<Integer> arrayact = new ArrayList<Integer>(); //ARRAY PARA VALORES DAS TEXTBOXES "SAMPLES OF PHYSICAL ACTIVITY"
            ArrayList<Integer> arrayblo = new ArrayList<Integer>(); //ARRAY PARA VALORES DAS TEXTBOXES "SAMPLES OF DROPS IN BLOOD"
            
            for(int i = 1; i<=contador;i++)
            {
            	String input = "activity"+i;
            	int valor= Integer.parseInt(request.getParameter(input));
            	arrayact.add(valor);
            }
            
            for(int j = 1; j<=contador2;j++)
            {
            	String input = "blood"+j;
            	int valor= Integer.parseInt(request.getParameter(input));
            	arrayblo.add(valor);
            	System.out.println("blood"+valor);
            }
            

            PrintWriter out = response.getWriter();
            out.println (
                      "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" +" +
                          "http://www.w3.org/TR/html4/loose.dtd\">\n" +
                      "<html> \n" +
                        "<head> \n" +
                          "<meta http-equiv=\"Content-Type\" content=\"text/html; " +
                            "charset=ISO-8859-1\"> \n" +
                          "<title> Crunchify.com JSP Servlet Example  </title> \n" +
                        "</head> \n" +
                        "<body> <div align='center'> \n" +
                          "<style= \"font-size=\"12px\" color='black'\"" + "\">" +
                            "Contador: " + contador + " <br> " +
                            "Contador2: " + contador2 +
                        "</font></body> \n" +
                      "</html>" 
                    ); 
            
           //converter lista para array
            System.out.println( voter.getMealTimeInsulinDose(field1, field2, field3, field4, voter.getPersonalSensitivityToInsulin(field5,buildIntArray(arrayact), buildIntArray(arrayblo))));

          
          String nextJSP=("personal.jsp");
          request.setAttribute("informacao", Voter.FinalResultsInfotext);
          request.setAttribute("resultado", (voter.getMealTimeInsulinDose(field1, field2, field3, field4, voter.getPersonalSensitivityToInsulin(field5,buildIntArray(arrayact), buildIntArray(arrayblo)))));
          request.getRequestDispatcher(nextJSP).forward(request, response);
		}else if("background".equals(request.getParameter("operation")))
		{
			int field1 = Integer.parseInt(request.getParameter("field1")); 
			
			System.out.println(voter.getBackgroundInsulinDose(field1));
			
			String nextJSP=("background.jsp");
	        request.setAttribute("informacao", Voter.FinalResultsInfotext);
	        request.setAttribute("resultado",( voter.getBackgroundInsulinDose(field1)));
	        request.getRequestDispatcher(nextJSP).forward(request, response);
			
		} else if("technical".equals(request.getParameter("operation")))
		{
			String technical = request.getParameter("information");
			String nextJSP=("technical.jsp");
	        request.setAttribute("informacao", technical);
	        request.getRequestDispatcher(nextJSP).forward(request, response);
		}
            
        }
    private int[] buildIntArray(ArrayList<Integer> integers) {
        int[] ints = new int[integers.size()];
        int i = 0;
        for (Integer n : integers) {
            ints[i++] = n;
        }
        return ints;
    }
    
}