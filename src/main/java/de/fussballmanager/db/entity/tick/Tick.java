package de.fussballmanager.db.entity.tick;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Tick {

	@Id
	@Column(length=36)
	private String id = UUID.randomUUID().toString();
	
	@Column
	private Long time = System.currentTimeMillis();

	@Column(length=34)
	private String schemaName = System.getenv("SCHEME");
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public String getSchemaName() {
		return schemaName;
	}

	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
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
		Tick other = (Tick) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Tick [id=" + id + ", time=" + time + "]";
	}
	
}
