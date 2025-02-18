CREATE TABLE Rtables (
    tID int,
    tNAME varchar(3),
    status varchar(10),
    PRIMARY KEY (tID)
);

CREATE SEQUENCE POS_rtables START WITH 1 NOCACHE ORDER;

create or replace TRIGGER Rtables_id_AUTO
BEFORE INSERT ON Rtables  
FOR EACH ROW  
WHEN (NEW.tID IS NULL)  
BEGIN 
:NEW.tID := POS_rtables.NEXTVAL; 
END; 

create or replace PROCEDURE P_Rtables
(tname Rtables.tNAME%TYPE,
tstatus Rtables.status%TYPE)
AS
 BEGIN
 INSERT INTO Rtables(tNAME,status)
 Values (tname,tstatus);
END;

CREATE TABLE Orders (
    ordersID int,
    tID int,
    FOREIGN KEY(tID) REFERENCES Rtables(tID),
    ID int,
    FOREIGN KEY(ID) REFERENCES EMPLOYEES(ID),
    mID int,
    FOREIGN KEY(mID) REFERENCES Menu_Items(mID),
    timeordering DATE,
    PRIMARY KEY (ordersID)
);

CREATE SEQUENCE POS_ORDERS START WITH 1 NOCACHE ORDER;

create or replace TRIGGER Orders_id_AUTO
BEFORE INSERT ON Orders  
FOR EACH ROW  
WHEN (NEW.ordersID IS NULL)  
BEGIN 
:NEW.ordersID := POS_ORDERS.NEXTVAL; 
END; 

create or replace PROCEDURE P_Orders
(rtID Orders.tID%TYPE,
eID Orders.ID%TYPE,
menuID Orders.mID%TYPE,
otime Orders.timeordering%TYPE)
AS
 BEGIN
 INSERT INTO Orders(tID,ID,mID,timeordering)
 Values (rtID,eID,menuID,otime);
END;


