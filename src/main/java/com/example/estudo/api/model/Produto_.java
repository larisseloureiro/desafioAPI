package com.example.estudo.api.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Produto.class)
public abstract class Produto_ {

	public static volatile SingularAttribute<Produto, Long> codigo;
	public static volatile SingularAttribute<Produto, String> nome;
	public static volatile SingularAttribute<Produto, String> unidade;
	public static volatile SingularAttribute<Produto, String> descricao;

	public static final String CODIGO = "codigo";
	public static final String NOME = "nome";
	public static final String UNIDADE = "unidade";
	public static final String DESCRICAO = "descricao";

}

