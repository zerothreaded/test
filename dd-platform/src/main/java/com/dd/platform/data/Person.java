package com.dd.platform.data;

import org.apache.commons.lang.builder.ToStringBuilder;

public class Person {

	// DD Identity
	private String id;

	// TODO: Add external source system identities
	
	// TheyWorkForYou ID's
	
	private Long memberId;
	
	private Long personId;
	
	// Attributes
	
	private String fullName;
	
	private String imageUrl;
	
	private String constituencyName;

	private String partyName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getConstituencyName() {
		return constituencyName;
	}

	public void setConstituencyName(String constituencyName) {
		this.constituencyName = constituencyName;
	}

	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
			.append("id", id)
			.append("member_id", memberId)
			.append("person_id", personId)
			.append("name", fullName)
			.append("name", partyName)
			.append("name", constituencyName)
			.toString();
	}


}
