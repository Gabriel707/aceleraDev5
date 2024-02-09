package br.com.aceleradev5.services;

import br.com.aceleradev5.dtos.EstoqueDTO;
import br.com.aceleradev5.dtos.ProdutoDTO;
import br.com.aceleradev5.entities.Estoque;
import br.com.aceleradev5.entities.Produto;
import br.com.aceleradev5.exceptions.produto.ProdutoNotFoundException;
import br.com.aceleradev5.repositories.EstoqueRepository;
import br.com.aceleradev5.repositories.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstoqueService {

    @Autowired
    private EstoqueRepository estoqueRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public EstoqueDTO incluirEstoqueProduto(Integer produtoId, EstoqueDTO estoqueDTO) throws ProdutoNotFoundException {

        // Busca o produto pelo id e caso não encontre retorna um ProdutoNotFoundException
        Produto produto = produtoRepository.findById(produtoId).orElseThrow(() -> new ProdutoNotFoundException("O Produto não foi encontrado"));

        // Cria a entidade estoque a ser inserida
        Estoque estoque = converterDTOEmEntidadeEstoque(estoqueDTO, produto);

        // Insere o estoque na tabela estoque
        Estoque estoqueInserido = estoqueRepository.save(estoque);

        // Constroi o retorno do estoque (EstoqueDTO)
        return construirRetornoEstoqueDTO(estoqueInserido);
    }

    private Estoque converterDTOEmEntidadeEstoque(EstoqueDTO estoqueDTO, Produto produto) {
        Estoque estoque = new Estoque();
        estoque.setProduto(produto);
        estoque.setDataEstoque(estoqueDTO.getDataEstoque());
        estoque.setQtdDisponivel(estoqueDTO.getQtdDisponivel());
        return estoque;
    }

    private EstoqueDTO construirRetornoEstoqueDTO(Estoque estoque) {
        EstoqueDTO estoqueDTO = new EstoqueDTO();
        estoqueDTO.setId(estoque.getId());
        estoqueDTO.setDataEstoque(estoque.getDataEstoque());
        estoqueDTO.setQtdDisponivel(estoque.getQtdDisponivel());
        estoqueDTO.setProduto(converterEntidadeProdutoEmDTO(estoque.getProduto()));
        return estoqueDTO;
    }

    private ProdutoDTO converterEntidadeProdutoEmDTO(Produto produto) {
        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setId(produto.getId());
        produtoDTO.setCodigo(produto.getCodigo());
        produtoDTO.setNome(produto.getNome());
        produtoDTO.setPreco(produto.getPreco());
        return produtoDTO;
    }
}
