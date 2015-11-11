package de.fussballmanager.db.json;

import java.util.HashMap;
import java.util.Map;

public class BindContext {

	private Map<String,PropertyResolver> bindedHandler = new HashMap<String,PropertyResolver>();
	
	public void bind(String aRoot,PropertyResolver aRequestHandler){
		bindedHandler.put(aRoot, aRequestHandler);
		aRequestHandler.setBinding(this);
	}
	
	public PropertyResolver getHandler(String aRoot){
		PropertyResolver requestHandler = bindedHandler.get(aRoot);
		if(requestHandler != null){
			return requestHandler;
		}
		return new PropertyResolver() {
			
			@Override
			public Object resolve(Object aValue) {
				return aValue;
			}

			@Override
			public void setBinding(BindContext bindContext) {
				
			}
		};
	}
}
