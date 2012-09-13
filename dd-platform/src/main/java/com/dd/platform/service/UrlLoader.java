package com.dd.platform.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class UrlLoader {

	public static final String PROPERTY_PROXY_HOST = "http.proxyHost";
	public static final String PROPERTY_PROXY_PORT = "http.proxyPort";
	
	protected String proxyHost;
	protected String proxyPort;

	public UrlLoader() {
		
	}

	public UrlLoader(String proxyHost, String proxyPort) {
		this.proxyHost = proxyHost;
		this.proxyPort = proxyPort;
	}

	public String getProxyHost() {
		return proxyHost;
	}

	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}

	public String getProxyPort() {
		return proxyPort;
	}

	public void setProxyPort(String proxyPort) {
		this.proxyPort = proxyPort;
	}

	public String loadUrl(String url) throws Exception {
		// Use the proxy conection
		// TODO: Add a flag to make this switchable
		System.setProperty(PROPERTY_PROXY_HOST, proxyHost);
		System.setProperty(PROPERTY_PROXY_PORT, proxyPort);

		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new URL(url).openConnection().getInputStream()));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = bufferedReader.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			bufferedReader.close();			
		}
		return sb.toString();
	}

}
