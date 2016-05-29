package br.com.colbert.mychart.infrastructure.conversation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.*;

import javax.interceptor.InterceptorBinding;

/**
 * Qualificador que indica que um método ou tipo deve executar dentro de um contexto de conversação.
 * 
 * @author ThiagoColbert
 * @since 29 de mai de 2016
 * 
 * @see <a href="https://www.42lines.net/2011/12/07/conversations-and-transactions-in-worker-threads/index.html">Original</a>
 */
@Target({ METHOD, TYPE })
@Retention(RUNTIME)
@Documented
@InterceptorBinding
public @interface Conversational {
}
