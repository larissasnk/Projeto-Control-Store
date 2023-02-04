create table if not exists venda(
    id                int primary key auto_increment,
    total_venda       decimal(10, 2) not null,
    valor_pago        decimal(10, 2) not null,
    troco             decimal(10, 2),
    desconto          decimal(10, 2),
    cliente_id        bigint         not null,
    usuario_id        bigint         not null,
    data_hora_criacao datetime default current_timestamp,
    constraint fk_venda_cliente foreign key fk_venda_cliente (cliente_id) references cliente (id),
    constraint fk_venda_usuario foreign key fk_venda_usuario (usuario_id) references usuario (id)
);