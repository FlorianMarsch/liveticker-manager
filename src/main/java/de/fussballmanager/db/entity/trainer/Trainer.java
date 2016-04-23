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
	
	@Column
	private Boolean fake = Boolean.FALSE;

	@Column
	private String imageUrl;
	
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Boolean getFake() {
		return fake;
	}

	public void setFake(Boolean fake) {
		this.fake = fake;
	}

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

	@Override
	public String getDisplayValue() {
		return name;
	}
	
	
	
}
