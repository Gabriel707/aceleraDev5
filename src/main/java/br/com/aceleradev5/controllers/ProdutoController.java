package br.com.aceleradev5.controllers;

import br.com.aceleradev5.dtos.ProdutoDTO;
import br.com.aceleradev5.exceptions.produto.ProdutoException;
import br.com.aceleradev5.exceptions.produto.ProdutoNotFoundException;
import br.com.aceleradev5.services.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid ProdutoDTO produtoDTO) {

        try {
            ProdutoDTO response = service.registrarProduto(produtoDTO);

            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(response.getId())
                    .toUri();

            return ResponseEntity.created(uri).body(response);
        } catch (ProdutoException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody @Valid ProdutoDTO produtoDTO) {

        try {
            ProdutoDTO response = service.atualizarProduto(id, produtoDTO);
            return ResponseEntity.ok(response);
        } catch (ProdutoNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (ProdutoException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> searchById(@PathVariable Integer id) {
        try {
            ProdutoDTO response = service.consultarProdutoPorId(id);
            return ResponseEntity.ok(response);
        } catch (ProdutoNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }

    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> searchAll() {
        List<ProdutoDTO> response = service.consultarTodosProdutos();
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id) {
        try {
            service.excluirProdutoPorId(id);
            return ResponseEntity.ok().build();
        } catch (ProdutoNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
