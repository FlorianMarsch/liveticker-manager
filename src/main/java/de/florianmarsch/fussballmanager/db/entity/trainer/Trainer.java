package de.florianmarsch.fussballmanager.db.entity.trainer;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;

import de.florianmarsch.fussballmanager.db.entity.AbstractEntity;

@Table
@Entity
public class Trainer extends AbstractEntity {

	@Column
	private String name;
	
	@Column
	private String kaderUrl;
	
	@Column
	private String excludeKaderUrl;
	
	@Column
	private String hashTag;
	
	@Column
	private Boolean fake = Boolean.FALSE;

	@Column
	private String imageUrl;
	
	@Column
	private String twitterName;


	@ElementCollection
	@Column
	private Set<String> archivments = new HashSet<>();
	
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

	public String getKaderUrl() {
		return kaderUrl;
	}

	public void setKaderUrl(String kaderUrl) {
		this.kaderUrl = kaderUrl;
	}

	public String getExcludeKaderUrl() {
		return excludeKaderUrl;
	}

	public void setExcludeKaderUrl(String excludeKaderUrl) {
		this.excludeKaderUrl = excludeKaderUrl;
	}

	public String getHashTag() {
		return hashTag;
	}

	public void setHashTag(String hashTag) {
		this.hashTag = hashTag;
	}

	public Set<String> getArchivments() {
		return archivments;
	}

	public void setArchivments(Set<String> archivments) {
		this.archivments = archivments;
	}

	
	
	public String getTwitterName() {
		return twitterName;
	}

	public void setTwitterName(String twitterName) {
		this.twitterName = twitterName;
	}
	
	
}
