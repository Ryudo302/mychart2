package br.com.colbert.mychart.test;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * Implementação de {@link TestRule} que imprime o nome do teste antes de sua execução.
 * 
 * @author ThiagoColbert
 * @date 27/05/2014
 */
public class PrintTestNameWatcher implements TestRule {

	@Override
	public Statement apply(Statement base, Description description) {
		System.out.println();
		System.out.println(">>>>>> Executando: " + description.getMethodName() + " <<<<<<");
		System.out.println();
		return base;
	}
}
