package br.com.aceleradev5.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Objects;

public class EstoqueDTO {

    private Integer id;

    @NotNull(message = "O campo 'qtdDisponivel' é obrigatório")
    private Integer qtdDisponivel;

    @NotNull(message = "O campo 'dataEstoque' é obrigatório")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
    private LocalDate dataEstoque;

    private ProdutoDTO produto;

    public EstoqueDTO(Integer qtdDisponivel, LocalDate dataEstoque) {
        this.qtdDisponivel = qtdDisponivel;
        this.dataEstoque = dataEstoque;
    }

    public EstoqueDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQtdDisponivel() {
        return qtdDisponivel;
    }

    public void setQtdDisponivel(Integer qtdDisponivel) {
        this.qtdDisponivel = qtdDisponivel;
    }

    public LocalDate getDataEstoque() {
        return dataEstoque;
    }

    public void setDataEstoque(LocalDate dataEstoque) {
        this.dataEstoque = dataEstoque;
    }

    public ProdutoDTO getProduto() {
        return produto;
    }

    public void setProduto(ProdutoDTO produto) {
        this.produto = produto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EstoqueDTO estoque = (EstoqueDTO) o;
        return Objects.equals(id, estoque.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
