create table if not exists caixa(
    id_movimentacao int primary key auto_increment,
    valor_diario    decimal(10, 2) null,
    tipo            varchar(50)    null,
    data_inicial    date           null,
    data_final      date           null,
    valor_mensal    decimal(10, 2) null
);