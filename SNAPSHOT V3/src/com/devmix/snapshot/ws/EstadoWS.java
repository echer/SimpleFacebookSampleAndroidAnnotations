package com.devmix.snapshot.ws;

import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;

import com.devmix.snapshot.model.EstadoList;
import org.androidannotations.annotations.rest.Accept;
import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.MediaType;

@Rest(converters = { MappingJacksonHttpMessageConverter.class })
public interface EstadoWS {

	@Get("/list")
	@Accept(MediaType.APPLICATION_JSON) 
	EstadoList list();
	
	void setRootUrl(String rootUrl);
}
