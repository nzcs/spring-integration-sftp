CREATE SEQUENCE SLACCT_CODE_TABLES_SEQ
    MINVALUE 1
    MAXVALUE 9999999999999999
    START WITH 2
    INCREMENT BY 50 CACHE 100;


CREATE TABLE "ROUTING_TABLE_LOAD"
(
    "ID"         NUMBER(9, 0) NOT NULL PRIMARY KEY,
    "VERSION"    NUMBER(9, 0),
    "VALID_FROM" DATE,
    "LOAD_TIME"  TIMESTAMP
);


CREATE TABLE "ROUTING_TABLE"
(
    "ID"                     NUMBER(9, 0) NOT NULL PRIMARY KEY,
    "ROUTING_TABLE_LOAD_ID"  NUMBER(9, 0),
    "GIRO_CODE"              NUMBER(8, 0),
    "VIBER_SIGN"             VARCHAR2(1),
    "VIBER_BIC"              VARCHAR2(11),
    "GIRO_ACCOUNT_NO_LENGTH" NUMBER(2, 0),
    "GIRO_BANK"              VARCHAR2(40),
    "GIRO_ADDRESS"           VARCHAR2(50),
    "GIRO_NATURE"            VARCHAR2(1),
    "GIRO_DIRECT"            VARCHAR2(8),
    "GIRO_GID"               VARCHAR2(4),
    "VIBER_TANDT"            VARCHAR2(11),
    "VIBER_SEND"             VARCHAR2(1),
    "VIBER_RECEIVE"          VARCHAR2(1),
    "AFR_NATURE"             VARCHAR2(1),
    "AFR_DIRECT_BIC"         VARCHAR2(11),
    "AFR_INDIRECT_BIC"       VARCHAR2(11),
    "PRIMARY_BANK_CODE"      NUMBER(3, 0),

    CONSTRAINT FK_ROUTING_TABLE_1 FOREIGN KEY (ROUTING_TABLE_LOAD_ID) REFERENCES ROUTING_TABLE_LOAD (ID)
);



CREATE TABLE "BIC_INFO_LOAD"
(
    "ID"        NUMBER(9, 0) NOT NULL PRIMARY KEY,
    "VERSION"   NUMBER(9, 0),
    "LOAD_TIME" DATE
);

CREATE TABLE "BIC_INFO"
(
    "ID"               NUMBER(9, 0) NOT NULL PRIMARY KEY,
    "BIC_INFO_LOAD_ID" NUMBER(9, 0),
    "MONTH"            DATE,
    "BIC8"             VARCHAR2(8),
    "BIC"              VARCHAR2(11),
    "INSTITUTION"      VARCHAR2(200),
    "CITY"             VARCHAR2(200),
    "ISO_COUNTRY_CODE" VARCHAR2(2),

    CONSTRAINT FK_BIC_INFO_1 FOREIGN KEY (BIC_INFO_LOAD_ID) REFERENCES BIC_INFO_LOAD (ID)
)
--     partition by range ("MONTH") interval (NUMTOYMINTERVAL(1, 'MONTH'))
-- (
--     partition p0 values less than (to_date('01/01/2019', 'dd/mm/yyyy'))
-- );
