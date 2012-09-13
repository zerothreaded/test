package com.dd.platform.service.integration;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dd.platform.service.file.TextFileWriter;

public class TestTheyWorkForYouIntegrationService extends TestCase {

	protected ApplicationContext testContext;
	protected TheyWorkForYouIntegrationService testTWFYIntegrationService;
	protected TextFileWriter testTextFileWriter;
	protected String apiKey = "EzGjVuAJCaE8DKJSTzFnVHYF";
	protected String importedFileName = "C:\\test_folder\\sandbox\\workspaces\\trunk\\dd-platform\\src\\test\\resources\\incoming\\theyworkforyou\\getConstituencies-js.txt";
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		// Load the application service context
		String[] applicationContexts = new String[] { 
				"config/spring/dd-services.xml",
		};
		testContext = new ClassPathXmlApplicationContext(applicationContexts);
		testTWFYIntegrationService = (TheyWorkForYouIntegrationService) testContext.getBean("service.integration.theyWorkForYou");
		testTextFileWriter = (TextFileWriter) testContext.getBean("service.textFileWriter");

	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testImportConstituencies() throws Exception {
		// Import Constituencies from Web
		String content = testTWFYIntegrationService.importConstituencyNamesFromWebsite(importedFileName, apiKey);
		
	}
	
}
