package com.genfare.reprocess.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class DummyEntity {
	@Id
	private long id;
	private String dummy;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDummy() {
		return dummy;
	}

	public void setDummy(String dummy) {
		this.dummy = dummy;
	}

}
