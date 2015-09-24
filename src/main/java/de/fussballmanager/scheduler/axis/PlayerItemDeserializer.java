package de.fussballmanager.scheduler.axis;

import java.util.Vector;

import javax.xml.namespace.QName;

import org.apache.axis.AxisFault;
import org.apache.axis.encoding.DeserializationContext;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Target;
import org.apache.axis.message.MessageElement;
import org.apache.axis.message.SOAPHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class PlayerItemDeserializer extends SOAPHandler implements Deserializer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4265835088823503028L;

	Vector<Target> registeredTargets = new Vector<Target>();
	QName qName = null;
	Object value=null;
	long resultToken;
	
	public String getMechanismType() {
		return "{http://schemas.xmlsoap.org/soap/encoding/}Struct";
	}

	public void setValue(Object value, Object hint) throws SAXException {
		setValue(value);
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Object getValue(Object hint) {
		return getValue();
	}

	public void setChildValue(Object value, Object hint) throws SAXException {
		
	}

	public void setDefaultType(QName qName) {
		this.qName = qName;
	}

	public QName getDefaultType() {
		return qName;
	}

	public void registerValueTarget(Target target) {
		registeredTargets.add(target);
	}

	public Vector getValueTargets() {
		return registeredTargets;
	}

	public void removeValueTargets() {
		
	}

	public void moveValueTargets(Deserializer other) {
	}

	public boolean componentsReady() {
		return false;
	}

	public void valueComplete() throws SAXException {
	}

	public void onStartElement(String namespace, String localName,
			String prefix, Attributes attributes, DeserializationContext context)
			throws SAXException {
		return;
	}

	public void onEndElement(String namespace, String localName,
			DeserializationContext context) throws SAXException {
	}

	
	@Override
	public void startElement(String namespace, String localName, String prefix,
			Attributes attributes, DeserializationContext context)
			throws SAXException {
		setValue(new PlayerItem());
		super.startElement(namespace, localName, prefix, attributes, context);
	}

	@Override
	public MessageElement makeNewElement(String namespace, String localName,
			String prefix, Attributes attributes, DeserializationContext context)
			throws AxisFault {
		setValue(new PlayerItem());
		return super.makeNewElement(namespace, localName, prefix, attributes, context);
	}

	@Override
	public void endElement(String namespace, String localName,
			DeserializationContext context) throws SAXException {
		ResultHolder.getResult(resultToken).add(getValue());
		super.endElement(namespace, localName, context);
	}

	@Override
	public SOAPHandler onStartChild(String namespace, String localName,
			String prefix, Attributes attributes, DeserializationContext context)
			throws SAXException {
	
		if(context.getCurElement().getChildren()!=null && !context.getCurElement().getChildren().isEmpty()){
		if(localName.equals("id")){
				((PlayerItem)getValue()).setId(Integer.valueOf(context.getCurElement().getChildren().get(0).toString()));
		}
		if(localName.equals("name")){
				((PlayerItem)getValue()).setName(context.getCurElement().getChildren().get(0).toString());
		}
		if(localName.equals("points")){
				((PlayerItem)getValue()).setPoints(Integer.valueOf(context.getCurElement().getChildren().get(0).toString()));
		}
		if(localName.equals("clubid")){
				((PlayerItem)getValue()).setClubid(Integer.valueOf(context.getCurElement().getChildren().get(0).toString()));
		}
		if(localName.equals("quote")){
				((PlayerItem)getValue()).setQuote(Integer.valueOf(context.getCurElement().getChildren().get(0).toString()));
		}
		if(localName.equals("status")){
				((PlayerItem)getValue()).setStatus(context.getCurElement().getChildren().get(0).toString());
		}
		if(localName.equals("status_info")){
				((PlayerItem)getValue()).setStatus_info(context.getCurElement().getChildren().get(0).toString());
		}
		if(localName.equals("position")){
				((PlayerItem)getValue()).setPosition(context.getCurElement().getChildren().get(0).toString());
		}
		if(localName.equals("rankedgamesnumber")){
				((PlayerItem)getValue()).setRankedgamesnumber(context.getCurElement().getChildren().get(0).toString());
			}
		}
		
		
		SOAPHandler temp= super.onStartChild(namespace, localName, prefix, attributes, context);
		return temp;
	}

	@Override
	public void onEndChild(String namespace, String localName,
			DeserializationContext context) throws SAXException {
		super.onEndChild(namespace, localName, context);
	}

	@Override
	public void characters(char[] chars, int start, int end)
			throws SAXException {
		super.characters(chars, start, end);
	}

	public PlayerItemDeserializer() {
		super();
	}

	public PlayerItemDeserializer(MessageElement[] elements, int index) {
		super(elements, index);
	}

	public void setResultToken(long resultToken) {
		// TODO Auto-generated method stub
		this.resultToken = resultToken;
	}

	
}
