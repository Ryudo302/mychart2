package br.com.colbert.mychart.infrastructure.transaction;

import java.io.Serializable;

import javax.inject.Inject;
import javax.interceptor.*;
import javax.persistence.EntityManager;

import org.slf4j.Logger;

import br.com.colbert.mychart.infrastructure.transaction.Transactional.TransactionType;

/**
 * {@link Interceptor} responsável por gerenciar transações.
 * 
 * @author ThiagoColbert
 * @since 29 de mai de 2016
 */
@Transactional
@Interceptor
public class TransactionalInterceptor implements Serializable {

	private static final long serialVersionUID = -6770733070677570044L;

	@Inject
	private transient Logger logger;
	@Inject
	private transient EntityManager entityManager;

	/**
	 * 
	 * @param invocation
	 * @return
	 * @throws Exception
	 */
	@AroundInvoke
	public Object wrapInTransaction(InvocationContext invocation) throws Exception {
		Transactional annotation = invocation.getMethod().getAnnotation(Transactional.class);
		if (annotation != null) {
			TransactionType transactionType = annotation.value();
			logger.debug("Tipo: {}", transactionType);

			if (transactionType == TransactionType.REQUIRED) {
				boolean owner = !entityManager.getTransaction().isActive();

				if (owner) {
					logger.debug("Iniciando nova transação");
					entityManager.getTransaction().begin();
				} else {
					logger.debug("Já existe uma transação ativa");
				}

				try {
					return invocation.proceed();
				} catch (RuntimeException exception) {
					entityManager.getTransaction().setRollbackOnly();
					throw exception;
				} finally {
					if (owner) {
						if (entityManager.getTransaction().getRollbackOnly()) {
							logger.debug("ROLLBACK");
							entityManager.getTransaction().rollback();
						} else {
							logger.debug("COMMIT");
							entityManager.getTransaction().commit();
						}
					}
				}
			} else {
				return invocation.proceed();
			}
		} else {
			return invocation.proceed();
		}
	}
}
