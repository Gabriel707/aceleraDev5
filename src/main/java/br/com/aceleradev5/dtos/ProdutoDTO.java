package br.com.aceleradev5.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Objects;

public class ProdutoDTO {

    private Integer id;

    @NotNull(message = "O campo 'codigo' é obrigatório")
    private Long codigo;

    @NotNull(message = "O campo 'nome' é obrigatório")
    @NotBlank(message = "O campo 'nome' é obrigatório")
    private String nome;

    @NotNull(message = "O campo 'preco' é obrigatório")
    @DecimalMin(value = "0", inclusive = false, message = "O campo 'preco' deve ser maior que zero")
    private BigDecimal preco;

    public ProdutoDTO() {
    }

    public ProdutoDTO(Long codigo, String nome, BigDecimal preco) {
        this.codigo = codigo;
        this.nome = nome;
        this.preco = preco;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProdutoDTO produto = (ProdutoDTO) o;
        return Objects.equals(id, produto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
