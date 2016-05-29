package br.com.colbert.mychart.domain.artista;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;

import br.com.colbert.mychart.test.AbstractDbUnitTestCase;

/**
 * Testes de integração da {@link ArtistaRepository}.
 * 
 * @author ThiagoColbert
 * @since 29 de mai de 2016
 */
public class ArtistaRepositoryIT extends AbstractDbUnitTestCase {

	@Inject
	private ArtistaRepository artistaRepository;

	@Override
	protected String getDataSetFileName() {
		return "artista.xml";
	}

	@Test
	public void testGetTodos() {
		List<Artista> artistas = artistaRepository.getTodos();
		assertThat(artistas.size(), is(equalTo(3)));
	}
}
