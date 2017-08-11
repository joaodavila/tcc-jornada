package br.com.exemplo.usuario;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.exemplo.usuario.modelo.Carrinho;
import br.com.exemplo.usuario.modelo.Produto;
import br.com.exemplo.usuario.modelo.Projeto;

public class ClienteTest {
	
	private HttpServer servidor;
	private WebTarget target;

	@Before
	public void IniciaServidor(){
		servidor = Servidor.startaServidor();
		ClientConfig config = new ClientConfig();
		config.register(new LoggingFilter());
		Client client = ClientBuilder.newClient(config);
		target = client.target("http://localhost:8085");
	}
	
	@After
	public void paraServidor(){
		servidor.stop();
	}

	@Test
	public void testaQueBuscarUmCarrinhoTrazOCarrinhoEsperado() throws Exception {
		Carrinho carrinho = target.path("/carrinhos/1").request().get(Carrinho.class);		
		Assert.assertEquals("Rua Vergueiro 3185, 8 andar", carrinho.getRua());
	}
	
	@Test
	public void testaQueBuscarUmProjetoTrazOProjetoEsperado() throws Exception {
		Projeto projeto = target.path("/projetos/1").request().get(Projeto.class);
		Assert.assertEquals("Minha loja",projeto.getNome());
	}
	
	@Test
	public void testaInsercaoDeCarrinho() throws Exception {
		Carrinho carrinho = new Carrinho();
		carrinho.adiciona(new Produto(314L, "Tablet", 999, 1));
        carrinho.setRua("Rua Vergueiro");
        carrinho.setCidade("Sao Paulo");
        Entity<Carrinho> entity = Entity.entity(carrinho, MediaType.APPLICATION_XML);
        Response response = target.path("/carrinhos").request().post(entity);        
        Assert.assertEquals(201, response.getStatus());
        String location = response.getHeaderString("Location");
        System.out.println(location);
	}
}
