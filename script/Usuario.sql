create table if not exists usuario(
    id       bigint primary key auto_increment,
    nome     varchar(75)  not null,
    usuario  varchar(50)  not null unique,
    senha    varchar(75)  not null,
    telefone varchar(75)  not null,
    endereco varchar(100) not null,
    cargo    varchar(10)  not null
);