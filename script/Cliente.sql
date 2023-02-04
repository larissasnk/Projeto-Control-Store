create table if not exists cliente(
    id       bigint primary key auto_increment,
    nome     varchar(75) not null,
    cpf      varchar(50) not null,
    email    varchar(75) not null,
    telefone varchar(50) not null,
    endereco varchar(75) not null
);