package br.com.colbert.mychart.jetty;

import java.io.File;
import java.util.Arrays;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AllowSymLinkAliasChecker;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.*;

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
		Server server = new Server(8080);

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
		webApp.setClassLoader(classLoader);

		server.setHandler(webApp);

		server.start();
		// server.dumpStdErr();
		server.setStopAtShutdown(true);
		server.join();
	}
}
