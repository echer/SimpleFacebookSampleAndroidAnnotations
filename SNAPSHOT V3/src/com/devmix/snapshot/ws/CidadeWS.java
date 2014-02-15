package com.devmix.snapshot.ws;

import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;

import com.devmix.snapshot.model.CidadeList;
import org.androidannotations.annotations.rest.Accept;
import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.MediaType;

@Rest(converters = { MappingJacksonHttpMessageConverter.class })
public interface CidadeWS {

	@Get("/list")
	@Accept(MediaType.APPLICATION_JSON) 
	CidadeList list();
	
	void setRootUrl(String rootUrl);
}
