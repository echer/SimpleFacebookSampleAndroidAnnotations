package com.devmix.snapshot.ws;

import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;

import com.devmix.snapshot.model.Cidade;
import com.devmix.snapshot.model.ConfiguracaoCidade;
import com.devmix.snapshot.model.ConfiguracaoEnterprise;
import com.devmix.snapshot.model.Configuracoes;
import com.devmix.snapshot.model.Enterprise;
import org.androidannotations.annotations.rest.Accept;
import org.androidannotations.annotations.rest.Post;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.MediaType;

@Rest(converters = { MappingJacksonHttpMessageConverter.class })
public interface ConfiguracoesWS {
	@Post("/load")
	@Accept(MediaType.APPLICATION_JSON)
	Configuracoes load(String facebookId);
	
	@Post("/saveCidade/{facebookId}")
	@Accept(MediaType.APPLICATION_JSON)
	ConfiguracaoCidade saveCidade(Cidade cidade,String facebookId);
	
	@Post("/deleteCidade/{facebookId}")
	@Accept(MediaType.APPLICATION_JSON)
	Boolean deleteCidade(Cidade cidade,String facebookId);
	
	@Post("/deleteCidade/{facebookId}")
	@Accept(MediaType.APPLICATION_JSON)
	ConfiguracaoEnterprise saveEnterprise(Enterprise enterprise,String facebookId);
	
	@Post("/deleteEnterprise/{facebookId}")
	@Accept(MediaType.APPLICATION_JSON)
	Boolean deleteEnterprise(Enterprise enterprise,String facebookId);
	
	void setRootUrl(String rootUrl);
}
