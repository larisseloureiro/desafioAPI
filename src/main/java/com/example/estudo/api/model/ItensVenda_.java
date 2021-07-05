package com.example.estudo.api.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ItensVenda.class)
public abstract class ItensVenda_ {

	public static volatile SingularAttribute<ItensVenda, Long> codigo;
	public static volatile SingularAttribute<ItensVenda, Produto> produto;
	public static volatile SingularAttribute<ItensVenda, Float> valor;
	public static volatile SingularAttribute<ItensVenda, Long> quantidade;

	public static final String CODIGO = "codigo";
	public static final String PRODUTO = "produto";
	public static final String VALOR = "valor";
	public static final String QUANTIDADE = "quantidade";

}

