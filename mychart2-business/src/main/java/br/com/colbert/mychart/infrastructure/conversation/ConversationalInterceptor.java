package br.com.colbert.mychart.infrastructure.conversation;

import java.io.Serializable;
import java.util.*;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.interceptor.*;

import org.jboss.weld.context.ConversationContext;
import org.jboss.weld.context.bound.*;
import org.jboss.weld.context.http.Http;
import org.slf4j.Logger;

/**
 * {@link Interceptor} responsável por gerenciar contextos de conversação.
 *
 * @author ThiagoColbert
 * @since 29 de mai de 2016
 * 
 * @see <a href="https://www.42lines.net/2011/12/07/conversations-and-transactions-in-worker-threads/index.html">Original</a>
 */
@Conversational
@Interceptor
public class ConversationalInterceptor implements Serializable {

	private static final long serialVersionUID = -3422158303483602205L;

	@Inject
	private transient Logger logger;

	@Inject
	@Http
	private transient Instance<ConversationContext> httpContext;

	@Inject
	@Bound
	private transient BoundConversationContext boundContext;

	/**
	 * 
	 * @param invocation
	 * @return
	 * @throws Exception
	 */
	@AroundInvoke
	public Object wrapInConversation(InvocationContext invocation) throws Exception {
		BoundRequest storage = null;

		if (!isActive(httpContext) && !boundContext.isActive()) {
			logger.debug("Iniciando nova conversação");

			Map<String, Object> session = new HashMap<>();
			Map<String, Object> request = new HashMap<>();
			storage = new MutableBoundRequest(request, session);
			boundContext.associate(storage);
			boundContext.activate();
		}

		try {
			return invocation.proceed();
		} finally {
			if (storage != null) {
				logger.debug("Encerrando conversação");
				boundContext.deactivate();
				boundContext.dissociate(storage);
			}
		}
	}

	private boolean isActive(Instance<ConversationContext> context) {
		return !context.isUnsatisfied() && context.get().isActive();
	}
}
