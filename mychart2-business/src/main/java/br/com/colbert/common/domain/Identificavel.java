package br.com.colbert.common.domain;

import java.io.Serializable;

/**
 * Um objeto que possui um identificador único (ID).
 * 
 * @author ThiagoColbert
 * @since 28 de mai de 2016
 * 
 * @param <ID>
 *            o tipo de identificador
 */
public interface Identificavel<ID extends Serializable> {

	/**
	 * Obtém o identificador único da instância.
	 * 
	 * @return o identificador único
	 */
	ID getId();
}
