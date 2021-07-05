package com.example.estudo.api.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Compra.class)
public abstract class Compra_ {

	public static volatile SingularAttribute<Compra, Long> codigo;
	public static volatile SingularAttribute<Compra, Fornecedor> fornecedor;
	public static volatile ListAttribute<Compra, ItensCompra> itensCompra;

	public static final String CODIGO = "codigo";
	public static final String FORNECEDOR = "fornecedor";
	public static final String ITENS_COMPRA = "itensCompra";

}

