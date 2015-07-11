import generated.Date;
import generated.News;
import generated.Region;
import generated.Regions;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.xml.crypto.Data;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

public class Parser {

	private String url;

	Parser(String UrL) {
		this.url = UrL;

	}

	private News newsParse(String link) { //DA ASNEIRAS NOS VIDEOS
		News news=new News();

		try {
			Document doc = Jsoup.connect(link).timeout(5000).get();
			news.setUrl(link);
			Elements content = doc.getElementsByClass("cnn_storyarea");
			if(!content.isEmpty()){
				news.setTitle(getNewTitle(content.get(0)));
				news.setAuthor(getNewAuthor(content.get(0)));
				news.setText(getNewText(content.get(0)));

			}
			Elements side_content=doc.getElementsByClass("cnn_strylctcntr");
			if(!side_content.isEmpty()){
				news.setHighlits(getNewHighlits(side_content.get(0)));


			}
			Elements hours_content=doc.getElementsByClass("cnn_strytmstmp");
			if(!hours_content.isEmpty()){
				news.setDate(getNewDate(hours_content.get(0)));					

			}
			Elements photo_content=doc.getElementsByAttributeValueContaining("class", "cnn_stryimg640captioned");
			
			if(!photo_content.isEmpty()){
				news.setPhotos(getNewPhotos(photo_content));
				
			}
		
		/*	Elements video_content=doc.getElementsByClass("video-experience");
			if(!video_content.isEmpty()){
				news.setVideo(getNewVideo(content.get(0)));
			}	*/
					
				

			//}
		} catch (IOException e) {
			// TODO Auto-generated catch block
 		}
		return news;

		
	}

	public Region parsePage(String Nurl) { // Gets the links to the news
		Region region=new Region();
	//	int newsCount=0;
		try {
			Document doc = Jsoup.connect(Nurl).timeout(30000).get();

			System.out.println("Now geting the news");
			// Elements content=doc.getElementsByClass("cnnWOOL"); //Ideia, ir
			// apra o proximo no usar a classe para distinguir
			Elements content = doc.getElementsByClass("cnn_bulletbin");

			// Elements filtredLink=links.
			
				Elements temp = content.get(0).getElementsByTag("a");
				for (int i = 0; i < temp.size(); i++) {

					String newsLink = temp.get(i).attr("abs:href"); // "http://jsoup.org/"
					System.out.println(newsLink);
					region.getNews().add(newsParse(newsLink)); //não sei se funciona
				//	newsCount++;
					

				}

			

		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return region;
	}

	public Regions parse_other_languages() { // vai as paginas de outras linguas
		Regions regions=new Regions();
		String ids[] = new String[7];
		ids[0] = "nav-world";
		ids[1] = "nav-us";
		ids[2] = "nav-africa";
		ids[3] = "nav-asia";
		ids[4] = "nav-europe";
		ids[5] = "nav-latin-america";
		ids[6] = "nav-middle-east";
		int j;
		String absHref = null;
		Region region = null;
		for (j = 0; j < ids.length; j++) {
			
			try {
				Document doc = Jsoup.connect(url).timeout(100000).get();
				Element content = doc.getElementById(ids[j]);
				Elements links = content.getElementsByTag("a"); // a with href
				int i;
				 region=new Region();
				for (i = 0; i < links.size(); i++) {
				
					 absHref = links.get(i).attr("abs:href");
					System.out.println(absHref);
				
				
				}
				region=parsePage(absHref);
				region.setArea(ids[j]);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
			}
			regions.getRegion().add(region);

		}
		
		return regions;
	}



	private String getNewTitle(Element element) {
		if(!(element.getElementsByTag("h1")).isEmpty()){
		System.out.println("Title   "+element.getElementsByTag("h1").get(0).text());

		return element.getElementsByTag("h1").get(0).text();}
		else return "";
	}



	private String getNewHighlits(Element element) {
		String temp="";
		Elements paragrafos=element.getElementsByTag("li");
		int i;
		for(i=0;i<paragrafos.size();i++){
			temp=temp+paragrafos.get(i).text()+"\n";
		}
		
		System.out.println(temp);

		return temp;
	}

	private Date getNewDate(Element element) {
		Date date=new Date();
		String fullDate=element.text();
		System.out.println(element.text());
	
		StringTokenizer st = new StringTokenizer(fullDate);
		String day,month,year;
		month=st.nextToken().toString();
		day=st.nextToken().toString();
		day=day.replace(",", "");
		year=st.nextToken().toString();
		System.out.println(day);
		System.out.println(month);
		System.out.println(year);
		date.setMonth(month);//string
		date.setDay(Integer.valueOf(day)); //numero
		date.setYear(Integer.valueOf(year));//numero
		st.nextToken();
		st.nextToken();
		String tim=st.nextToken().toString();
		System.out.println(tim);
		date.setTime(Integer.valueOf(tim));
		return date ;
	}
	

	private String getNewAuthor(Element element) {
		if(!element.getElementsByClass("cnnByline").isEmpty()){
		System.out.println(element.getElementsByClass("cnnByline").get(0).text());
		return element.getElementsByClass("cnnByline").get(0).text();
		}
		else return "";
	}

	private String getNewText(Element element) {
		String temp="";
		Elements paragrafos=element.getElementsByTag("p");
		int i;
		for(i=0;i<paragrafos.size();i++){
			temp=temp+paragrafos.get(i).text()+"\n";
		}
		
		System.out.println(temp);

		return temp;
	}

	private String getNewPhotos(Elements elements) {
		String image="";
		for(Element imagem : elements)
		{
			Elements src = imagem.select("[src]");
			
			for (Element link : src) {
				if (link.tagName().equals("img"))
				{
					image= (link.attr("abs:src"));
				}
			}

		}
		return image;
	}

	private String getNewVideo(Element element) {
		if(element.hasAttr("cnnStryVidCont")){
		//System.out.println(element.getElementById("data-src").text());

		return element.getElementById("data-src").text();}
		else return "";
		
	}
	
	
}