package br.com.guigasgame.gameobject.hero.state;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.guigasgame.animation.HeroAnimationsIndex;
import br.com.guigasgame.math.Vector2;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class HeroStatePropertiesFile {

	@XmlElement
	private Map<HeroAnimationsIndex, HeroStatePropertiesPrototype> statesMap;

	protected HeroStatePropertiesFile() {
		statesMap = new HashMap<>();
	}

	public static HeroStatePropertiesFile loadFromFile(String filename) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(HeroStatePropertiesFile.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		HeroStatePropertiesFile statePropertiesFile = ((HeroStatePropertiesFile) jaxbUnmarshaller
				.unmarshal(new File(filename)));

		return statePropertiesFile;
	}

	
	public Map<HeroAnimationsIndex, HeroStatePropertiesPrototype> getStatesMap()
	{
		return statesMap;
	}

	public static void newProperties() throws JAXBException {
		
		try
		{
			HeroStatePropertiesFile states = new HeroStatePropertiesFile();
			states.statesMap.put(HeroAnimationsIndex.HERO_SLIDING, new HeroStatePropertiesPrototype( new HeroStatePropertiesPrototype.ShootXml(), new HeroStatePropertiesPrototype.RopeXml(), 
					new HeroStatePropertiesPrototype.JumpXml(-10f), new Vector2(), new HeroStatePropertiesPrototype.MoveXml(20f)));
			JAXBContext context = JAXBContext
					.newInstance(HeroStatePropertiesFile.class);
			Marshaller m = context.createMarshaller(); // for pretty-print XML
														// in JAXB
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			// Write to System.out for debugging 
			m.marshal(states, System.out);

//			 Write to File 
//			 m.marshal(anim, new File("prototype.xml"));
		}
		catch
		(JAXBException e)
		{
			e.printStackTrace();
		}

	}	
	
	public static void main(String[] args) throws JAXBException 
	{
		//currentProperties();
//		newProperties();
		loadFromFile("statePrototype.xml");
	}

}
