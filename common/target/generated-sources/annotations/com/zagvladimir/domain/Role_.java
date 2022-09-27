package com.zagvladimir.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Role.class)
public abstract class Role_ extends com.zagvladimir.domain.BaseEntity_ {

	public static volatile SingularAttribute<Role, String> name;
	public static volatile SetAttribute<Role, User> users;

	public static final String NAME = "name";
	public static final String USERS = "users";

}

