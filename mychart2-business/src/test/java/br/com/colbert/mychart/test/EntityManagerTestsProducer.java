package br.com.colbert.mychart.test;

import java.io.Serializable;

import javax.enterprise.inject.*;
import javax.inject.Inject;
import javax.persistence.*;

import org.jglue.cdiunit.ProducesAlternative;
import org.slf4j.Logger;

/**
 * Provê acesso a instâncias de {@link EntityManager}.
 * 
 * @author ThiagoColbert
 * @since 29 de mai de 2016
 */
public class EntityManagerTestsProducer implements Serializable {

	private static final long serialVersionUID = 3075682395966745202L;

	@Inject
	private transient Logger logger;
	@Inject
	private transient EntityManagerFactory entityManagerFactory;

	/**
	 * Obtém uma instância de {@link EntityManager}.
	 * 
	 * @return a instância criada
	 */
	@Produces
	@ProducesAlternative
	public EntityManager criarEntityManager() {
		logger.debug("Criando novo EntityManager");
		return entityManagerFactory.createEntityManager();
	}

	/**
	 * Fecha um {@link EntityManager} criado anteriormente.
	 * 
	 * @param entityManager
	 *            instância a ser fechada
	 */
	public void fecharEntityManager(@Disposes EntityManager entityManager) {
		if (entityManager.isOpen()) {
			entityManager.close();
		}
	}
}