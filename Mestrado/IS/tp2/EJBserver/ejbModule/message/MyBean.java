package message;

import java.io.StringReader;
import java.util.ArrayList;

import generated.Regions;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import toDatabase.DbNews;
import toDatabase.DbRegion;
import toDatabase.DbRegions;

/**
 * Message-Driven Bean implementation class for: MyBean
 *
 */
@MessageDriven(
		activationConfig = {
				@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
				@ActivationConfigProperty(propertyName = "destination", propertyValue = "topic/test")
				//@ActivationConfigProperty(propertyName = "subscriptionDurability", propertyValue = "Durable")
		},
		mappedName = "topic/Test")
public class MyBean implements MessageListener {
	@PersistenceContext(name="TestPersistence")
	EntityManager em;

	/**
	 * Default constructor. 
	 */
	public MyBean() {
	}
	/**
	 * @see MessageListener#onMessage(Message)
	 */
	public void onMessage(Message message) {
		try {
			String messageReceived=((TextMessage) message).getText();
			StreamSource streamSource = new StreamSource(new StringReader(messageReceived));
			//System.out.println("--------------------------------\n"+messageReceived);
			Regions toWrite=Marshelling.Unmarshalls(streamSource);
			
			/*if(toWrite==null){
				System.out.println("TA A FAZER UNMARSHALL MAL");
			}
			else{
				System.out.println(toWrite.getRegion().size());
			}*/
			DbRegions toPersist=transformToDB(toWrite);
			System.out.println(toPersist.getRegion().size());

			
			for(int i=0;i<toPersist.getRegion().size();i++){
				em.persist(toPersist.getRegion().get(i));
			}
			
			
			for(int i=0;i<toPersist.getRegion().size();i++){
				for(int j=0;j<toPersist.getRegion().get(i).getNews().size();j++){
					toPersist.getRegion().get(i).getNews().get(j).setRegion(toPersist.getRegion().get(i));
					em.persist(toPersist.getRegion().get(i).getNews().get(j) );
				}
			}
			
			
			
		
			
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	private DbRegions transformToDB (Regions original){
		ArrayList<DbRegion> arrayRegions= new ArrayList<DbRegion>();
		DbRegions nova= new DbRegions();
		for(int i=0;i<original.getRegion().size();i++){
			DbRegion temp= new DbRegion();
			ArrayList<DbNews> arrayTempDb= new ArrayList<DbNews>();

			temp.setArea(original.getRegion().get(i).getArea());
			for(int j=0;j<original.getRegion().get(i).getNews().size();j++){
				DbNews tempNews=new DbNews();
				tempNews.setAuthor(original.getRegion().get(i).getNews().get(j).getAuthor());
				if(original.getRegion().get(i).getNews().get(j).getDate()!=null){
					tempNews.setDay(original.getRegion().get(i).getNews().get(j).getDate().getDay());
					tempNews.setMonth(original.getRegion().get(i).getNews().get(j).getDate().getMonth());
					tempNews.setYear(original.getRegion().get(i).getNews().get(j).getDate().getYear());
					tempNews.setTime(original.getRegion().get(i).getNews().get(j).getDate().getTime());
				}
				tempNews.setHighlits(original.getRegion().get(i).getNews().get(j).getHighlits());
				tempNews.setId(original.getRegion().get(i).getNews().get(j).getId());
				tempNews.setPhotos(original.getRegion().get(i).getNews().get(j).getPhotos());
				tempNews.setTitle(original.getRegion().get(i).getNews().get(j).getTitle());
				tempNews.setUrl(original.getRegion().get(i).getNews().get(j).getUrl());
				tempNews.setText(original.getRegion().get(i).getNews().get(j).getText());
				tempNews.setVideo(original.getRegion().get(i).getNews().get(j).getVideo());
			
				arrayTempDb.add(tempNews);
			}
				temp.setNews(arrayTempDb);
				//System.out.println("Numero de noticias na região:"+(i+1)+": "+temp.getNews().size());
				arrayRegions.add(temp);

		}
		nova.setRegion(arrayRegions);

		return nova;
	}


}