package de.fussballmanager.db.entity.trainer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import de.fussballmanager.db.entity.AbstractEntity;

@Table
@Entity
public class Trainer extends AbstractEntity {

	@Column
	private String name;
	
	@Column
	private String url;
	
	@Column
	private String hashTag;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getHashTag() {
		return hashTag;
	}

	public void setHashTag(String hashTag) {
		this.hashTag = hashTag;
	}
	
	
	
}
