package com.example.estudo.api.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ItensCompra.class)
public abstract class ItensCompra_ {

	public static volatile SingularAttribute<ItensCompra, Long> codigo;
	public static volatile SingularAttribute<ItensCompra, Produto> produto;
	public static volatile SingularAttribute<ItensCompra, Float> valor;
	public static volatile SingularAttribute<ItensCompra, Long> quantidade;

	public static final String CODIGO = "codigo";
	public static final String PRODUTO = "produto";
	public static final String VALOR = "valor";
	public static final String QUANTIDADE = "quantidade";

}

