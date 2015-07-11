package client;



import java.io.File;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;

public class Marshelling {

	public static String Marshalls(ChannelSubscticion sub) {
		StringWriter sw = new StringWriter();
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(ChannelSubscticion.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(sub, sw);
		} catch (JAXBException e) {
		}
		return sw.toString();
	}

	


	public static void Marshalls(String dirFile, ChannelSubscticion sub) {
		try {
			File file = new File(dirFile);
			if (file.exists()) {
				file.delete();
			}
			JAXBContext jaxbContext = JAXBContext.newInstance(ChannelSubscticion.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(sub, file);
		} catch (JAXBException e) {
		}
	}
	
	/**
	 * Unmarshalls movies
	 * @param dirXML
	 * @return movies object
	 */
	public static Regions Unmarshalls(String dirXML) {
		Regions regions = new Regions();
		try {
			File file = new File(dirXML);
			JAXBContext jaxbContext = JAXBContext.newInstance(Regions.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			regions = (Regions) jaxbUnmarshaller.unmarshal(file);
		} catch (JAXBException e) {
		}
		return regions;
	}

	/**
	 * Unmarshalls movies
	 * @param source XML in string format
	 * @return movies object
	 */
	public static Regions Unmarshalls(Source source) {
		Regions regions = null;
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Regions.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			regions = (Regions) jaxbUnmarshaller.unmarshal(source);
		} catch (JAXBException e) {
		}
		return regions;
	}

}
