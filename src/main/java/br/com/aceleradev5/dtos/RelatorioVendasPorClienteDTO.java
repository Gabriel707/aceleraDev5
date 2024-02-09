package br.com.aceleradev5.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.List;

public class RelatorioVendasPorClienteDTO {
    private String nome;
    private String cpf;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
    private LocalDate dataNascimento;
    private EnderecoDTO endereco;
    List<ProdutoVendidoClienteDTO> listaProdutosCompradosUsuario;

    public RelatorioVendasPorClienteDTO() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public EnderecoDTO getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoDTO endereco) {
        this.endereco = endereco;
    }

    public List<ProdutoVendidoClienteDTO> getListaProdutosCompradosUsuario() {
        return listaProdutosCompradosUsuario;
    }

    public void setListaProdutosCompradosUsuario(List<ProdutoVendidoClienteDTO> listaProdutosCompradosUsuario) {
        this.listaProdutosCompradosUsuario = listaProdutosCompradosUsuario;
    }
}
