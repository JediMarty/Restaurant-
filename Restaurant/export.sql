--------------------------------------------------------
--  File created - Понеделник-Април-21-2025   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Sequence M_ID
--------------------------------------------------------

   CREATE SEQUENCE  "MARTI"."M_ID"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 99 NOCACHE  ORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence POS_EMPLO
--------------------------------------------------------

   CREATE SEQUENCE  "MARTI"."POS_EMPLO"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 24 NOCACHE  ORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence POS_ORDERS
--------------------------------------------------------

   CREATE SEQUENCE  "MARTI"."POS_ORDERS"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 343 NOCACHE  ORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence POS_ORDERS_ARCHIVE
--------------------------------------------------------

   CREATE SEQUENCE  "MARTI"."POS_ORDERS_ARCHIVE"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 181 NOCACHE  ORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence POS_POSITION
--------------------------------------------------------

   CREATE SEQUENCE  "MARTI"."POS_POSITION"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 12 NOCACHE  ORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence POS_RESERVED_TABLE
--------------------------------------------------------

   CREATE SEQUENCE  "MARTI"."POS_RESERVED_TABLE"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 33 NOCACHE  ORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence POS_RTABLES
--------------------------------------------------------

   CREATE SEQUENCE  "MARTI"."POS_RTABLES"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 470 NOCACHE  ORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence POS_TABLE_BILL
--------------------------------------------------------

   CREATE SEQUENCE  "MARTI"."POS_TABLE_BILL"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 174 NOCACHE  ORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Table EMPLOYEES
