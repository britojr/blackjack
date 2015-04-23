package sukhwinder.chat.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.Enumeration;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import sukhwinder.chat.*;

/** Allows users to add new rooms.
* At server startup this servlet is initialised.
* @author Sukhwinder Singh
*/
public class ManageChatServlet extends HttpServlet
{
	ChatRoomList rooms = new ChatRoomList();
	Properties props = null;
	/** Reads chat.properties file creates an object of ChatRoomList and stores it in ServletContext
	*/
	/*Override: O metodo init e chamado uma vez na inicializacao*/
	public void init() throws ServletException
	{
		try
		{
			/*(?)Pra que inicia com vazia?*/
			String path = "";
			/*(?)O getInitParameter pega os valores do web.xml
			o path sera: /WEB-INF/chat.properties*/
			path = "/WEB-INF/"+getServletContext().getInitParameter("chatpropertyfile");
			String realPath;
			/*getRealPath: caminho absoluto do chat.properties*/
			realPath = getServletContext().getRealPath(path);
			
			if (realPath != null)
			{
				/*(?)le o arquivo chat.properties, pega as salas e descricoes dele e coloca la lista rooms
				e por isso que desde o inicio ja existe as salas (e tbm a StartUp)
				...Pena que le de um jeito bem estranho... (pesquisar a classe Properties depois)*/
				InputStream fis = new FileInputStream(realPath);
				props = new Properties();
				props.load(fis);
				Enumeration keys = props.keys();
				while (keys.hasMoreElements())
				{
					String roomName = (String)keys.nextElement();
					String roomDescr = (String)props.getProperty(roomName);
					addNewRoom(rooms, roomName, roomDescr);
				}
				fis.close();
				/*(!)Coloca la no servletContext com o nome "chatroomlist", entao ela nao vem do alem!*/
				getServletContext().setAttribute("chatroomlist", rooms);
				System.err.println("Room List Created");
			}
			else
			{
				System.out.println("Unable to get realpath of chatproperty file " + path + ".\nCheck that application war file is expanded and file can be read.\nChat application won't work.");
			}
		}
		catch(FileNotFoundException fnfe)
		{
			System.err.println("Properites file not found:" + fnfe.getMessage());
		}
		catch(IOException ioe)
		{
			System.out.print("Unable to load Properties File: " + ioe.getMessage());
		}		
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		/*(?) O que que isso faz alem de imprimir coisas inuteis?*/
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("Room List Created");
		out.close();
	}
	
	/**	Allows users to add new rooms after performing minimum validation.
	* Also saves information to chat.properties files if required by initialization parameter <code>saveRooms</code>.
	*/
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String roomName = request.getParameter("rn");
		String roomDescr = request.getParameter("rd");
		if (roomName == null || (roomName = roomName.trim()).length() == 0 || roomDescr == null || (roomDescr = roomDescr.trim()).length() == 0)
		{
			request.setAttribute("error", "Please specify the room name and room description");
			/*(?) WTF?*/
			getServletContext().getRequestDispatcher("/addRoom.jsp").forward(request, response);
			return;

		}
		else if (roomName != null && roomName.indexOf(" ") != -1)
		{
			request.setAttribute("error", "Room Name cannot contain spaces");
			getServletContext().getRequestDispatcher("/addRoom.jsp").forward(request, response);
			return;
		}
		try
		{
			if (rooms != null)
			{
				addNewRoom(rooms, roomName, roomDescr);
			}
			
			/*le do web.xml*/
			String s = getServletContext().getInitParameter("saveRooms");
			boolean save = false;
			/*se for true, save recebe true
			(?)praque essa frescura, porque nao atrubui true logo?*/
			if (s != null && "true".equals(s) )
			{
				Boolean b = Boolean.valueOf(s);
				save = b.booleanValue();
			}
			/*se save = true, salva no properties*/
			if (save)
			{
				if (props != null)
				{
					props.put(roomName, roomDescr);
					String path = "/WEB-INF/"+getServletContext().getInitParameter("chatpropertyfile");
					String realPath = getServletContext().getRealPath(path);
					OutputStream fos = new FileOutputStream(realPath);
					props.store(fos, "List of Rooms");
					fos.close();	
				}
				else
				{
					response.setContentType("text/html");
					PrintWriter out = response.getWriter();
					out.println("Properties are null");
				}
			}
			response.sendRedirect(request.getContextPath() + "/listrooms.jsp");
		}
		catch (Exception e)
		{
				System.err.println("Exception: " + e.getMessage());
		}		
	}
	/**
	* Adds a new Room to ChatRoomList object and saves it to chat.properties file if required.
	*/
	public void addNewRoom(ChatRoomList list, String roomName, String roomDescr)
	{
		/*(?)getInitParameter sempre le alguma coisa do web.xml
		e se o xml tiver outro nome?*/
		String s = getServletContext().getInitParameter("maxNoOfMessages");
		int maxMessages = 25;
		if (s != null)
		{
			try
			{
				maxMessages = Integer.parseInt(s);
			}
			catch (NumberFormatException nfe)
			{
				
			}
		}
		ChatRoom room = new ChatRoom(roomName, roomDescr);
		room.setMaximumNoOfMessages(maxMessages);
		rooms.addRoom(room);		
	}

	/** Called when servlet is being destroyed */

	public void destroy()
	{
		System.err.println("Destroying all rooms");
	}
}