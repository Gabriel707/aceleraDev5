package br.com.aceleradev5.services;

import br.com.aceleradev5.dtos.ClienteDTO;
import br.com.aceleradev5.entities.Cliente;
import br.com.aceleradev5.exceptions.cliente.ClienteException;
import br.com.aceleradev5.exceptions.cliente.ClienteNotFoundException;
import br.com.aceleradev5.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    @Transactional
    public ClienteDTO registrarCliente(ClienteDTO clienteDTO) throws ClienteException {


        // Valida se o cpf a ser cadastrado já existe
        boolean cpfExistente = repository.existsClienteByCpf(clienteDTO.getCpf());

        if (cpfExistente) {
            throw new ClienteException("CPF já cadastrado na base");
        }

        // Criando a entidade cliente a ser inserida
        Cliente cliente = converterDTOParaEntidadeCliente(clienteDTO);

        // Inserindo a entidade cliente na tabela clientes
        Cliente clienteInserido = repository.save(cliente);

        // Retornando o cliente inserido
        return construirRetornoClienteDTO(clienteInserido);
    }


    @Transactional
    public ClienteDTO atualizarCliente(Integer id, ClienteDTO clienteDTO) throws ClienteException, ClienteNotFoundException {

        //Busca o cliente pelo id e caso não encontre retorna um ClienteNotFoundException
        Cliente cliente = repository.findById(id).orElseThrow(() -> new ClienteNotFoundException("O Cliente não foi encontrado"));

        // Verifica se teve alteração no cpf antes de validar ele
        if (!cliente.getCpf().equals(clienteDTO.getCpf())) {

            // Valida se o cpf a ser modificado já existe na base, caso esteja cadastrado para outro cliente lança um ClienteException
            boolean cpfExistente = repository.existsClienteByCpf(clienteDTO.getCpf());

            if (cpfExistente) {
                throw new ClienteException("CPF já cadastrado na base para outro cliente.");
            }
        }

        // Modificando a entidade cliente
        Cliente clienteModificado = modificarCliente(clienteDTO, cliente);

        // Atualizando a entidade cliente na tabela clientes
        Cliente clienteAtualizado = repository.save(clienteModificado);

        // Retornando o cliente inserido
        return construirRetornoClienteDTO(clienteAtualizado);
    }

    public ClienteDTO consultarClientePorId(Integer id) throws ClienteNotFoundException {
        
        //Busca o cliente pelo id e caso não encontre retorna um ClienteNotFoundException
        Cliente cliente = repository.findById(id).orElseThrow(() -> new ClienteNotFoundException("O Cliente não foi encontrado"));

        // Retornando o cliente
        return construirRetornoClienteDTO(cliente);
    }

    public List<ClienteDTO> consultarTodosClientes() {
        List<ClienteDTO> clientesDTO = new ArrayList<>();

        // Busca todos os clientes na tabela de clientes
        List<Cliente> clientes = repository.findAll();

        // Cria uma lista com todos clientes retornados da tabela de clientes
        for (Cliente cliente : clientes) {
            ClienteDTO clienteDTO = construirRetornoClienteDTO(cliente);
            clientesDTO.add(clienteDTO);
        }

        // Retornando a lista de clientes
        return clientesDTO;
    }

    @Transactional
    public void excluirClientePorId(Integer id) throws ClienteNotFoundException {
        //Busca o cliente pelo id e caso não encontre retorna um ClienteNotFoundException
        Cliente cliente = repository.findById(id).orElseThrow(() -> new ClienteNotFoundException("O Cliente não foi encontrado"));

        // Deleta o cliente
        repository.delete(cliente);
    }

    private Cliente converterDTOParaEntidadeCliente(ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente();
        cliente.setNome(clienteDTO.getNome());
        cliente.setCep(clienteDTO.getCep());
        cliente.setCpf(clienteDTO.getCpf());
        cliente.setDataNascimento(clienteDTO.getDataNascimento());
        return cliente;
    }

    private ClienteDTO construirRetornoClienteDTO(Cliente cliente) {
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setNome(cliente.getNome());
        clienteDTO.setCep(cliente.getCep());
        clienteDTO.setCpf(cliente.getCpf());
        clienteDTO.setId(cliente.getId());
        clienteDTO.setDataNascimento(cliente.getDataNascimento());

        return clienteDTO;
    }

    private Cliente modificarCliente(ClienteDTO clienteDTO, Cliente cliente) {
        cliente.setCep(clienteDTO.getCep());
        cliente.setNome(clienteDTO.getNome());
        cliente.setCpf(clienteDTO.getCpf());
        cliente.setDataNascimento(clienteDTO.getDataNascimento());
        return cliente;
    }
}
