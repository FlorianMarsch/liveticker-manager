package de.florianmarsch.fussballmanager.db.entity.club;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import de.florianmarsch.fussballmanager.db.entity.AbstractEntity;

@Table
@Entity
public class Club extends AbstractEntity {

	@Column
	@NotNull
	private String name;

	@Column
	@NotNull
	private Integer externID;

	@Column
	@NotNull
	private String logoUrl;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getExternID() {
		return externID;
	}

	public void setExternID(Integer aExternID) {
		this.externID = aExternID;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	@Override
	public String getDisplayValue() {
		return name;
	}

}
