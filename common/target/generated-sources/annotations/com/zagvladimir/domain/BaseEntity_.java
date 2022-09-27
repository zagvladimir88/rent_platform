package com.zagvladimir.domain;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(BaseEntity.class)
public abstract class BaseEntity_ {

	public static volatile SingularAttribute<BaseEntity, Timestamp> modificationDate;
	public static volatile SingularAttribute<BaseEntity, Long> id;
	public static volatile SingularAttribute<BaseEntity, Timestamp> creationDate;
	public static volatile SingularAttribute<BaseEntity, Status> status;

	public static final String MODIFICATION_DATE = "modificationDate";
	public static final String ID = "id";
	public static final String CREATION_DATE = "creationDate";
	public static final String STATUS = "status";

}

