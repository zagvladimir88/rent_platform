package com.zagvladimir.util;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.UUID;

public class UUIDGenerator {
    public static String generatedUI(){
        return UUID.randomUUID().toString();
    }
}
