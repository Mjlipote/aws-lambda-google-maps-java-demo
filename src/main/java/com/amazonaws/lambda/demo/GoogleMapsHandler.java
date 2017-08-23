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
import com.google.maps.TextSearchRequest;
import com.google.maps.errors.ApiException;
import com.google.maps.model.PlaceType;
import com.google.maps.model.RankBy;

public class GoogleMapsHandler implements RequestHandler<ApiGatewayRequest, ApiGatewayProxyResponse> {

	@Override
	public ApiGatewayProxyResponse handleRequest(ApiGatewayRequest request, Context context) {
		context.getLogger().log("Input: " + request);

		String query = request.getQueryStringParameters().getOrDefault("query", "Taipei City");
		String rankby = request.getQueryStringParameters().getOrDefault("rankby", "PROMINENCE").toUpperCase();
		String type = request.getQueryStringParameters().getOrDefault("type", "RESTAURANT").toUpperCase();
		String radius = request.getQueryStringParameters().getOrDefault("radius", "5000");

		GeoApiContext geoApiContext = new GeoApiContext.Builder().apiKey(Config.getInstance().getApiKey()).build();
		TextSearchRequest textSearchRequest = new TextSearchRequest(geoApiContext);
		Map<String, String> header = newHashMap();
		header.put("Content-Type", "application/json; charset=utf-8");
		Gson json = new Gson();
		String result = null;
		try {
			result = json.toJson(textSearchRequest.type(PlaceType.valueOf(type)).query(query)
					.rankby(RankBy.valueOf(rankby)).radius(Integer.valueOf(radius)).await().results);
		} catch (ApiException | InterruptedException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new ApiGatewayProxyResponse(200, header, result);

	}

}
