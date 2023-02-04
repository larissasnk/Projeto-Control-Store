create table if not exists fornecedor(
    id          bigint primary key auto_increment,
    nome        varchar(75)  not null,
    nomeEmpresa varchar(75)  not null,
    cnpj        varchar(50)  not null unique,
    email       varchar(75)  not null,
    telefone    varchar(75)  not null,
    endereco    varchar(50)  not null,
    categoria   varchar(75)  not null,
    descricao   varchar(400) not null
);
