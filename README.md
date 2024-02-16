Projeto Acelera DEV - Grupo 5 - API de controle de venda e estoque.

Após clonar o projeto, verifique se todas as dependencias estarão instaladas.

No terminal caso tenha problemas, verifique a instalação do maven e possiveis problemas de dependencias

**$ mvn clean install**

O comando acima pode ajudar.

Ao subir a aplicação, siga os passos a seguir:

1. H2 console login:

**http://localhost:8080/h2-console/**

2. Verificar no arquivo application.properties por login e senha:

**username=sa**

**password=password**



Endpoints: 

**http://localhost:8080/clientes**

**http://localhost:8080/produtos**

**http://localhost:8080/estoques/2/produtos**


**OBS: Vale lembrar que a aplicação está rodando com banco em memória, ou seja, qualquer queda no sistema, os dados – volateis – serão perdidos, e uma nova repopulação deverá ser feita no banco de dados.**

Output esperado ao importar a planilha:

{

    "mensagemProcessamento": "Planilha de vendas importada com sucesso"

}

TODAS as funções do CRUS estarão feitas no nas coleções importadas. 

Caso queria testar se as mesmas funcionam:

CRIAR CLIENTE: POST

Endpoint -> http://localhost:8080/clientes

**{
    "nome": "NOME DO CLIENTE",
    "cep": "00000-000",
    "cpf": "888.873.440-60",
    "dataNascimento": "03/02/1998"
}**

ATUALIZAR CLIENTE: PUT

Endpouint -> http://localhost:8080/clientes/<id>

**{
    "nome": "Luciana Santana Ribeiro",
    "cep": "71906-115",
    "cpf": "102.742.456-30",
    "dataNascimento": "03/02/1985"
}**



DELETAR CLIENTE DA BASE PELO ID: DEL

Endpoint -> http://localhost:8080/clientes/<id>


##################################################



CRIA PRODUTO: PUT

Endpoint -> http://localhost:8080/produtos

**{
    "codigo": <codigo desejado>,
    "nome": "Nome do produto",
    "preco": 75.00
}**


DELETAR PRODUTO PELO ID:

http://localhost:8080/produtos/<id do produto>


##################################################


CRIAR A QUANTIDADE DE PRODUTOS PELO ID -> NO ESTOQUE:

Endpoint -> http://localhost:8080/estoques/<id do produto>/produtos
**{
    "qtdDisponivel": 5,
    "dataEstoque": "05/01/2024"
}**
