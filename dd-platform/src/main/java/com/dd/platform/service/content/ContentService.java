package com.dd.platform.service.content;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.dd.platform.data.Constituency;
import com.dd.platform.data.MemberOfParliament;
import com.dd.platform.data.Person;
import com.dd.platform.service.dao.ConstituencyDao;
import com.dd.platform.service.dao.PersonDao;
import com.dd.platform.service.integration.TheyWorkForYouIntegrationService;

public class ContentService {

	protected TheyWorkForYouIntegrationService theyWorkForYouIntegrationService;
	protected ConstituencyDao constituencyDao;
	protected PersonDao personDao;
	protected String constituencyNamesFileName;

	// Content Cache
	protected Map<String, Constituency> constituencyCache;
	protected List<String> constituencyNamesCache;
	protected Map<String, Person> personCache;
	
	public ContentService() {
		System.out.println("\n\n\n CONTENT SERVICE CONSTRUCTED \n\n\n");	
		constituencyCache = new TreeMap<String, Constituency>();
		constituencyNamesCache = new ArrayList<String>();
		personCache = new TreeMap<String, Person>();
	}

	// Content API
	public List<String> getAllConstituencyNames() throws Exception {
// 		PRE DAO Code		
//		if (constituencyNamesCache.size() == 0) {
//			System.out.println("Reloading constituencyNamesCache");
//			constituencyNamesCache = this.theyWorkForYouIntegrationService.importConstituencyNamesFromFile(constituencyNamesFileName);
//			System.out.println("constituencyNamesCache now has [" + constituencyNamesCache.size() + "] items");
//		}
//		this.theyWorkForYouIntegrationService.importMpsFromFile("getmps-js.txt");
//		return constituencyNamesCache;

		// DB Access - No Cache
		List<String> results = new ArrayList<String>();
		List<Constituency> allConts = this.constituencyDao.findAll();
		// System.out.println("DAO returned [" + allConts.toString() + "]");
		for (Constituency c : allConts) {
			results.add(c.getName());
			constituencyNamesCache.add(c.getName());
		}
		return results;
	}
	
	public Map<String, Person> getMps() throws Exception {
		if (personCache.size() == 0) {
			System.out.println("Loading MP persons cache...");
			personCache = this.theyWorkForYouIntegrationService.importMpsFromFile("getmps-js.txt");
			int id = 0;
			for (Person person : personCache.values()) {
				person.setId(new Integer(id).toString());
				personDao.create(person);
				// this.theyWorkForYouIntegrationService.importMpDataFromWebsite(person.getPersonId().toString(), "EzGjVuAJCaE8DKJSTzFnVHYF");
				id++;
			}
		} else {
			System.out.println("Using MP persons cache...");
		}
		return personCache;
	}
	
	public MemberOfParliament getMpData(String personId) throws Exception {
		
		// return this.theyWorkForYouIntegrationService.importMpDataFromWebsite(personId, "EzGjVuAJCaE8DKJSTzFnVHYF");
		return this.theyWorkForYouIntegrationService.importMpDataFromFile(personId);
		
	}
	
	public Map<String, Constituency> getAllConstituencies() throws Exception {
		if (constituencyCache.size() == 0) {
			System.out.println("Reloading constituencyCache");
			// Build Constituency Model
			int id = 1;
			for (String constituencyName : getAllConstituencyNames()) {
				// Build the Constituency
				Map<String, String> constData = theyWorkForYouIntegrationService.importConstituencyFromFile(constituencyName);
				Constituency constituency = new Constituency("imported.twfy.id." + id, constituencyName);
				constituency.getProperties().putAll(constData);
				constituencyCache.put(constituencyName, constituency);
				id++;
			}
			System.out.println("constituencyCache now has [" + constituencyCache.size() + "] items");
		}
		return constituencyCache;
	}
	
	public TheyWorkForYouIntegrationService getTheyWorkForYouIntegrationService() {
		return theyWorkForYouIntegrationService;
	}

	public void setTheyWorkForYouIntegrationService(
			TheyWorkForYouIntegrationService theyWorkForYouIntegrationService) {
		this.theyWorkForYouIntegrationService = theyWorkForYouIntegrationService;
	}

	public ConstituencyDao getConstituencyDao() {
		return constituencyDao;
	}

	public void setConstituencyDao(ConstituencyDao constituencyDao) {
		this.constituencyDao = constituencyDao;
	}

	public PersonDao getPersonDao() {
		return personDao;
	}

	public void setPersonDao(PersonDao personDao) {
		this.personDao = personDao;
	}

	public String getConstituencyNamesFileName() {
		return constituencyNamesFileName;
	}

	public void setConstituencyNamesFileName(String constituencyNamesFileName) {
		this.constituencyNamesFileName = constituencyNamesFileName;
	}

}