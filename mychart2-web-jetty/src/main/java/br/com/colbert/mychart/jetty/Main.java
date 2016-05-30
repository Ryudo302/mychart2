package br.com.colbert.mychart.jetty;

import java.io.File;
import java.net.URI;
import java.util.Arrays;

import javax.ws.rs.core.UriBuilder;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AllowSymLinkAliasChecker;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.*;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * 
 * @author ThiagoColbert
 * @since 28 de mai de 2016
 */
public final class Main {

	private Main() {

	}

	/**
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		/*System.setProperty("com.sun.jersey.server.impl.cdi.lookupExtensionInBeanManager", "true");

		server.setHandler(webApp);

		server.start();
		// server.dumpStdErr();
		server.setStopAtShutdown(true);
		server.join();*/
		
		WebAppContext webApp = new WebAppContext();
		webApp.setContextPath("/mychart");
		
		File warFile = new File("../mychart2-web/target/mychart2");
		webApp.setWar(warFile.getAbsolutePath());
		webApp.setResourceBase(warFile.getAbsolutePath());
		
		webApp.setConfigurationClasses(Arrays.asList("org.eclipse.jetty.webapp.WebXmlConfiguration"));
		webApp.addAliasCheck(new AllowSymLinkAliasChecker());
		
		WebAppClassLoader classLoader = new WebAppClassLoader(webApp);
		classLoader.addClassPath("../mychart2-web/target/mychart2/WEB-INF/classes");
		Resource jars = Resource.newResource("../mychart2-web/target/mychart2/WEB-INF/lib");
		classLoader.addJars(jars);

		ResourceConfig application = new ResourceConfig().setClassLoader(classLoader);
		
		URI baseUri = UriBuilder.fromUri("http://localhost/").port(8080).build();
		ResourceConfig config = ResourceConfig.forApplication(application);

	    Server server = JettyHttpContainerFactory.createServer(baseUri, config);

	    server.join();
	}
}
