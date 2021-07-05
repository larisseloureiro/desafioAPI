package com.example.estudo.api.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Venda.class)
public abstract class Venda_ {

	public static volatile SingularAttribute<Venda, Cliente> cliente;
	public static volatile SingularAttribute<Venda, Long> codigo;
	public static volatile ListAttribute<Venda, ItensVenda> itensVenda;

	public static final String CLIENTE = "cliente";
	public static final String CODIGO = "codigo";
	public static final String ITENS_VENDA = "itensVenda";

}

