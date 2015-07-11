package servlet;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import data.UserData;
import generated.News;
import toDatabase.DbNews;
import ejb.LoginBeanRemote;
import ejb.RegisterBeanRemote;
import ejb.GetNewsBeanRemote;

	@WebServlet("/DynamicWeb")
	@MultipartConfig
	public class Register extends HttpServlet {
		private static final long serialVersionUID = 1L;
		@EJB
		RegisterBeanRemote regbean;
		@EJB
		LoginBeanRemote logbean;
		@EJB
		GetNewsBeanRemote newsbean;
	
		public void doGet(HttpServletRequest request, HttpServletResponse response) 
				throws ServletException, java.io.IOException {

			try
			{	    
				
			 
				if("registo".equals(request.getParameter("operation")))
				{
					String username=request.getParameter("un");
					String password=request.getParameter("pw");
					String name=request.getParameter("na");
					String email=request.getParameter("em");
					String resultado = "";
					
					UserData userData=new UserData(username,password,name,email);
					resultado = regbean.RetriveLogin(username);
					
					if(resultado == "no")
					{
						regbean.addNewUser(userData);
						/*user.setUser(userData);
						user.setValid(true);
						if (user.isValid())
						{
							HttpSession  session = request.getSession(true);	    
							session.setAttribute("currentSessionUser",user); 
							response.sendRedirect("estado.jsp?estado=sucesso&name="+name);
						}*/
						
						response.sendRedirect("estado.jsp?estado=sucesso&name="+name);
						System.out.println("Register - user não existe");
					}
					else
					{
						response.sendRedirect("estado.jsp?estado=insucesso"); //error page
						System.out.println("Register - user existe");
					}
				
				} //FIM IF OPERATION REGISTO
				else if("login".equals(request.getParameter("operation")))
				{
					String username=request.getParameter("un");
					String password=request.getParameter("pw");
				
					UserData userData=new UserData();
					userData = logbean.RetriveLogin(username, password);
					
					if(username.equals("admin") && password.equals("admin"))
					{
						HttpSession  session = request.getSession(true);	
						session.setAttribute("currentSessionUser","admin"); 
						response.sendRedirect("Register?operation=listusers");
					}
					else
					{
						if(userData.getLogin_name().equalsIgnoreCase("no") && userData.getPassword().equalsIgnoreCase("no"))
						{
							System.out.println("Servlet - Dados incorretos.");
							response.sendRedirect("login.jsp?estado=insucesso");
						}
						else
						{
							System.out.println("Servlet - Dados corretos.");
							HttpSession  session = request.getSession(true);	    
							session.setAttribute("currentSessionUser",userData.getName()); 
							response.sendRedirect("Register?operation=news&news=latest");
							
						}
					}
				} // FIM IF OPERATION LOGIN
				
				else  if("news".equals(request.getParameter("operation")))
				{
					ArrayList<DbNews> array = new ArrayList<DbNews>();
					if("latest".equals(request.getParameter("news")))
					{
						array = newsbean.Getlatestnews("latest");
					} else if("world".equals(request.getParameter("news")))
					{
						array = newsbean.Getlatestnews("nav-world");
					} else if("states".equals(request.getParameter("news")))
					{
						array = newsbean.Getlatestnews("nav-us");
					} else if("africa".equals(request.getParameter("news")))
					{
						array = newsbean.Getlatestnews("nav-africa");
					} else if("asia".equals(request.getParameter("news")))
					{
						array = newsbean.Getlatestnews("nav-asia");
					} else if("europe".equals(request.getParameter("news")))
					{
						array = newsbean.Getlatestnews("nav-europe");
					} else if("latin".equals(request.getParameter("news")))
					{
						array = newsbean.Getlatestnews("nav-latin-america");
					} else if("middle".equals(request.getParameter("news")))
					{
						array = newsbean.Getlatestnews("nav-middle-east");
					}
					
					int numnews = array.size();
					int perpage = 15;
					int numpages = (int) Math.ceil(numnews * 1.0 / perpage);
					ArrayList<DbNews> arrayenviar = new ArrayList<DbNews>();
					int page = 1;
			        if(request.getParameter("page") != null)
			            page = Integer.parseInt(request.getParameter("page"));
			        
			        int inicio = (page - 1) * 15;
			        for(int i=inicio;i<array.size(); i++)
					{
						if(i<inicio+15)
							arrayenviar.add(array.get(i));
					}
			        String nextJSP = "main.jsp?selected=latest";
					request.setAttribute("list", arrayenviar);
					request.setAttribute("numpages", numpages);
					request.setAttribute("current", page);
					request.getRequestDispatcher(nextJSP).forward(request, response);
			        
				} //FIM IF OPERATION NEWS
				else  if("selnew".equals(request.getParameter("operation")))
				{
					ArrayList<DbNews> array = new ArrayList<DbNews>();
					
					String chave = request.getParameter("chave");
					String type = request.getParameter("news");
					String current = request.getParameter("page");
					
					array = newsbean.Getselectednew(Long.parseLong(chave));
					
			        String nextJSP = "news.jsp?selected="+type;
					request.setAttribute("list", array);
					request.setAttribute("news", type);
					request.setAttribute("current", current);
					request.getRequestDispatcher(nextJSP).forward(request, response);
				} // FIM IF OPERATION SELNEW
				else  if("search".equals(request.getParameter("operation")))
				{
					String texto=request.getParameter("st");
					String[] words = texto.split(" ");  
					ArrayList<String> termos = new ArrayList<String>();
					
					for (String word : words)  
					{  
					   termos.add(word); 
					}  
					
					ArrayList<DbNews> array = new ArrayList<DbNews>();
					array = newsbean.Getsearchnew(termos);
					String nextJSP = "mainsearch.jsp";
					request.setAttribute("list", array);
					request.setAttribute("words", texto);
					//request.setAttribute("news", type);
					request.getRequestDispatcher(nextJSP).forward(request, response);
				}	//FIM IF OPERATION SEARCH
				else if("selnewsearch".equals(request.getParameter("operation")))
				{
					ArrayList<DbNews> array = new ArrayList<DbNews>();
					
					String chave = request.getParameter("chave");
					String termos = request.getParameter("st");
					
					array = newsbean.Getselectednew(Long.parseLong(chave));
					
			        String nextJSP = "newssearch.jsp";
					request.setAttribute("list", array);
					request.setAttribute("words", termos);
					request.getRequestDispatcher(nextJSP).forward(request, response);
				} // FIM IF OPERATION SELNEW
				else if("advancedsearch".equals(request.getParameter("operation")))
				{
					String selected = request.getParameter("type");
					
					String datepicker = "";
					if(request.getParameter("datepicker").equals(""))
					{
						Date dataatual = new Date();
						DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
						datepicker = dateFormat.format(dataatual);
					}
					else
					{
						datepicker=request.getParameter("datepicker");
					}
						
					String[] parts = datepicker.split("/");
					String mes = parts[0];
					String dia = parts[1];
					String ano = parts[2]; 
					String caixa = "";
					String[] words;
					ArrayList<String> termos = new ArrayList<String>();
					
					if(!request.getParameter("st").equals(""))
					{		
						caixa = request.getParameter("st");
						words = caixa.split(" ");  

						for (String word : words)  
						{  
							  termos.add(word); 
						}  
						System.out.println(termos.size());
						for(String app : termos)
						{
							System.out.println(app);
						}
					}

					String date = request.getParameter("date");
					
					System.out.println(selected + " " + datepicker + " " + date + " " + dia + " " + mes + " " + ano +"\n");
					System.out.println("Texto: " + caixa);
					
					ArrayList<DbNews> array = new ArrayList<DbNews>();
					array = newsbean.Getadvancedsearch(caixa, termos, selected, date, Integer.parseInt(dia), Integer.parseInt(mes), Integer.parseInt(ano));
					String nextJSP = "mainsearch.jsp";
					request.setAttribute("list", array);
					request.setAttribute("words", "teste");
					request.setAttribute("datepicker", datepicker);
					request.setAttribute("type", selected);
					request.setAttribute("st", caixa);
					request.setAttribute("date", date);
					request.getRequestDispatcher(nextJSP).forward(request, response);
				} // FIM IF OPERATION ADVANCED SEARCH
				else if("seladvsearch".equals(request.getParameter("operation")))
				{
					ArrayList<DbNews> array = new ArrayList<DbNews>();
					
					String chave = request.getParameter("chave");
					String st = request.getParameter("st");
					String datepicker = request.getParameter("datepicker");
					String date = request.getParameter("date");
					String type = request.getParameter("type");
					
					array = newsbean.Getselectednew(Long.parseLong(chave));
					
			        String nextJSP = "newssearch.jsp";
					request.setAttribute("list", array);
					request.setAttribute("st", st);
					request.setAttribute("datepicker", datepicker);
					request.setAttribute("date", date);
					request.setAttribute("type", type);
					request.getRequestDispatcher(nextJSP).forward(request, response);
				} // FIM IF OPERATION SELADVSEARCH
				else if("listusers".equals(request.getParameter("operation")))
				{
					ArrayList<UserData> array = new ArrayList<UserData>();
					
					array = logbean.Allusers();
					
			        String nextJSP = "mainadmin.jsp";
					request.setAttribute("list", array);
					request.getRequestDispatcher(nextJSP).forward(request, response);
				} // FIM IF OPERATION LISTUSERS
				else if("edit".equals(request.getParameter("operation")))
				{
					ArrayList<UserData> array = new ArrayList<UserData>();
					String loginid = request.getParameter("loginid");
					
					array = logbean.Getselecteduser(Long.parseLong(loginid));
					
			        String nextJSP = "user.jsp";
					request.setAttribute("list", array);
					request.getRequestDispatcher(nextJSP).forward(request, response);
				} // FIM IF OPERATION EDIT
				else if("updateuser".equals(request.getParameter("operation")))
				{
					ArrayList<UserData> array = new ArrayList<UserData>();
					String username=request.getParameter("un");
					String password=request.getParameter("pw");
					String name=request.getParameter("na");
					String email=request.getParameter("em");
					String loginid = request.getParameter("loginid");
					long login = Long.parseLong(loginid);
					System.out.println("password");
					logbean.Updateuser(login, name, username, email, password);
					array = logbean.Getselecteduser(Long.parseLong(loginid));

			        String nextJSP = "user.jsp";
					request.setAttribute("list", array);
					request.setAttribute("state", "sim");
					request.getRequestDispatcher(nextJSP).forward(request, response);
				} // FIM IF OPERATION UPDATEUSER
				else if("delete".equals(request.getParameter("operation")))
				{
					String loginid = request.getParameter("loginid");
					long login = Long.parseLong(loginid);
					logbean.Deleteuser(login);
					ArrayList<UserData> array = new ArrayList<UserData>();
					
					array = logbean.Allusers();
					
			        String nextJSP = "mainadmin.jsp";
					request.setAttribute("list", array);
					request.setAttribute("state", "sim");
					request.getRequestDispatcher(nextJSP).forward(request, response);
				} // FIM IF OPERATION DELETE
				else if("logout".equals(request.getParameter("operation")))
				{
					HttpSession  session = request.getSession(true);
					session.removeAttribute("currentSessionUser"); 
					
			        String nextJSP = "login.jsp";
					request.getRequestDispatcher(nextJSP).forward(request, response);
				} //FIM OPERATION LOGOUT
				else if("advancedauthor".equals(request.getParameter("operation")))
				{
					ArrayList<String> array = null; 
					array = newsbean.Getauthors();
					
			        String nextJSP = "advancedsearch.jsp";
			        request.setAttribute("list", array);
					request.getRequestDispatcher(nextJSP).forward(request, response);
				} //FIM OPERATION ADVANCED AUTHOR
				else if("searchauthor".equals(request.getParameter("operation")))
				{
					ArrayList<DbNews> array = null; 
					String author = request.getParameter("author");
					array = newsbean.Getnewsbyauthor(author);
					
					String nextJSP = "mainsearch.jsp";
					request.setAttribute("list", array);
					request.setAttribute("type", "author");
					request.setAttribute("author", author);
					request.getRequestDispatcher(nextJSP).forward(request, response);
				} //FIM OPERATION SEARCH AUTHOR
				else if("seladvauthor".equals(request.getParameter("operation")))
				{
					ArrayList<DbNews> array = new ArrayList<DbNews>();
					
					String chave = request.getParameter("chave");
					String author = request.getParameter("author");
					
					array = newsbean.Getselectednew(Long.parseLong(chave));
					
			        String nextJSP = "newssearch.jsp";
					request.setAttribute("list", array);
					request.setAttribute("author", author);
					request.setAttribute("type", "author");
					request.getRequestDispatcher(nextJSP).forward(request, response);
				} // FIM IF OPERATION SELADVSEARCH
			}
			catch (Throwable theException) 	    
			{
				System.out.println(theException); 
			}
		}
	}