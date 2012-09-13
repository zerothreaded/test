package com.dd.platform.data;

import java.util.Date;
import java.util.Map;

public class Parliament {

	// DD Identity
	private String id;

	// Date of Election
	private Date electionDate;

	// Start Date of Parliament
	private Date startDate;

	// End Date of Parliament
	private Date endDate;

	// Year of election - e.g. 2010
	private String electionYear;
	
	private Map<String, MemberOfParliament> membersOfParliament;
	

}
