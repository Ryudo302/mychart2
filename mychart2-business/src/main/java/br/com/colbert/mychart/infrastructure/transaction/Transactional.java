package br.com.colbert.mychart.infrastructure.transaction;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.*;

import javax.enterprise.util.Nonbinding;
import javax.interceptor.InterceptorBinding;

import br.com.colbert.mychart.infrastructure.conversation.Conversational;

/**
 * Qualificador que indica que um método ou tipo deve executar dentro de uma transação.
 * 
 * @author ThiagoColbert
 * @since 29 de mai de 2016
 */
@Target({ METHOD, TYPE })
@Retention(RUNTIME)
@Documented
@InterceptorBinding
@Conversational
public @interface Transactional {

	/**
	 * Os tipos de comportamento possíveis dentro de um contexto transacional.
	 * 
	 * @author ThiagoColbert
	 * @since 29 de mai de 2016
	 */
	public enum TransactionType {

		/**
		 * Cria uma nova transação caso não já exista uma. Caso contrário, reaproveita a transação existente.
		 */
		REQUIRED,

		/**
		 * Não necessita de uma transação aberta, mas a utiliza caso exista uma.
		 */
		SUPPORTED;
	}

	/**
	 * Comportamento do contexto transacional.
	 * 
	 * @return o valor
	 */
	@Nonbinding
	TransactionType value() default TransactionType.REQUIRED;
}
