package de.fussball.live.ticker.event;

public class Event {
	private String id;
	private String name;
	private String resolved;
	private String event;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Event [id=" + id + ", name=" + name + ", event=" + event + "]";
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getResolved() {
		return resolved;
	}
	public void setResolved(String resolved) {
		this.resolved = resolved;
	}
	
	
}
