package de.florianmarsch.fussballmanager.db.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractEntity {
	@Id
	@Column(length = 36)
	private String id = UUID.randomUUID().toString();

	@Column
	private Long creationTime = System.currentTimeMillis();

	@Column
	private Long lastChangedTime;

	@Column
	private Boolean persistend = Boolean.FALSE;

	@Column(length = 4)
	private String schemaName = System.getenv("SCHEME");

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Long creationTime) {
		this.creationTime = creationTime;
	}

	public String getSchemaName() {
		return schemaName;
	}

	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}

	public Long getLastChangedTime() {
		return lastChangedTime;
	}

	public void setLastChangedTime(Long lastChangedTime) {
		this.lastChangedTime = lastChangedTime;
	}

	public Boolean getPersistend() {
		return persistend;
	}

	public void setPersistend(Boolean persistend) {
		this.persistend = persistend;
	}

	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public final boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractEntity other = (AbstractEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public final String toString() {
		return id;
	}

	public abstract String getDisplayValue();
	
}
