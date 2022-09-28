package com.zagvladimir.domain;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ extends com.zagvladimir.domain.BaseEntity_ {

	public static volatile SetAttribute<User, Grade> gradesFromSet;
	public static volatile SingularAttribute<User, String> userPassword;
	public static volatile SetAttribute<User, ItemLeased> itemsleased;
	public static volatile SingularAttribute<User, String> mobileNumber;
	public static volatile SetAttribute<User, Role> roles;
	public static volatile SetAttribute<User, Grade> gradesToSet;
	public static volatile SingularAttribute<User, String> userLogin;
	public static volatile SingularAttribute<User, String> phoneNumber;
	public static volatile SingularAttribute<User, Integer> locationId;
	public static volatile SingularAttribute<User, String> locationDetails;
	public static volatile SingularAttribute<User, Timestamp> registrationDate;
	public static volatile SetAttribute<User, Item> items;
	public static volatile SingularAttribute<User, String> email;
	public static volatile SingularAttribute<User, String> username;

	public static final String GRADES_FROM_SET = "gradesFromSet";
	public static final String USER_PASSWORD = "userPassword";
	public static final String ITEMSLEASED = "itemsleased";
	public static final String MOBILE_NUMBER = "mobileNumber";
	public static final String ROLES = "roles";
	public static final String GRADES_TO_SET = "gradesToSet";
	public static final String USER_LOGIN = "userLogin";
	public static final String PHONE_NUMBER = "phoneNumber";
	public static final String LOCATION_ID = "locationId";
	public static final String LOCATION_DETAILS = "locationDetails";
	public static final String REGISTRATION_DATE = "registrationDate";
	public static final String ITEMS = "items";
	public static final String EMAIL = "email";
	public static final String USERNAME = "username";

}

