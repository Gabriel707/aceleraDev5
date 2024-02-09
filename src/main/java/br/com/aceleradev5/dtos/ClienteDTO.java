package br.com.aceleradev5.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Objects;

public class ClienteDTO {

    private Integer id;

    @NotNull(message = "O campo 'nome' é obrigatório")
    @NotBlank(message = "O campo 'nome' é obrigatório")
    private String nome;

    @NotNull(message = "O campo 'cpf' é obrigatório")
    @NotBlank(message = "O campo 'cpf' é obrigatório")
    private String cpf;

    @NotNull(message = "O campo 'cep' é obrigatório")
    @NotBlank(message = "O campo 'cep' é obrigatório")
    private String cep;

    @NotNull(message = "O campo 'dataNascimento' é obrigatório")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
    private LocalDate dataNascimento;

    public ClienteDTO() {
    }

    public ClienteDTO(String nome, String cpf, String cep, LocalDate dataNascimento) {
        this.nome = nome;
        this.cpf = cpf;
        this.cep = cep;
        this.dataNascimento = dataNascimento;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClienteDTO cliente = (ClienteDTO) o;
        return Objects.equals(id, cliente.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


}
