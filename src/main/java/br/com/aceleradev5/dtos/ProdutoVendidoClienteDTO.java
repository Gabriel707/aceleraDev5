package br.com.aceleradev5.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class ProdutoVendidoClienteDTO {

    private Long codigoProduto;
    private String nome;
    private Integer quantidadeProdutoComprado;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
    private LocalDate dataCompra;

    public ProdutoVendidoClienteDTO() {
    }

    public Long getCodigoProduto() {
        return codigoProduto;
    }

    public void setCodigoProduto(Long codigoProduto) {
        this.codigoProduto = codigoProduto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getQuantidadeProdutoComprado() {
        return quantidadeProdutoComprado;
    }

    public void setQuantidadeProdutoComprado(Integer quantidadeProdutoComprado) {
        this.quantidadeProdutoComprado = quantidadeProdutoComprado;
    }

    public LocalDate getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(LocalDate dataCompra) {
        this.dataCompra = dataCompra;
    }
}
