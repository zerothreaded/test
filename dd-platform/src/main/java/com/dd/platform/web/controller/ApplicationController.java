package com.dd.platform.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.dd.platform.data.Constituency;
import com.dd.platform.data.Person;
import com.dd.platform.service.content.ContentService;
import com.dd.platform.service.file.TextFileWriter;
import com.dd.platform.web.ApplicationContextProvider;

@Controller
public class ApplicationController extends AbstractController {
	
	// protected ContentService contentService;
	protected TextFileWriter testTextFileWriter = new TextFileWriter();
	protected ApplicationContext applicationContext;
	protected String apiKey = "EzGjVuAJCaE8DKJSTzFnVHYF";
	protected String outputFileName = "/Users/ralphmasilamani/Documents/workspace/dd-platform/src/test/resources/incoming/theyworkforyou/getConstituencies-js.txt";
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("handleRequestInternal() called");

		ModelAndView model = new ModelAndView("views/view");
		HttpSession session = request.getSession();
		
		if (session == null) {
			// New Session
			System.out.println("New HTTP Session");			
		} else {
			// Existing Session
			System.out.println("Existing HTTP Session [" + session.getId() + "]");			
		}
		
		// RETHINK ALL THIS LATER
		applicationContext = ApplicationContextProvider.getApplicationContext();
		session.setAttribute("SPRING.CONTAINER", applicationContext);		

		ContentService contentService = (ContentService) applicationContext.getBean("service.content.ContentService");

		// Constituencies Names
		List<String> constituencyNames = contentService.getAllConstituencyNames();
		model.addObject("CONST.NAMES", constituencyNames);

		Map<String, Person> mpData = contentService.getMps();
		
		// Load all the MP Details and save to files
//		for (Person person : mpData.values()) {
//			contentService.getMpData(person.getPersonId().toString());			
//		}		
		Person person = null;
		
		String requestLookupName = (String) request.getParameter("lookupName");
		if (requestLookupName == null) {
			System.out.println("NO LOOKUP NAME");
			// NO Name - get all Constituencies
			Map<String, Constituency> allConstituencies = contentService.getAllConstituencies();
			model.addObject("ALL.CONST.DATA", allConstituencies);
		} else {
			System.out.println("LOOKUP NAME [" + requestLookupName + "]");
			// Name - get only that one
			Map<String, Constituency> allConstituencies = contentService.getAllConstituencies();
			Constituency lookedUpConstituency = allConstituencies.get(requestLookupName);
			allConstituencies = new HashMap<String, Constituency>();
			if (lookedUpConstituency == null) {
				System.out.println("LOOKUP RESULT [NULL]");
			} else {
				System.out.println("LOOKUP RESULT [" + lookedUpConstituency.toString() + "]");
				allConstituencies.put(requestLookupName, lookedUpConstituency);
				person = mpData.get(lookedUpConstituency.getName());
				contentService.getMpData(person.getPersonId().toString());
			}
			model.addObject("SINGLE.CONST.DATA", allConstituencies);	
			model.addObject("SINGLE.MP.PERSON", person);
		}
		model.addObject("FORM.INPUT.LOOKUP_NAME", requestLookupName);
		return model;
	}


}
