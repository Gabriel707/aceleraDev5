package br.com.aceleradev5.repositories;

import br.com.aceleradev5.entities.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EstoqueRepository extends JpaRepository<Estoque, Integer> {

    @Query("SELECT SUM(e.qtdDisponivel) FROM Estoque e WHERE MONTH(e.dataEstoque) = :mes")
    Integer calculaTotalDeProdutosDisponiveisNoEstoqueParaMes(Integer mes);
}
