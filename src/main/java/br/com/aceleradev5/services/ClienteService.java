package br.com.aceleradev5.services;

import br.com.aceleradev5.dtos.ClienteDTO;
import br.com.aceleradev5.entities.Cliente;
import br.com.aceleradev5.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;


    public ClienteDTO criarCliente(ClienteDTO clienteDTO){
        Cliente cliente = new Cliente();
        cliente.setNome(clienteDTO.getNome());
        cliente.setCep(clienteDTO.getCep());
        cliente.setCpf(clienteDTO.getCpf());
        cliente.setDataNascimento(clienteDTO.getDataNascimento());


        Cliente clienteInserido = repository.save(cliente);

        ClienteDTO clienteCriado = new ClienteDTO();
        clienteCriado.setNome(clienteInserido.getNome());
        clienteCriado.setCep(clienteInserido.getCep());
        clienteCriado.setCpf(clienteInserido.getCpf());
        clienteCriado.setId(clienteInserido.getId());
        clienteCriado.setDataNascimento(clienteInserido.getDataNascimento());

        return clienteCriado;
    }




}
