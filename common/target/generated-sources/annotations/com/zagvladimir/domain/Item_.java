package com.zagvladimir.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Item.class)
public abstract class Item_ extends com.zagvladimir.domain.BaseEntity_ {

	public static volatile SingularAttribute<Item, User> owner;
	public static volatile SingularAttribute<Item, String> itemName;
	public static volatile SingularAttribute<Item, SubItemType> subItemType;
	public static volatile SingularAttribute<Item, Boolean> available;
	public static volatile SingularAttribute<Item, String> description;
	public static volatile SingularAttribute<Item, Double> pricePerHour;
	public static volatile SingularAttribute<Item, Location> location;
	public static volatile SingularAttribute<Item, String> itemLocation;

	public static final String OWNER = "owner";
	public static final String ITEM_NAME = "itemName";
	public static final String SUB_ITEM_TYPE = "subItemType";
	public static final String AVAILABLE = "available";
	public static final String DESCRIPTION = "description";
	public static final String PRICE_PER_HOUR = "pricePerHour";
	public static final String LOCATION = "location";
	public static final String ITEM_LOCATION = "itemLocation";

}

