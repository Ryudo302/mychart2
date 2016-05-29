package br.com.colbert.mychart.domain.artista;

import javax.persistence.*;

import org.apache.commons.lang3.builder.*;

import br.com.colbert.common.domain.AbstractIdentificavel;

/**
 * Um artista musical.
 * 
 * @author ThiagoColbert
 * @since 28 de mai de 2016
 */
@Entity
@Table(name = "ARTISTA")
@SequenceGenerator(name = "ArtistaId", sequenceName = "SEQ_ID_ARTISTA", allocationSize = 1)
@NamedQueries({ @NamedQuery(name = Artista.QUERY_FIND_ALL, query = "SELECT a FROM Artista a") })
public class Artista extends AbstractIdentificavel<Long> {

	/**
	 * <em>Query</em> que recupera todos os artistas.
	 */
	public static final String QUERY_FIND_ALL = "Artista.findAll";

	private static final long serialVersionUID = -2822029065239232789L;

	@Id
	@Column(name = "COD_ARTISTA", precision = 0, scale = 4)
	@GeneratedValue(generator = "ArtistaId", strategy = GenerationType.SEQUENCE)
	private long id;

	@Column(name = "NOM_ARTISTA", length = 100, nullable = false, unique = false)
	private String nome;

	/**
	 * Cria um novo artista informando seu ID e nome.
	 * 
	 * @param id
	 *            ID do artista
	 * @param nome
	 *            nome do artista
	 */
	public Artista(long id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	Artista() {
		this(0, null);
	}

	@Override
	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("nome", nome).toString();
	}
}
