package br.com.colbert.mychart.test;

import org.jboss.weld.log.LoggerProducer;
import org.jglue.cdiunit.*;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import br.com.colbert.mychart.infrastructure.jpa.EntityManagerFactoryProducer;
import br.com.colbert.mychart.infrastructure.transaction.TransactionalInterceptor;

/**
 * Implementação base para testes que necessitem do contexto CDI ativado.
 * 
 * @author ThiagoColbert
 * @since 29 de mai de 2016
 */
@RunWith(CdiRunner.class)
@AdditionalPackages({ LoggerProducer.class, TransactionalInterceptor.class })
@AdditionalClasses({ EntityManagerFactoryProducer.class, EntityManagerTestsProducer.class })
public abstract class AbstractCdiTestCase {

	@Rule
	public TestRule printTestNameRule() {
		return new PrintTestNameWatcher();
	}
}
