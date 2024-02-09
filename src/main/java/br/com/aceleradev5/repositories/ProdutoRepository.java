package br.com.aceleradev5.repositories;

import br.com.aceleradev5.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

    boolean existsProdutoByCodigo(Long codigo);

    Produto searchProdutoByCodigo(Long codigo);
}
