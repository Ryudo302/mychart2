package br.com.colbert.mychart.application.artista;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

import java.util.*;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.jglue.cdiunit.ProducesAlternative;
import org.junit.Test;
import org.mockito.Mock;

import br.com.colbert.mychart.domain.artista.*;
import br.com.colbert.mychart.test.AbstractCdiTestCase;

/**
 * Testes unitários da {@link ArtistaResource}.
 * 
 * @author ThiagoColbert
 * @since 29 de mai de 2016
 */
public class ArtistaResourceTest extends AbstractCdiTestCase {

	private static final Artista ARTISTA1 = new Artista(1, "Fulano");
	private static final Artista ARTISTA2 = new Artista(2, "Ciclano");

	@Inject
	private ArtistaResource artistaResource;

	@Produces
	@ProducesAlternative
	@Mock
	private ArtistaRepository artistaRepository;

	@Test
	public void testRecuperarTodos() {
		List<Artista> artistas = Arrays.asList(ARTISTA1, ARTISTA2);

		when(artistaRepository.getTodos()).thenReturn(artistas);

		List<Artista> artistasRecuperados = artistaResource.recuperarTodos();

		verify(artistaRepository, times(1)).getTodos();
		assertThat(artistasRecuperados, is(equalTo(artistas)));
	}
}
