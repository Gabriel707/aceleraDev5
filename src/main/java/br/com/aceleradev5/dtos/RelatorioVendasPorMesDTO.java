package br.com.aceleradev5.dtos;

import java.util.List;

public class RelatorioVendasPorMesDTO {

    private String statusVenda;

    private List<ProdutoVendidoMesDTO> listaProdutosVendidos;

    private Integer quantidadeTotalVendida;


    public RelatorioVendasPorMesDTO() {
    }

    public List<ProdutoVendidoMesDTO> getListaProdutosVendidos() {
        return listaProdutosVendidos;
    }

    public void setListaProdutosVendidos(List<ProdutoVendidoMesDTO> listaProdutosVendidos) {
        this.listaProdutosVendidos = listaProdutosVendidos;
    }

    public String getStatusVenda() {
        return statusVenda;
    }

    public void setStatusVenda(String statusVenda) {
        this.statusVenda = statusVenda;
    }

    public Integer getQuantidadeTotalVendida() {
        return quantidadeTotalVendida;
    }

    public void setQuantidadeTotalVendida(Integer quantidadeTotalVendida) {
        this.quantidadeTotalVendida = quantidadeTotalVendida;
    }
}
