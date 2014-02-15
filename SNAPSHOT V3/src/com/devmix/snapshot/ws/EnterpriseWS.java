package com.devmix.snapshot.ws;

import org.androidannotations.annotations.rest.Accept;
import org.androidannotations.annotations.rest.Post;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.MediaType;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;

import com.devmix.snapshot.model.CidadeListString;
import com.devmix.snapshot.model.Enterprise;
import com.devmix.snapshot.model.EnterpriseFoto;
import com.devmix.snapshot.model.EnterpriseList;

@Rest(converters = { MappingJacksonHttpMessageConverter.class })
public interface EnterpriseWS{
	@Post("/list/{facebookId}")
	@Accept(MediaType.APPLICATION_JSON)
	EnterpriseList list(CidadeListString cidades, String facebookId);
	
	@Post("/getEnterpriseImagem/{facebookId}")
	@Accept(MediaType.APPLICATION_JSON)
	EnterpriseFoto getEnterpriseImagem(Enterprise en,String facebookId);

	void setRootUrl(String rootUrl);
}
