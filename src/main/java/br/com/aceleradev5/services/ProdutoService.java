package br.com.aceleradev5.services;

import br.com.aceleradev5.dtos.ProdutoDTO;
import br.com.aceleradev5.entities.Produto;
import br.com.aceleradev5.exceptions.produto.ProdutoException;
import br.com.aceleradev5.exceptions.produto.ProdutoNotFoundException;
import br.com.aceleradev5.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    @Transactional
    public ProdutoDTO registrarProduto(ProdutoDTO produtoDTO) throws ProdutoException {

        // Valida se o codigo a ser cadastrado já existe
        boolean produtoExistente = repository.existsProdutoByCodigo(produtoDTO.getCodigo());

        if (produtoExistente) {
            throw new ProdutoException("Produto já cadastrado na base");
        }

        // Criando a entidade produto a ser inserida
        Produto produto = converterDTOParaEntidadeProduto(produtoDTO);

        // Inserindo a entidade produto na tabela produtos
        Produto produtoInserido = repository.save(produto);

        // Retornando o produto inserido
        return contruitRetornoProdutoDTO(produtoInserido);
    }


    @Transactional
    public ProdutoDTO atualizarProduto(Integer id, ProdutoDTO produtoDTO) throws ProdutoException, ProdutoNotFoundException {

        //Busca o produto pelo id e caso não encontre retorna um ProdutoNotFoundException
        Produto produto = repository.findById(id).orElseThrow(() -> new ProdutoNotFoundException("O Produto não foi encontrado"));

        // Verifica se teve alteração no codigo do produto antes de validar ele
        if (!produto.getCodigo().equals(produtoDTO.getCodigo())) {

            // Valida se o codigo a ser cadastrado já existe
            boolean produtoExistente = repository.existsProdutoByCodigo(produtoDTO.getCodigo());

            if (produtoExistente) {
                throw new ProdutoException("Codigo do produto já cadastrado na base para outro produto. ");
            }
        }

        // Modificando a entidade produto
        Produto produtoModificado = modificarProduto(produtoDTO, produto);

        // Atualizando a entidade produto na tabela produtos
        Produto produtoAtualizado = repository.save(produtoModificado);

        // Retornando o produto inserido
        return contruitRetornoProdutoDTO(produtoAtualizado);
    }

    public ProdutoDTO consultarProdutoPorId(Integer id) throws ProdutoNotFoundException {

        //Busca o produto pelo id e caso não encontre retorna um ProdutoNotFoundException
        Produto produto = repository.findById(id).orElseThrow(() -> new ProdutoNotFoundException("O Produto não foi encontrado"));

        // Retornando o produto
        return contruitRetornoProdutoDTO(produto);
    }

    public List<ProdutoDTO> consultarTodosProdutos() {
        List<ProdutoDTO> produtoDTOSList = new ArrayList<>();

        // Busca todos os produtos na tabela de produtos
        List<Produto> produtos = repository.findAll();

        // Cria uma lista com todos produtos retornados da tabela de produtos
        for (Produto produto : produtos){
            ProdutoDTO produtoDTO = contruitRetornoProdutoDTO(produto);
            produtoDTOSList.add(produtoDTO);
        }

        // Retornando a lista de produtos
        return produtoDTOSList;
    }

    @Transactional
    public void excluirProdutoPorId(Integer id) throws ProdutoNotFoundException {

        //Busca o produto pelo id e caso não encontre retorna um ProdutoNotFoundException
        Produto produto = repository.findById(id).orElseThrow(() -> new ProdutoNotFoundException("O Produto não foi encontrado"));

        // Deleta o produto
        repository.delete(produto);
    }

    private Produto converterDTOParaEntidadeProduto(ProdutoDTO produtoDTO) {
        Produto produto = new Produto();
        produto.setNome(produtoDTO.getNome());
        produto.setCodigo(produtoDTO.getCodigo());
        produto.setPreco(produtoDTO.getPreco());
        return produto;
    }

    private ProdutoDTO contruitRetornoProdutoDTO(Produto produto) {
        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setId(produto.getId());
        produtoDTO.setNome(produto.getNome());
        produtoDTO.setCodigo(produto.getCodigo());
        produtoDTO.setPreco(produto.getPreco());

        return produtoDTO;
    }

    private Produto modificarProduto(ProdutoDTO produtoDTO, Produto produto) {
        produto.setNome(produtoDTO.getNome());
        produto.setCodigo(produtoDTO.getCodigo());
        produto.setPreco(produtoDTO.getPreco());
        return produto;
    }
}
