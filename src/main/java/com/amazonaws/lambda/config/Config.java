package com.amazonaws.lambda.config;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class Config {

	private static Config instance = null;
	private String apiKey;
	private String awsAccessKey;
	private String awsSecretKey;

	public Config() {
		Properties properties = new Properties();
		try {
			properties.load(new InputStreamReader(
					Config.class.getClassLoader().getResourceAsStream("./resources/config.properties"), "UTF-8"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		this.apiKey = properties.getProperty("google.maps.api.key");
		this.awsAccessKey = properties.getProperty("aws.access.key");
		this.awsSecretKey = properties.getProperty("aws.secret.key");
	}

	public static Config getInstance() {
	      if(instance == null) {
	         instance = new Config();
	      }
	      return instance;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getAwsAccessKey() {
		return awsAccessKey;
	}

	public void setAwsAccessKey(String awsAccessKey) {
		this.awsAccessKey = awsAccessKey;
	}

	public String getAwsSecretKey() {
		return awsSecretKey;
	}

	public void setAwsSecretKey(String awsSecretKey) {
		this.awsSecretKey = awsSecretKey;
	}
	

}
