package br.com.colbert.common.domain;

import java.io.Serializable;

import org.apache.commons.lang3.builder.*;

/**
 * Implementação-base para classes cujas instâncias possuam um identificador único.
 * 
 * @author ThiagoColbert
 * @since 28 de mai de 2016
 *
 * @param <ID>
 *            tipo de identificador único
 */
public abstract class AbstractIdentificavel<ID extends Serializable> implements Identificavel<ID>, Serializable {

	private static final long serialVersionUID = -123825056391507087L;

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Identificavel && new EqualsBuilder().append(getId(), ((Identificavel<?>) obj).getId()).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("id", getId()).toString();
	}
}
