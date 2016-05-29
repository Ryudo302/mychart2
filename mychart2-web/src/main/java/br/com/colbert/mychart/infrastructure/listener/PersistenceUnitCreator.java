package br.com.colbert.mychart.infrastructure.listener;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.*;
import javax.persistence.*;
import javax.servlet.*;
import javax.servlet.annotation.WebListener;

import org.slf4j.*;

/**
 * {@link ServletContextListener} responsável por inicializar o contexto de persistência da aplicação.
 * 
 * @author ThiagoColbert
 * @since 29 de mai de 2016
 */
@WebListener
public class PersistenceUnitCreator implements ServletContextListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(PersistenceUnitCreator.class);

	private transient EntityManagerFactory entityManagerFactory;

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		LOGGER.info("Inicializando contexto de persistência");
		entityManagerFactory = Persistence.createEntityManagerFactory("MyChartPU");
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		LOGGER.info("Fechando contexto de persistência");
		entityManagerFactory.close();
	}

	/**
	 * 
	 * @return
	 */
	@Produces
	@RequestScoped
	public EntityManager createEntityManager() {
		LOGGER.debug("Criando novo EntityManager");
		return entityManagerFactory.createEntityManager();
	}

	/**
	 * 
	 * @param entityManager
	 */
	public void closeEntityManager(@Disposes EntityManager entityManager) {
		LOGGER.debug("Fechando EntityManager: {}", entityManager);
		entityManager.close();
	}
}
