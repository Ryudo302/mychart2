package br.com.colbert.mychart.test;

import javax.inject.Inject;
import javax.persistence.*;

import org.junit.*;
import org.slf4j.Logger;

/**
 * Implementação base dos testes que necessitem do contexto de persistência.
 * 
 * @author ThiagoColbert
 * @since 29 de mai de 2016
 */
public abstract class AbstractPersistenceTestCase extends AbstractCdiTestCase {

	private static EntityManagerFactory entityManagerFactory;

	@Inject
	private Logger logger;

	protected EntityManager entityManager;

	@BeforeClass
	public static void setUpClass() {
		entityManagerFactory = Persistence.createEntityManagerFactory("MyChartPU");
	}

	@AfterClass
	public static void tearDownClass() {
		entityManagerFactory.close();
	}

	@Before
	public void setUpEntityManager() {
		logger.debug("Abrindo EntityManager");
		entityManager = entityManagerFactory.createEntityManager();
	}

	@After
	public void tearDownEntityManager() {
		logger.debug("Fechando EntityManager: {}", entityManager);
		entityManager.close();
	}
}
