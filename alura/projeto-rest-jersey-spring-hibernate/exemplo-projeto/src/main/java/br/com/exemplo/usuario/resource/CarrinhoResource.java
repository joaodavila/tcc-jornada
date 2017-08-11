package br.com.exemplo.usuario.resource;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.XStream;

import br.com.exemplo.usuario.dao.CarrinhoDAO;
import br.com.exemplo.usuario.modelo.Carrinho;
import br.com.exemplo.usuario.modelo.Produto;

@Path("carrinhos")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CarrinhoResource {
	
	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public Carrinho busca(@PathParam("id") long id){
		CarrinhoDAO dao = new CarrinhoDAO();
		//return dao.busca(id).toXml();
		return dao.busca(id);
	}
	
	@POST
    @Consumes(MediaType.APPLICATION_XML)
    public Response adiciona(Carrinho carrinho) {		
		new CarrinhoDAO().adiciona(carrinho);
		URI uri = URI.create("/carrinhos/" + carrinho.getId());
        return Response.created(uri).build();
    }
	
	@Path("{id}/produtos/{produtoId}")
    @DELETE
    public Response removeProduto(@PathParam("id") long id, @PathParam("produtoId") long produtoId) {		
		CarrinhoDAO dao = new CarrinhoDAO();		
		dao.busca(id).remove(produtoId);
		//System.out.println("DELETANDO PRODUTO"+produtoId);
        return Response.ok().build();
    }
	
	@Path("{id}/produtos/{produtoId}/quantidade")
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public Response alteraProduto(@PathParam("id") long id, @PathParam("produtoId") long produtoId, String conteudo) {
		CarrinhoDAO dao = new CarrinhoDAO();
        Produto produto = (Produto) new XStream().fromXML(conteudo);        
        dao.busca(id).trocaQuantidade(produto);
        System.out.println("PRODUTO ALTERADO QTDE: "+ produto.getQuantidade());
        return Response.ok().build();
    }
}
