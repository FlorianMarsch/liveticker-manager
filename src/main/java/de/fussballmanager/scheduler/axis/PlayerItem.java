package de.fussballmanager.scheduler.axis;

public class PlayerItem implements Comparable<PlayerItem>{
	
	int id;
	String name;
	int points;
	int clubid;
	int quote;
	String status;
	String status_info;
	String position;
	String rankedgamesnumber;

	//ToString
	

	@Override
	public String toString() {
		return "PlayerItem [id="
				+ id
				+ ", "
				+ (name != null ? "name=" + name + ", " : "")
				+ "points="
				+ points
				+ ", clubid="
				+ clubid
				+ ", quote="
				+ quote
				+ ", "
				+ (status != null ? "status=" + status + ", " : "")
				+ (status_info != null ? "status_info=" + status_info + ", "
						: "")
				+ (position != null ? "position=" + position + ", " : "")
				+ (rankedgamesnumber != null ? "rankedgamesnumber="
						+ rankedgamesnumber : "") + "]";
	}

	// HashCode + Equals
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + clubid;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + points;
		result = prime * result
				+ ((position == null) ? 0 : position.hashCode());
		result = prime * result + quote;
		result = prime
				* result
				+ ((rankedgamesnumber == null) ? 0 : rankedgamesnumber
						.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result
				+ ((status_info == null) ? 0 : status_info.hashCode());
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
		PlayerItem other = (PlayerItem) obj;
		if (clubid != other.clubid)
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (points != other.points)
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		if (quote != other.quote)
			return false;
		if (rankedgamesnumber == null) {
			if (other.rankedgamesnumber != null)
				return false;
		} else if (!rankedgamesnumber.equals(other.rankedgamesnumber))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (status_info == null) {
			if (other.status_info != null)
				return false;
		} else if (!status_info.equals(other.status_info))
			return false;
		return true;
	}

	// Getter & Setter
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getClubid() {
		return clubid;
	}

	public void setClubid(int clubid) {
		this.clubid = clubid;
	}

	public int getQuote() {
		return quote;
	}

	public void setQuote(int quote) {
		this.quote = quote;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus_info() {
		return status_info;
	}

	public void setStatus_info(String status_info) {
		this.status_info = status_info;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getRankedgamesnumber() {
		return rankedgamesnumber;
	}

	public void setRankedgamesnumber(String rankedgamesnumber) {
		this.rankedgamesnumber = rankedgamesnumber;
	}

	public int compareTo(PlayerItem o) {
		// TODO Auto-generated method stub
		return getPricePerPoint().compareTo(o.getPricePerPoint());
	}

	public Double getPricePerPoint(){
		return ((double)getQuote() / (double)getPoints());
	}
	
}
