/*
 * @author Ming-Jheng Li
 */

package com.amazonaws.lambda.demo;

import static com.google.common.collect.Maps.newHashMap;

import java.io.IOException;
import java.util.Map;

import com.amazonaws.lambda.apigateway.ApiGatewayProxyResponse;
import com.amazonaws.lambda.apigateway.ApiGatewayRequest;
import com.amazonaws.lambda.config.Config;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;

public class GeocodingHandler implements RequestHandler<ApiGatewayRequest, ApiGatewayProxyResponse> {

	@Override
	public ApiGatewayProxyResponse handleRequest(ApiGatewayRequest request, Context context) {
		context.getLogger().log("Input: " + request);
		String query = request.getQueryStringParameters().getOrDefault("query", "Taipei City");

		GeoApiContext geoApiContext = new GeoApiContext.Builder().apiKey(Config.getInstance().getApiKey()).build();
		GeocodingResult[] results = null;
		try {
			results = GeocodingApi.geocode(geoApiContext, query).await();
		} catch (ApiException | InterruptedException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Gson json = new Gson();

		Map<String, String> header = newHashMap();
		header.put("Content-Type", "application/json; charset=utf-8");

		return new ApiGatewayProxyResponse(200, header, json.toJson(results));
	}

}
