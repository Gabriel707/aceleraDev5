package br.com.aceleradev5.controllers;

import br.com.aceleradev5.dtos.ErrorDTO;
import br.com.aceleradev5.dtos.EstoqueDTO;
import br.com.aceleradev5.exceptions.produto.ProdutoNotFoundException;
import br.com.aceleradev5.services.EstoqueService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/estoques")
public class EstoqueController {

    @Autowired
    private EstoqueService service;

    @PostMapping("/{produtoId}/produtos")
    public ResponseEntity<?> create(@PathVariable Integer produtoId, @RequestBody @Valid EstoqueDTO estoqueDTO) {
        try {
            EstoqueDTO response = service.incluirEstoqueProduto(produtoId, estoqueDTO);
            return ResponseEntity.ok(response);
        } catch (ProdutoNotFoundException e) {
            return ResponseEntity.internalServerError().body((new ErrorDTO(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())));
        }
    }
}
