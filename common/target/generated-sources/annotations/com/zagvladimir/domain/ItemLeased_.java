package com.zagvladimir.domain;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ItemLeased.class)
public abstract class ItemLeased_ extends com.zagvladimir.domain.BaseEntity_ {

	public static volatile SingularAttribute<ItemLeased, Timestamp> timeFrom;
	public static volatile SingularAttribute<ItemLeased, Long> itemId;
	public static volatile SingularAttribute<ItemLeased, String> rentierGradeDescription;
	public static volatile SingularAttribute<ItemLeased, Timestamp> timeTo;
	public static volatile SingularAttribute<ItemLeased, String> renterGradeDescription;
	public static volatile SingularAttribute<ItemLeased, Double> fee;
	public static volatile SingularAttribute<ItemLeased, Double> priceTotal;
	public static volatile SingularAttribute<ItemLeased, Double> pricePerHour;
	public static volatile SingularAttribute<ItemLeased, Double> discount;
	public static volatile SingularAttribute<ItemLeased, User> renter;

	public static final String TIME_FROM = "timeFrom";
	public static final String ITEM_ID = "itemId";
	public static final String RENTIER_GRADE_DESCRIPTION = "rentierGradeDescription";
	public static final String TIME_TO = "timeTo";
	public static final String RENTER_GRADE_DESCRIPTION = "renterGradeDescription";
	public static final String FEE = "fee";
	public static final String PRICE_TOTAL = "priceTotal";
	public static final String PRICE_PER_HOUR = "pricePerHour";
	public static final String DISCOUNT = "discount";
	public static final String RENTER = "renter";

}

