package br.com.aceleradev5.dtos;

import java.util.List;

public class ProdutoVendidoMesDTO {

    private Long codigoProduto;
    private List<DetalheVendaDTO> listaDetalheVenda;

    public ProdutoVendidoMesDTO() {
    }

    public ProdutoVendidoMesDTO(Long codigoProduto, List<DetalheVendaDTO> listaDetalheVenda) {
        this.codigoProduto = codigoProduto;
        this.listaDetalheVenda = listaDetalheVenda;
    }

    public List<DetalheVendaDTO> getListaDetalheVenda() {
        return listaDetalheVenda;
    }

    public void setListaDetalheVenda(List<DetalheVendaDTO> listaDetalheVenda) {
        this.listaDetalheVenda = listaDetalheVenda;
    }

    public Long getCodigoProduto() {
        return codigoProduto;
    }

    public void setCodigoProduto(Long codigoProduto) {
        this.codigoProduto = codigoProduto;
    }
}
