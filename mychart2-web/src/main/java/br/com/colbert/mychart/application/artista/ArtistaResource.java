package br.com.colbert.mychart.application.artista;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;

import br.com.colbert.mychart.domain.artista.*;

/**
 * Serviço REST de operções de {@link Artista}.
 * 
 * @author ThiagoColbert
 * @since 29 de mai de 2016
 */
@Path("/artista")
public class ArtistaResource implements Serializable {

	private static final long serialVersionUID = -6786503444452285518L;

	@Inject
	private transient Logger logger;
	@Inject
	private transient ArtistaRepository artistaRepository;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Artista> recuperarTodos() {
		logger.info("Recuperando todos os artistas");
		return artistaRepository.getTodos();
	}
}
