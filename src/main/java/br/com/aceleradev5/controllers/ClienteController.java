package br.com.aceleradev5.controllers;

import br.com.aceleradev5.dtos.ClienteDTO;
import br.com.aceleradev5.exceptions.cliente.ClienteException;
import br.com.aceleradev5.exceptions.cliente.ClienteNotFoundException;
import br.com.aceleradev5.services.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid ClienteDTO clienteDTO) {

        try {
            ClienteDTO response = service.registrarCliente(clienteDTO);

            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(response.getId())
                    .toUri();

            return ResponseEntity.created(uri).body(response);
        } catch (ClienteException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody @Valid ClienteDTO clienteDTO) {

        try {
            ClienteDTO response = service.atualizarCliente(id, clienteDTO);
            return ResponseEntity.ok(response);
        } catch (ClienteNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (ClienteException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> searchById(@PathVariable Integer id) {
        try {
            ClienteDTO response = service.consultarClientePorId(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> searchAll() {
        List<ClienteDTO> response = service.consultarTodosClientes();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id) {
        try {
            service.excluirClientePorId(id);
            return ResponseEntity.ok().build();
        } catch (ClienteNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
