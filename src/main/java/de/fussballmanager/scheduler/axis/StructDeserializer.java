package de.fussballmanager.scheduler.axis;

import java.util.Date;
import java.util.Iterator;

import javax.xml.rpc.encoding.Deserializer;

import org.apache.axis.encoding.DeserializerFactory;


public class StructDeserializer implements DeserializerFactory{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3649044071316857169L;
	private Long resultToken;
	
	
	public Deserializer getDeserializerAs(String arg0) {
		// TODO Auto-generated method stub
		Deserializer de = new PlayerItemDeserializer();
		((PlayerItemDeserializer)de).setResultToken(getResultToken());
		return de;
	}

	public Iterator getSupportedMechanismTypes() {
		// TODO Auto-generated method stub
		return null;
	}

	public long getResultToken() {
		if(resultToken == null){
			setResultToken(new Date().getTime());
		}
		return resultToken;
	}

	public void setResultToken(long resultToken) {
		this.resultToken = resultToken;
	}



}
