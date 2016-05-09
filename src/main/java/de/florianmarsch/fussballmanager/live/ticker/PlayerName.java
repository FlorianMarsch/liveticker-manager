package de.florianmarsch.fussballmanager.live.ticker;

public class PlayerName {

	private String[] nameparts;
	String name;
	public PlayerName(String aName){
		nameparts = aName.split(" ");
		name = aName;
	}
	public boolean hasAbbriviatedName(){
		return name.contains(".");
	}
	
	public String getFirstName(){
		return nameparts[0].replace(".", "");
	}
	public String getLastName(){
		return nameparts[nameparts.length - 1];
	}
	
	public boolean isSameFamily(PlayerName aBroter){
		return getLastName().equals(aBroter.getLastName());
	}
	@Override
	public String toString() {
		return name;
	}
	
}
