package br.com.aceleradev5.repositories;

import br.com.aceleradev5.entities.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Integer> {

    @Query("SELECT v FROM Venda v WHERE MONTH(v.dataDaVenda) = :mes")
    List<Venda> searchVendasByMes(@Param("mes") Integer mes);

    @Query("SELECT v FROM Venda v WHERE v.cliente.cpf = :cpf")
    List<Venda> searchVendasByCpf(@Param("cpf") String cpf);
}
