package br.com.aceleradev5.services;

import br.com.aceleradev5.dtos.EnderecoDTO;
import br.com.aceleradev5.exceptions.cliente.CepNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CepService {

    private static final String URL_VIACEP = "https://viacep.com.br/ws/{cep}/json/";

    @Autowired
    private RestTemplate restTemplate;

    public EnderecoDTO consultaCepCliente(String cep) throws CepNotFoundException {
        EnderecoDTO enderecoDTO = restTemplate.getForObject(URL_VIACEP, EnderecoDTO.class, cep);

        if (enderecoDTO == null) {
            throw new CepNotFoundException(String.format("O cep informado %s não foi encontrado na api viaCEP.", cep));
        }

        return enderecoDTO;
    }
}
