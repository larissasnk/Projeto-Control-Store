CREATE TABLE venda_item(
    venda_id   int            not null,
    produto_id bigint         not null,
    quantidade int            not null,
    total      decimal(10, 2) not null,
    desconto   decimal(10, 2),
    constraint fk_venda_item_venda foreign key (venda_id) references venda (id),
    constraint fk_venda_item_produto foreign key (produto_id) references produto (id_prod)
);