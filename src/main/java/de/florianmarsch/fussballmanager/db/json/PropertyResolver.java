package de.florianmarsch.fussballmanager.db.json;

public interface PropertyResolver {

	public Object resolve(Object aValue);

	public void setBinding(BindContext bindContext);
}
