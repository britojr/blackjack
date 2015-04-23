package tpw.cassino;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletContext;


/**
It is used by Chat Application for listening to session events.
* @author Sukhwinder Singh
*/
public class SessionListener implements HttpSessionAttributeListener 
{
	public void attributeAdded(HttpSessionBindingEvent hsbe)
	{
		//System.out.println("Attribute has been bound");
	}

	public void attributeReplaced(HttpSessionBindingEvent hsbe)
	{
		//System.out.println("Attribute has been replaced");
	}

	/** This is the method we are interested in. It deletes a user from this list of users when his session
		expires.
	*/
	public void attributeRemoved(HttpSessionBindingEvent httpsession) 
	{   System.out.println("aki 1");
		String name = httpsession.getName();
		HttpSession session = httpsession.getSession();
		if ("nickname".equalsIgnoreCase(name))
		{   System.out.println("aki 2");
			String nickname = (String)httpsession.getValue();
			if (nickname != null)
			{   System.out.println("aki 3");
				ServletContext application = session.getServletContext();
				if (application != null)
				{ 	//Player player = player.getWatcher(nickname).addCash(1000);
					System.out.println("aki 4");
				
					//Object o = application.getAttribute("blackjackroomlist");	
					  Object o = application.getAttribute("blackjackroom");						
					if (o != null)
					{   System.out.println("aki 5");
						//BlackJackRoomList roomList = (BlackJackRoomList)o;
						BlackJackRoom room = (BlackJackRoom)o;
						//BlackJackRoom room = roomList.getRoomOf(nickname);
						if (room != null)
						{   System.out.println("aki 6");
							Object watcher = room.removeWatcher(nickname);
							if (watcher != null)
							{   System.out.println("aki 7");
								String n = ((Player)watcher).getName();
							} 
							
						} 
					}  
				}
				else
				{	 
					System.out.println("ServletContext is null");
				}					
			}
		}		
	}
}