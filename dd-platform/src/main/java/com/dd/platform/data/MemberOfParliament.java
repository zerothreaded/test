package com.dd.platform.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MemberOfParliament {

	// DD Identity
	private String id;

	// TheyWorkForYou ID's
	
	private Long memberId;
	
	private Long personId;

	private String partyName;
	
	// Relationships

	private Person person;

	private Parliament parliament;

	private Constituency constituency;
	
	// Attributes
	private String mpUrl;

	private String imageUrl;
	
	private String imageWidth;
	
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

	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Parliament getParliament() {
		return parliament;
	}

	public void setParliament(Parliament parliament) {
		this.parliament = parliament;
	}

	public Constituency getConstituency() {
		return constituency;
	}

	public void setConstituency(Constituency constituency) {
		this.constituency = constituency;
	}

	public String getMpUrl() {
		return mpUrl;
	}

	public void setMpUrl(String mpUrl) {
		this.mpUrl = mpUrl;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getImageWidth() {
		return imageWidth;
	}

	public void setImageWidth(String imageWidth) {
		this.imageWidth = imageWidth;
	}

	public String getImageHeight() {
		return imageHeight;
	}

	public void setImageHeight(String imageHeight) {
		this.imageHeight = imageHeight;
	}

	public Date getEnteredHouse() {
		return enteredHouse;
	}

	public void setEnteredHouse(Date enteredHouse) {
		this.enteredHouse = enteredHouse;
	}

	public String getEnteredReason() {
		return enteredReason;
	}

	public void setEnteredReason(String enteredReason) {
		this.enteredReason = enteredReason;
	}

	public Date getLeftHouse() {
		return leftHouse;
	}

	public void setLeftHouse(Date leftHouse) {
		this.leftHouse = leftHouse;
	}

	public String getLeftReason() {
		return leftReason;
	}

	public void setLeftReason(String leftReason) {
		this.leftReason = leftReason;
	}

	public List<PoliticalOffice> getOffices() {
		return offices;
	}

	public void setOffices(List<PoliticalOffice> offices) {
		this.offices = offices;
	}

	private String imageHeight;

	private Date enteredHouse;

	private String enteredReason;
	
	private Date leftHouse;

	private String leftReason;
	
	private List<PoliticalOffice> offices = new ArrayList<PoliticalOffice>();

}
