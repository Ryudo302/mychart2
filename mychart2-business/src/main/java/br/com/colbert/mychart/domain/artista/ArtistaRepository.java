package br.com.colbert.mychart.domain.artista;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.*;

import org.slf4j.Logger;

/**
 * Repositório de {@link Artista}.
 * 
 * @author ThiagoColbert
 * @since 29 de mai de 2016
 */
public class ArtistaRepository implements Serializable {

	private static final long serialVersionUID = 5501516895981814573L;

	@Inject
	private transient Logger logger;

	@PersistenceContext
	private transient EntityManager entityManager;

	/**
	 * Obtém todos os artistas existentes.
	 * 
	 * @return os artistas (vazio caso não exista nenhum)
	 */
	public List<Artista> getTodos() {
		logger.debug("Recuperando todos os artistas");
		return entityManager.createNamedQuery(Artista.QUERY_FIND_ALL, Artista.class).getResultList();
	}
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
