create table if not exists produto(
    id_prod            bigint primary key auto_increment,
    nome_prod          varchar(75)    not null,
    quantidade_prod    int            null,
    estoqueMinimo_prod int            null,
    precoCusto_prod    decimal(10, 2) not null,
    lucro_prod         decimal(10, 2) not null,
    precoVenda_prod    decimal(10, 2) not null,
    marca_prod         varchar(100)   not null,
    descricao_prod     varchar(400),
    id_categoria       int            not null,
    id_fornecedor      bigint         not null,
    data_hora_compra   datetime default current_timestamp,
    constraint produto_categoria foreign key produto_categoria (id_categoria) references categoria (id_categoria),
    constraint produto_fornecedor foreign key produto_fornecedor (id_fornecedor) references fornecedor (id)
);