--------------------------------------------------------

  CREATE TABLE "MARTI"."EMPLOYEES" 
   (	"ID" NUMBER(*,0), 
	"FIRSTNAME" VARCHAR2(255 BYTE), 
	"LASTNAME" VARCHAR2(255 BYTE), 
	"EGN" VARCHAR2(10 BYTE), 
	"PASSWORD" VARCHAR2(256 BYTE), 
	"POS_ID" NUMBER(*,0)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Table MENU_ITEMS
--------------------------------------------------------

  CREATE TABLE "MARTI"."MENU_ITEMS" 
   (	"MID" NUMBER(*,0), 
	"ITEM" VARCHAR2(255 BYTE), 
	"PRICE" NUMBER
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Table ORDERS
--------------------------------------------------------

  CREATE TABLE "MARTI"."ORDERS" 
   (	"ORDERSID" NUMBER(*,0), 
	"TID" NUMBER(*,0), 
	"ID" NUMBER(*,0), 
	"MID" NUMBER(*,0), 
	"TIMEORDERING" TIMESTAMP (6) DEFAULT NULL, 
	"BILLID" NUMBER
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Table ORDERS_ARCHIVE
--------------------------------------------------------

  CREATE TABLE "MARTI"."ORDERS_ARCHIVE" 
   (	"ARCHIVEID" NUMBER(*,0), 
	"TID" NUMBER(*,0), 
	"ID" NUMBER(*,0), 
	"MID" NUMBER(*,0), 
	"TIMEORDERING" TIMESTAMP (6), 
	"BILLID" NUMBER(*,0), 
	"ORDERSID" NUMBER
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Table POSITIONS
--------------------------------------------------------

  CREATE TABLE "MARTI"."POSITIONS" 
   (	"POS_ID" NUMBER(*,0), 
	"POS_NAME" VARCHAR2(10 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Table RESERVED_TABLE
--------------------------------------------------------

  CREATE TABLE "MARTI"."RESERVED_TABLE" 
   (	"RTID" NUMBER(*,0), 
	"TID" NUMBER(*,0), 
	"ID" NUMBER(*,0)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Table RTABLES
--------------------------------------------------------

  CREATE TABLE "MARTI"."RTABLES" 
   (	"TID" NUMBER(*,0), 
	"TNAME" VARCHAR2(3 BYTE), 
	"STATUS" VARCHAR2(20 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Table TABLE_BILL
--------------------------------------------------------

  CREATE TABLE "MARTI"."TABLE_BILL" 
   (	"BILLID" NUMBER(*,0), 
	"BILL" NUMBER(*,0)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
REM INSERTING into MARTI.EMPLOYEES
SET DEFINE OFF;
Insert into MARTI.EMPLOYEES (ID,FIRSTNAME,LASTNAME,EGN,PASSWORD,POS_ID) values (6,'Nikolai','Ivanov','8443758994','087dc7896b97911a582702b45ff1d41ffa3e142d0b000b0fbb11058188293cfc',2);
Insert into MARTI.EMPLOYEES (ID,FIRSTNAME,LASTNAME,EGN,PASSWORD,POS_ID) values (8,'Evgeni','Minkov','8443443664','bae8b80c3e9bc802b22f87eb4e66adeb6d46a9d6237f9e6711e1f759e1ea5e1c',2);
Insert into MARTI.EMPLOYEES (ID,FIRSTNAME,LASTNAME,EGN,PASSWORD,POS_ID) values (7,'Aleks','Simeonov','8337652231','15bc0305c23e7c2129f5b25dd7ecdcc804e281ea32ae6b6161ab945a27394f94',1);
REM INSERTING into MARTI.MENU_ITEMS
SET DEFINE OFF;
Insert into MARTI.MENU_ITEMS (MID,ITEM,PRICE) values (59,'Равиоли с 4 сирена',7.8);
Insert into MARTI.MENU_ITEMS (MID,ITEM,PRICE) values (2,'Овчарски шиш',8.99);
Insert into MARTI.MENU_ITEMS (MID,ITEM,PRICE) values (3,'Картофи соте',6.99);
Insert into MARTI.MENU_ITEMS (MID,ITEM,PRICE) values (88,'Суши - авокадо маки',7.99);
Insert into MARTI.MENU_ITEMS (MID,ITEM,PRICE) values (58,'Равиоли с домат',7.6);
Insert into MARTI.MENU_ITEMS (MID,ITEM,PRICE) values (60,'Равиоли с гъби',7.6);
Insert into MARTI.MENU_ITEMS (MID,ITEM,PRICE) values (89,'Суши с краставици',7.99);
Insert into MARTI.MENU_ITEMS (MID,ITEM,PRICE) values (94,null,null);
Insert into MARTI.MENU_ITEMS (MID,ITEM,PRICE) values (92,null,null);
Insert into MARTI.MENU_ITEMS (MID,ITEM,PRICE) values (97,'Портокалов сок',2.99);
Insert into MARTI.MENU_ITEMS (MID,ITEM,PRICE) values (98,null,null);
REM INSERTING into MARTI.ORDERS
SET DEFINE OFF;
REM INSERTING into MARTI.ORDERS_ARCHIVE
SET DEFINE OFF;
Insert into MARTI.ORDERS_ARCHIVE (ARCHIVEID,TID,ID,MID,TIMEORDERING,BILLID,ORDERSID) values (160,443,6,89,to_timestamp('2025-04-01 12:00:50.000000000','RRRR-MM-DD HH24:MI:SSXFF'),162,282);
Insert into MARTI.ORDERS_ARCHIVE (ARCHIVEID,TID,ID,MID,TIMEORDERING,BILLID,ORDERSID) values (158,443,6,59,to_timestamp('2025-04-01 12:00:50.000000000','RRRR-MM-DD HH24:MI:SSXFF'),162,277);
Insert into MARTI.ORDERS_ARCHIVE (ARCHIVEID,TID,ID,MID,TIMEORDERING,BILLID,ORDERSID) values (159,443,6,89,to_timestamp('2025-04-01 12:00:50.000000000','RRRR-MM-DD HH24:MI:SSXFF'),162,279);
Insert into MARTI.ORDERS_ARCHIVE (ARCHIVEID,TID,ID,MID,TIMEORDERING,BILLID,ORDERSID) values (161,443,6,3,to_timestamp('2025-04-01 12:00:50.000000000','RRRR-MM-DD HH24:MI:SSXFF'),162,286);
Insert into MARTI.ORDERS_ARCHIVE (ARCHIVEID,TID,ID,MID,TIMEORDERING,BILLID,ORDERSID) values (162,443,6,58,to_timestamp('2025-04-01 15:04:28.000000000','RRRR-MM-DD HH24:MI:SSXFF'),163,291);
Insert into MARTI.ORDERS_ARCHIVE (ARCHIVEID,TID,ID,MID,TIMEORDERING,BILLID,ORDERSID) values (163,443,6,58,to_timestamp('2025-04-01 15:17:08.000000000','RRRR-MM-DD HH24:MI:SSXFF'),164,293);
Insert into MARTI.ORDERS_ARCHIVE (ARCHIVEID,TID,ID,MID,TIMEORDERING,BILLID,ORDERSID) values (164,443,6,58,to_timestamp('2025-04-01 15:17:08.000000000','RRRR-MM-DD HH24:MI:SSXFF'),164,295);
Insert into MARTI.ORDERS_ARCHIVE (ARCHIVEID,TID,ID,MID,TIMEORDERING,BILLID,ORDERSID) values (178,460,6,97,to_timestamp('2025-04-10 13:00:27.000000000','RRRR-MM-DD HH24:MI:SSXFF'),173,334);
Insert into MARTI.ORDERS_ARCHIVE (ARCHIVEID,TID,ID,MID,TIMEORDERING,BILLID,ORDERSID) values (180,460,6,88,to_timestamp('2025-04-10 13:00:27.000000000','RRRR-MM-DD HH24:MI:SSXFF'),173,339);
Insert into MARTI.ORDERS_ARCHIVE (ARCHIVEID,TID,ID,MID,TIMEORDERING,BILLID,ORDERSID) values (167,444,6,60,to_timestamp('2025-04-10 12:39:14.000000000','RRRR-MM-DD HH24:MI:SSXFF'),167,302);
Insert into MARTI.ORDERS_ARCHIVE (ARCHIVEID,TID,ID,MID,TIMEORDERING,BILLID,ORDERSID) values (179,460,6,97,to_timestamp('2025-04-10 13:00:27.000000000','RRRR-MM-DD HH24:MI:SSXFF'),173,336);
REM INSERTING into MARTI.POSITIONS
SET DEFINE OFF;
Insert into MARTI.POSITIONS (POS_ID,POS_NAME) values (1,'Boss');
Insert into MARTI.POSITIONS (POS_ID,POS_NAME) values (2,'Waiter');
REM INSERTING into MARTI.RESERVED_TABLE
SET DEFINE OFF;
REM INSERTING into MARTI.RTABLES
SET DEFINE OFF;
Insert into MARTI.RTABLES (TID,TNAME,STATUS) values (443,'1','Свободна');
Insert into MARTI.RTABLES (TID,TNAME,STATUS) values (445,'3','Свободна');
Insert into MARTI.RTABLES (TID,TNAME,STATUS) values (444,'2','Свободна');
Insert into MARTI.RTABLES (TID,TNAME,STATUS) values (449,'4','Свободна');
Insert into MARTI.RTABLES (TID,TNAME,STATUS) values (460,'5','Свободна');
Insert into MARTI.RTABLES (TID,TNAME,STATUS) values (461,'6','Свободна');
REM INSERTING into MARTI.TABLE_BILL
SET DEFINE OFF;
Insert into MARTI.TABLE_BILL (BILLID,BILL) values (163,3140);
Insert into MARTI.TABLE_BILL (BILLID,BILL) values (161,1474);
Insert into MARTI.TABLE_BILL (BILLID,BILL) values (162,6178);
Insert into MARTI.TABLE_BILL (BILLID,BILL) values (164,9411);
Insert into MARTI.TABLE_BILL (BILLID,BILL) values (165,5243);
Insert into MARTI.TABLE_BILL (BILLID,BILL) values (166,2090);
Insert into MARTI.TABLE_BILL (BILLID,BILL) values (167,9790);
Insert into MARTI.TABLE_BILL (BILLID,BILL) values (168,4050);
Insert into MARTI.TABLE_BILL (BILLID,BILL) values (169,9540);
Insert into MARTI.TABLE_BILL (BILLID,BILL) values (170,3881);
Insert into MARTI.TABLE_BILL (BILLID,BILL) values (171,9373);
Insert into MARTI.TABLE_BILL (BILLID,BILL) values (172,9225);
Insert into MARTI.TABLE_BILL (BILLID,BILL) values (173,5603);
--------------------------------------------------------
--  DDL for Trigger MENU_ITEMS_ID_AUTO
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "MARTI"."MENU_ITEMS_ID_AUTO" 
BEFORE INSERT ON Menu_Items  
FOR EACH ROW  
 WHEN (NEW.mID IS NULL) BEGIN 
:NEW.mID := m_ID.NEXTVAL; 
END; 
/
ALTER TRIGGER "MARTI"."MENU_ITEMS_ID_AUTO" ENABLE;
--------------------------------------------------------
--  DDL for Trigger ORDERS_ARCHIVE_ID_AUTO
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "MARTI"."ORDERS_ARCHIVE_ID_AUTO" 
BEFORE INSERT ON Orders_Archive
FOR EACH ROW  
 WHEN (NEW.archiveID IS NULL) BEGIN 
:NEW.archiveID := POS_ORDERS_ARCHIVE.NEXTVAL; 
END; 
/
ALTER TRIGGER "MARTI"."ORDERS_ARCHIVE_ID_AUTO" ENABLE;
--------------------------------------------------------
--  DDL for Trigger ORDERS_ID_AUTO
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "MARTI"."ORDERS_ID_AUTO" 
BEFORE INSERT ON Orders  
FOR EACH ROW  
 WHEN (NEW.ordersID IS NULL) BEGIN 
:NEW.ordersID := POS_ORDERS.NEXTVAL; 
END; 
/
ALTER TRIGGER "MARTI"."ORDERS_ID_AUTO" ENABLE;
--------------------------------------------------------
--  DDL for Trigger POS_ID_AUTO
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "MARTI"."POS_ID_AUTO" 
BEFORE INSERT ON EMPLOYEES  
FOR EACH ROW  
 WHEN (NEW.ID IS NULL) BEGIN 
:NEW.ID := POS_EMPLO.NEXTVAL; 
END; 
/
ALTER TRIGGER "MARTI"."POS_ID_AUTO" ENABLE;
--------------------------------------------------------
--  DDL for Trigger POSITIONS_ID_AUTO
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "MARTI"."POSITIONS_ID_AUTO" 
BEFORE INSERT ON Positions  
FOR EACH ROW  
 WHEN (NEW.Pos_id IS NULL) BEGIN 
:NEW.Pos_id := POS_position.NEXTVAL; 
END; 
/
ALTER TRIGGER "MARTI"."POSITIONS_ID_AUTO" ENABLE;
--------------------------------------------------------
--  DDL for Trigger RESERVED_TABLE_ID_AUTO
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "MARTI"."RESERVED_TABLE_ID_AUTO" 
BEFORE INSERT ON RESERVED_TABLE 
FOR EACH ROW  
 WHEN (NEW.RTID IS NULL) BEGIN 
:NEW.RTID := POS_RESERVED_TABLE.NEXTVAL; 
END;
/
ALTER TRIGGER "MARTI"."RESERVED_TABLE_ID_AUTO" ENABLE;
--------------------------------------------------------
--  DDL for Trigger RTABLES_ID_AUTO
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "MARTI"."RTABLES_ID_AUTO" 
BEFORE INSERT ON Rtables  
FOR EACH ROW  
 WHEN (NEW.tID IS NULL) BEGIN 
:NEW.tID := POS_rtables.NEXTVAL; 
END; 
/
ALTER TRIGGER "MARTI"."RTABLES_ID_AUTO" ENABLE;
--------------------------------------------------------
--  DDL for Trigger TABLEBILL_ID_AUTO
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "MARTI"."TABLEBILL_ID_AUTO" 
BEFORE INSERT ON TABLE_BILL  
FOR EACH ROW  
 WHEN (NEW.billid IS NULL) BEGIN 
:NEW.billid := POS_table_bill.NEXTVAL; 
END; 
/
ALTER TRIGGER "MARTI"."TABLEBILL_ID_AUTO" ENABLE;
--------------------------------------------------------
--  DDL for Procedure D_ALLORDERS_ARCHIVE
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "MARTI"."D_ALLORDERS_ARCHIVE" 
AS
 BEGIN 
 DELETE FROM ORDERS_ARCHIVE;
 END;

/
--------------------------------------------------------
--  DDL for Procedure D_EMPLOYEES
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "MARTI"."D_EMPLOYEES" 
	        (eID employees.ID%TYPE)
            AS
			 BEGIN 
			 DELETE FROM employees WHERE  ID = eID;
			END;

/
--------------------------------------------------------
--  DDL for Procedure D_MENU_ITEMS
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "MARTI"."D_MENU_ITEMS" 
	        (m_ID MENU_ITEMS.mID%TYPE)
            AS
			 BEGIN 
			 DELETE FROM MENU_ITEMS WHERE  mID = m_ID;
			END;

/
--------------------------------------------------------
--  DDL for Procedure D_ORDERS
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "MARTI"."D_ORDERS" 
(oID ORDERS.ORDERSID%TYPE)
AS
 BEGIN 
 DELETE FROM ORDERS WHERE  ORDERSID = oID;
END;

/
--------------------------------------------------------
--  DDL for Procedure D_ORDERS_ARCHIVE
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "MARTI"."D_ORDERS_ARCHIVE" 
(oID orders_archive.ordersid%TYPE)
  AS
    BEGIN 
    DELETE FROM ORDERS_ARCHIVE WHERE ordersid = oID;
    END;

/
--------------------------------------------------------
--  DDL for Procedure D_RESERVED_TABLE
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "MARTI"."D_RESERVED_TABLE" 
	        (Table_ID RESERVED_TABLE.TID%TYPE)
            AS
			 BEGIN 
			 DELETE FROM RESERVED_TABLE WHERE TID = Table_ID;
			END;

/
--------------------------------------------------------
--  DDL for Procedure D_RTABLES
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "MARTI"."D_RTABLES" 
	        (t_ID RTABLES.TID%TYPE)
            AS
			 BEGIN 
			 DELETE FROM RTABLES WHERE TID = t_ID;
			END;

/
--------------------------------------------------------
--  DDL for Procedure D_TABLE_BILL
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "MARTI"."D_TABLE_BILL" 
AS
 BEGIN  
 DELETE FROM TABLE_BILL;
END;

/
--------------------------------------------------------
--  DDL for Procedure P_EMPLOYEES
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "MARTI"."P_EMPLOYEES" 
(FNAME EMPLOYEES.Firstname%TYPE,
LNAME EMPLOYEES.Lastname%TYPE,
EGN EMPLOYEES.EGN%TYPE,
Pass EMPLOYEES.Password%TYPE,
PNAME EMPLOYEES.pos_id%TYPE)
AS
 BEGIN
 INSERT INTO EMPLOYEES(Firstname,Lastname,EGN,Password,pos_id)
 Values (FNAME,LNAME,EGN,Pass,PNAME);
END;

/
--------------------------------------------------------
--  DDL for Procedure P_MENU_ITEMS
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "MARTI"."P_MENU_ITEMS" 
(mitem Menu_Items.item%TYPE,
mprice Menu_Items.price%TYPE)
AS
 BEGIN
 INSERT INTO Menu_Items(item,price)
 Values (mitem,mprice);
END;

/
--------------------------------------------------------
--  DDL for Procedure P_ORDERS
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "MARTI"."P_ORDERS" 
(rtID Orders.tID%TYPE,
eID Orders.ID%TYPE,
menuID Orders.mID%TYPE,
otime Orders.timeordering%TYPE,
tbbillid orders.billid%TYPE)
AS
 BEGIN
 INSERT INTO Orders(tID,ID,mID,timeordering,billid)
 Values (rtID,eID,menuID,otime,tbbillid);
END;

/
--------------------------------------------------------
--  DDL for Procedure P_ORDERS_ARCHIVE
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "MARTI"."P_ORDERS_ARCHIVE" 
(oID orders_archive.ordersid%TYPE,
rtID Orders_Archive.tID%TYPE,
eID Orders_Archive.ID%TYPE,
menuID Orders_Archive.mID%TYPE,
otime Orders_Archive.timeordering%TYPE,
tbbillid Orders_Archive.billid%TYPE)
AS
 BEGIN
 INSERT INTO Orders_Archive(ordersid,tID,ID,mID,timeordering,billid)
 Values (oID,rtID,eID,menuID,otime,tbbillid);
END;

/
--------------------------------------------------------
--  DDL for Procedure P_POSITIONS
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "MARTI"."P_POSITIONS" 
(PNAME positions.pos_name%TYPE)
AS
 BEGIN
 INSERT INTO Positions(pos_name)
 Values (PNAME);
END;

/
--------------------------------------------------------
--  DDL for Procedure P_RESERVED_TABLE
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "MARTI"."P_RESERVED_TABLE" 
(TableID RESERVED_TABLE.TID%TYPE,
EID reserved_table.ID%TYPE)
AS
 BEGIN
 INSERT INTO RESERVED_TABLE(TID,ID)
 Values (TableID,EID);
END;

/
--------------------------------------------------------
--  DDL for Procedure P_RTABLES
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "MARTI"."P_RTABLES" 
(tname Rtables.tNAME%TYPE,
tstatus Rtables.status%TYPE)
AS
 BEGIN
 INSERT INTO Rtables(tNAME,status)
 Values (tname,tstatus);
END;

/
--------------------------------------------------------
--  DDL for Procedure PSTATUSA
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "MARTI"."PSTATUSA" 
    (t_ID RTABLES.TID%TYPE)
    AS
     BEGIN
     UPDATE RTABLES SET STATUS = 'Свободна'
     WHERE TID = t_ID;
     END;

/
--------------------------------------------------------
--  DDL for Procedure PSTATUSB
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "MARTI"."PSTATUSB" 
    (t_ID RTABLES.TID%TYPE)
    AS
     BEGIN
     UPDATE RTABLES SET STATUS = 'Затворена'
     WHERE TID = t_ID;
     END;

/
--------------------------------------------------------
--  DDL for Procedure P_TABLE_BILL
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "MARTI"."P_TABLE_BILL" 
(tbill TABLE_BILL.BILL%TYPE)
AS
 BEGIN
 INSERT INTO TABLE_BILL(BILL)
 Values (tbill);
END;

/
--------------------------------------------------------
--  DDL for Procedure UP_EMPLOYEES
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "MARTI"."UP_EMPLOYEES" 
	        (eID employees.ID%TYPE,
            fname employees.firstname%TYPE,
            lname employees.lastname%TYPE,
            eEGN employees.EGN%TYPE,
            epass employees.password%TYPE,
            idpos employees.pos_id%TYPE)
            AS
			 BEGIN 
			 UPDATE employees SET firstname = fname,
             lastname = lname,
             EGN = eEGN,
             password = epass,
             pos_id = idpos
              WHERE ID = eID;
			END;

/
--------------------------------------------------------
--  DDL for Procedure UP_ORDERS
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "MARTI"."UP_ORDERS" 
	        (oID ORDERS.ORDERSID%TYPE,
            omID orders.mid%TYPE)
            AS
			 BEGIN 
			 UPDATE ORDERS SET mid = omID
             WHERE  ORDERSID = oID;
			END;

/
--------------------------------------------------------
--  DDL for Procedure UP_ORDERS_ARCHIVE
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "MARTI"."UP_ORDERS_ARCHIVE" 
(oID orders_archive.ordersid%TYPE,
omID ORDERS_ARCHIVE.mid%TYPE)
  AS
    BEGIN
    UPDATE ORDERS_ARCHIVE SET  mid = omID
    WHERE  ordersid = oID;
    END;

/
--------------------------------------------------------
--  Constraints for Table EMPLOYEES
--------------------------------------------------------

  ALTER TABLE "MARTI"."EMPLOYEES" ADD PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
--------------------------------------------------------
--  Constraints for Table RTABLES
--------------------------------------------------------

  ALTER TABLE "MARTI"."RTABLES" ADD PRIMARY KEY ("TID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
--------------------------------------------------------
--  Constraints for Table RESERVED_TABLE
--------------------------------------------------------

  ALTER TABLE "MARTI"."RESERVED_TABLE" ADD PRIMARY KEY ("RTID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
--------------------------------------------------------
--  Constraints for Table ORDERS_ARCHIVE
--------------------------------------------------------

  ALTER TABLE "MARTI"."ORDERS_ARCHIVE" ADD PRIMARY KEY ("ARCHIVEID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
--------------------------------------------------------
--  Constraints for Table POSITIONS
--------------------------------------------------------

  ALTER TABLE "MARTI"."POSITIONS" ADD PRIMARY KEY ("POS_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
--------------------------------------------------------
--  Constraints for Table MENU_ITEMS
--------------------------------------------------------

  ALTER TABLE "MARTI"."MENU_ITEMS" ADD PRIMARY KEY ("MID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
--------------------------------------------------------
--  Constraints for Table ORDERS
--------------------------------------------------------

  ALTER TABLE "MARTI"."ORDERS" ADD PRIMARY KEY ("ORDERSID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
--------------------------------------------------------
--  Constraints for Table TABLE_BILL
--------------------------------------------------------

  ALTER TABLE "MARTI"."TABLE_BILL" ADD PRIMARY KEY ("BILLID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table EMPLOYEES
--------------------------------------------------------

  ALTER TABLE "MARTI"."EMPLOYEES" ADD FOREIGN KEY ("POS_ID")
	  REFERENCES "MARTI"."POSITIONS" ("POS_ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table ORDERS
--------------------------------------------------------

  ALTER TABLE "MARTI"."ORDERS" ADD FOREIGN KEY ("TID")
	  REFERENCES "MARTI"."RTABLES" ("TID") ENABLE;
  ALTER TABLE "MARTI"."ORDERS" ADD FOREIGN KEY ("ID")
	  REFERENCES "MARTI"."EMPLOYEES" ("ID") ENABLE;
  ALTER TABLE "MARTI"."ORDERS" ADD FOREIGN KEY ("MID")
	  REFERENCES "MARTI"."MENU_ITEMS" ("MID") ENABLE;
  ALTER TABLE "MARTI"."ORDERS" ADD FOREIGN KEY ("BILLID")
	  REFERENCES "MARTI"."TABLE_BILL" ("BILLID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table ORDERS_ARCHIVE
--------------------------------------------------------

  ALTER TABLE "MARTI"."ORDERS_ARCHIVE" ADD FOREIGN KEY ("TID")
	  REFERENCES "MARTI"."RTABLES" ("TID") ENABLE;
  ALTER TABLE "MARTI"."ORDERS_ARCHIVE" ADD FOREIGN KEY ("ID")
	  REFERENCES "MARTI"."EMPLOYEES" ("ID") ENABLE;
  ALTER TABLE "MARTI"."ORDERS_ARCHIVE" ADD FOREIGN KEY ("MID")
	  REFERENCES "MARTI"."MENU_ITEMS" ("MID") ENABLE;
  ALTER TABLE "MARTI"."ORDERS_ARCHIVE" ADD FOREIGN KEY ("BILLID")
	  REFERENCES "MARTI"."TABLE_BILL" ("BILLID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table RESERVED_TABLE
--------------------------------------------------------

  ALTER TABLE "MARTI"."RESERVED_TABLE" ADD FOREIGN KEY ("TID")
	  REFERENCES "MARTI"."RTABLES" ("TID") ENABLE;
  ALTER TABLE "MARTI"."RESERVED_TABLE" ADD FOREIGN KEY ("ID")
	  REFERENCES "MARTI"."EMPLOYEES" ("ID") ENABLE;
