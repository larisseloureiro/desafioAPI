package com.example.estudo.api.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Estoque.class)
public abstract class Estoque_ {

	public static volatile SingularAttribute<Estoque, Long> codigo;
	public static volatile SingularAttribute<Estoque, Produto> produto;
	public static volatile SingularAttribute<Estoque, Float> valorVenda;
	public static volatile SingularAttribute<Estoque, Long> quantidade;

	public static final String CODIGO = "codigo";
	public static final String PRODUTO = "produto";
	public static final String VALOR_VENDA = "valorVenda";
	public static final String QUANTIDADE = "quantidade";

}

