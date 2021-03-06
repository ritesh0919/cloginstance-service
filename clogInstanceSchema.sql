/* Copyright (C) 2014 Covisint. All Rights Reserved. */
CREATE SCHEMA IF NOT EXISTS "SVC_CLOG_INSTANCE";
SET SCHEMA "SVC_CLOG_INSTANCE";

CREATE TABLE IF NOT EXISTS "CLOG_INSTANCE" (
    "ID" VARCHAR2(64) NOT NULL,
    "CREATION_INSTANT" NUMBER(19) NOT NULL,
    "CREATOR" VARCHAR2(64) NOT NULL,
    "CREATOR_APPLICATION_ID" VARCHAR2(64) NOT NULL,
    "VERSION" NUMBER(19) NOT NULL,
    "REALM" VARCHAR2(25) NOT NULL,
    "NAME" VARCHAR2(64) NOT NULL,
    "PLATFORM_GROUP_ID" VARCHAR2(128),
    "PLATFORM_INSTANCE_ID" VARCHAR2(25),
    "PLATFORM_SOLUTION_ID" VARCHAR2(25),
    "STATUS" VARCHAR2(25)
) AS SELECT * FROM csvread('classpath:data/cloginstance.csv');

COMMIT;