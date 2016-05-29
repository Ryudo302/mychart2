package br.com.colbert.mychart.test;

import java.util.Arrays;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.junit.*;

/**
 * Implementaão base para todos os testes de integração.
 * 
 * @author ThiagoColbert
 * @since 29 de mai de 2016
 */
public abstract class AbstractIT {

	protected static Server server;

	@BeforeClass
	public static void startServer() throws Exception {
		server = new Server(8080);
		server.setStopAtShutdown(true);

		WebAppContext webAppContext = new WebAppContext();
		webAppContext.setContextPath("/test");
		webAppContext.setResourceBase("src/main/webapp");
		webAppContext.setClassLoader(Thread.currentThread().getContextClassLoader());
		webAppContext.setConfigurationClasses(Arrays.asList("org.eclipse.jetty.webapp.WebXmlConfiguration"));

		server.setHandler(webAppContext);
		server.start();
	}

	@AfterClass
	public static void stopServer() throws Exception {
		server.stop();
	}
}
