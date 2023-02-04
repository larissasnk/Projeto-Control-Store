create table if not exists categoria(
    id_categoria        int primary key auto_increment,
    nome_categoria      varchar(75),
    descricao_categoria varchar(100)
);