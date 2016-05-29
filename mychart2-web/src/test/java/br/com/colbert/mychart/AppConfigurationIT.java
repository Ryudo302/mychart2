package br.com.colbert.mychart;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import br.com.colbert.mychart.test.AbstractIT;

/**
 * Testes de integração das configurações da aplicação.
 * 
 * @author ThiagoColbert
 * @since 29 de mai de 2016
 */
public class AppConfigurationIT extends AbstractIT {

	@Test
	public void test() {
		assertThat(server.isRunning(), is(true));
	}
}
