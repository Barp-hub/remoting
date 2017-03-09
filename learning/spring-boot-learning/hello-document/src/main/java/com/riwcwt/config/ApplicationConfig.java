package com.riwcwt.config;

import java.lang.reflect.Type;
import java.util.Date;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

@Configuration
public class ApplicationConfig {

	@Bean
	public ThreadPoolTaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(50);
		executor.setMaxPoolSize(150);
		executor.setWaitForTasksToCompleteOnShutdown(true);
		executor.setAwaitTerminationSeconds(60);
		return executor;
	}

	@Bean
	public Gson gson() {
		Gson gson = new GsonBuilder().enableComplexMapKeySerialization()
				.registerTypeAdapter(Date.class, new JsonSerializer<Date>() {
					@Override
					public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
						return src == null ? null : new JsonPrimitive(src.getTime());
					}
				}).registerTypeAdapter(java.util.Date.class, new JsonSerializer<java.util.Date>() {
					@Override
					public JsonElement serialize(java.util.Date src, Type typeOfSrc, JsonSerializationContext context) {
						return src == null ? null : new JsonPrimitive(src.getTime());
					}
				}).registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
					@Override
					public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
							throws JsonParseException {
						return new Date(json.getAsJsonPrimitive().getAsLong());
					}
				}).registerTypeAdapter(java.util.Date.class, new JsonDeserializer<java.util.Date>() {
					@Override
					public java.util.Date deserialize(JsonElement json, Type typeOfT,
							JsonDeserializationContext context) throws JsonParseException {
						return new java.util.Date(json.getAsJsonPrimitive().getAsLong());
					}
				}).setVersion(1.0).create();
		return gson;
	}

}
