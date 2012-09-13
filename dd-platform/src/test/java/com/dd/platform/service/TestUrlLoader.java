package com.dd.platform.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dd.platform.service.file.TextFileWriter;
import com.dd.platform.service.integration.TheyWorkForYouIntegrationService;

import junit.framework.TestCase;

public class TestUrlLoader extends TestCase {

	protected ApplicationContext testContext;
	protected UrlLoader testUrlLoader;
	protected TextFileWriter testTextFileWriter;
	protected TheyWorkForYouIntegrationService testTWFYIntegrationService;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();

		// Load the application service context
		String[] applicationContexts = new String[] { 
				"config/spring/dd-services.xml",
		};
		testContext = new ClassPathXmlApplicationContext(applicationContexts);
		
		// Fetch Services
		testUrlLoader = (UrlLoader) testContext.getBean("service.urlLoader");
		testTextFileWriter = (TextFileWriter) testContext.getBean("service.textFileWriter");
		testTWFYIntegrationService = (TheyWorkForYouIntegrationService) testContext.getBean("service.integration.theyWorkForYou");
		
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testUrlLoader() throws Exception {
		// Test URL Loader Service
		String testUrl = "http://news.bbc.co.uk";
		String content = testUrlLoader.loadUrl(testUrl);
		System.out.println(content);
		
		// Test TWFY Integration Service
		testTWFYIntegrationService.importConstituencyNamesFromWebsite("C:\\test_folder\\sandbox\\workspaces\\trunk\\dd-platform\\src\\test\\resources\\incoming\\theyworkforyou\\getConstituencies-js.txt", "EzGjVuAJCaE8DKJSTzFnVHYF");
	}
	
}
