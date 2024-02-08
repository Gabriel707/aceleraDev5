package br.com.aceleradev5.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "vendas")
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "cliente_id", referencedColumnName = "id")
    private Cliente cliente;
    @ManyToOne
    @JoinColumn(name = "produto_id", referencedColumnName = "id")
    private Produto produto;
    @Column(name = "quantidade_produtos_comprados", nullable = false)
    private Integer qtdProdutosComprados;
    @Column(name = "data_da_venda", nullable = false)
    private LocalDate dataDaVenda;

    public Venda() {
    }

    public Integer getQtdProdutosComprados() {
        return qtdProdutosComprados;
    }

    public void setQtdProdutosComprados(Integer qtdProdutosComprados) {
        this.qtdProdutosComprados = qtdProdutosComprados;
    }

    public LocalDate getDataDaVenda() {
        return dataDaVenda;
    }

    public void setDataDaVenda(LocalDate dataDaVenda) {
        this.dataDaVenda = dataDaVenda;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Venda venda = (Venda) o;
        return Objects.equals(id, venda.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
