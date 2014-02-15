package com.devmix.snapshot.ws;

import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;

import com.devmix.snapshot.model.Profile;
import org.androidannotations.annotations.rest.Accept;
import org.androidannotations.annotations.rest.Post;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.MediaType;

@Rest(converters = { MappingJacksonHttpMessageConverter.class })
public interface ProfileWS {  
	@Post("/save")
	@Accept(MediaType.APPLICATION_JSON) 
	Profile save(Profile profile);

	@Post("/update")
	@Accept(MediaType.APPLICATION_JSON)
	Profile update(Profile profile);

	@Post("/load")
	@Accept(MediaType.APPLICATION_JSON)
	Profile load(Profile profile);
	
	void setRootUrl(String rootUrl);
}
