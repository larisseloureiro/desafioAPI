package com.example.estudo.api.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Fornecedor.class)
public abstract class Fornecedor_ {

	public static volatile SingularAttribute<Fornecedor, Long> codigo;
	public static volatile SingularAttribute<Fornecedor, String> telefone;
	public static volatile SingularAttribute<Fornecedor, Endereco> endereco;
	public static volatile SingularAttribute<Fornecedor, String> nome;
	public static volatile SingularAttribute<Fornecedor, String> cnpj;
	public static volatile SingularAttribute<Fornecedor, String> email;

	public static final String CODIGO = "codigo";
	public static final String TELEFONE = "telefone";
	public static final String ENDERECO = "endereco";
	public static final String NOME = "nome";
	public static final String CNPJ = "cnpj";
	public static final String EMAIL = "email";

}

