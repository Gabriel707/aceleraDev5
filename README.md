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
