# Projeto-Control-Store

![capa](https://user-images.githubusercontent.com/94148400/216741092-0082aa2b-cf03-49ce-bc8c-cf66784bb8f3.jpg)

[![NPM](https://img.shields.io/npm/l/react)]([https://github.com/devsuperior/sds1-wmazoni/blob/master/LICENSE](https://github.com/larissasnk4/Projeto-Control-Store/blob/main/LICENCE))



##  Sobre o projeto

Projeto elaborado por Larissa Souza e Maicon Douglas.

Control Store é uma aplicação Java com objetivo realizar e consultar vendas e controlar o estoque de produtos de uma loja de utilidades. Foi construído como projeto de conclusão da matéria de LPOO orientado pelo professor Woquiton Fernandes (https://github.com/Woquiton) do Instituto Federal Baiano - Campus Guanambi no curso de Análise e Desenvolvimento de Sistemas.

## Tecnologias utilizadas

<table>
  <tr>
    <td>Linguagem</td>
    <td>IDE</td>
    <td>Banco de Dados</td>
  </tr>
   <tr>
    <td>Java</td>
    <td>Intellij</td>
    <td>MySQL</td>
  </tr>
</table>

## Como executar o sistema

1. Será necessário utilizar o script do banco de dados para criar a base de dados chamada 'loja' do sistema.

> lembre-se que para fazer a conexão com o banco de dados a URL terá que conter o mesmo nome da base de dados criada, conforme o código abaixo.

```
    private static final String URL_MYSQL = "jdbc:mysql://localhost/loja";
    private static final String DRIVER_CLASS_MYSQL = "com.mysql.cj.jdbc.Driver";
    private static final String USER = "root";
    private static final String PASS = "";
```
2. Em seguida, com a base de dados criada, será preciso utilizar o script.sql (que está dentro da pasta "script" no pacote do projeto) para a criação das tabelas do banco de dados.

3. Com as tabelas criadas, agora é necessario criar o usuario chefe, no qual será a pessoa que poderá criar outros usuarios (funcionarios) no sistema.
> Para adicionar o usuario que irá logar no sistema, será preciso inserir no banco de dados, conforme o código abaixo.

```
INSERT INTO usuario (nome, usuario, senha, telefone, endereco, cargo) VALUES
 ('Larissa', 'larissa', '1234','(19) 99900-0000','Guanambi-BA', 'Chefe');
```
#### Os campos inseridos podem variar de acordo com a preferência do usuario
+ **nome:**  nome da pessoa.
+ **usuario:**  nome que será utilizado para logar no sistema
+ **senha:**  senha que será utilizada para logar no sistema
+ **telefone:**  telefone da pessoa.
+ **endereco:**  endereço da pessoa.
+ **cargo:**  cargo ocupada na empresa.

4.Por fim, com as informações do usuario registradas no banco de dados e importar o projeto na IDE, basta executar a classe "Main" e inserir o usuario e a senha registrados no banco de dados.

## Demonstração do sistema

![Design sem nome](https://user-images.githubusercontent.com/94148400/216741215-5babb6db-059c-434e-af1c-384866bc8818.gif)