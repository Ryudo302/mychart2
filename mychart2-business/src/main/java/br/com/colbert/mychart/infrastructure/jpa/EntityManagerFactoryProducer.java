package br.com.colbert.mychart.infrastructure.jpa;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.*;
import javax.inject.Inject;
import javax.persistence.*;

import org.slf4j.Logger;

/**
 * 
 * @author ThiagoColbert
 * @since 29 de mai de 2016
 */
public class EntityManagerFactoryProducer implements Serializable {

	private static final long serialVersionUID = 902185891912929393L;

	@Inject
	private transient Logger logger;

	/**
	 * Obtém uma instância de {@link EntityManagerFactory}.
	 * 
	 * @return a instância criada
	 */
	@Produces
	@ApplicationScoped
	public EntityManagerFactory criarEntityManagerFactory() {
		logger.info("Inicializando contexto de persistência");
		return Persistence.createEntityManagerFactory("MyChartPU");
	}

	/**
	 * Fecha uma instância de {@link EntityManagerFactory} criada anteriormente.
	 * 
	 * @param entityManagerFactory
	 *            a instância a ser fechada
	 */
	public void fecharEntityManagerFactory(@Disposes EntityManagerFactory entityManagerFactory) {
		if (entityManagerFactory.isOpen()) {
			logger.info("Encerrando contexto de persistência: {}", entityManagerFactory);
			entityManagerFactory.close();
		}
	}
}
