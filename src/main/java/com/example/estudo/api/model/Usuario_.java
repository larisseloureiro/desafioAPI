package com.example.estudo.api.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Usuario.class)
public abstract class Usuario_ {

	public static volatile ListAttribute<Usuario, Perfil> perfis;
	public static volatile SingularAttribute<Usuario, String> senha;
	public static volatile SingularAttribute<Usuario, Long> codigo;
	public static volatile SingularAttribute<Usuario, String> nome;
	public static volatile SingularAttribute<Usuario, String> email;

	public static final String PERFIS = "perfis";
	public static final String SENHA = "senha";
	public static final String CODIGO = "codigo";
	public static final String NOME = "nome";
	public static final String EMAIL = "email";

}

