CREATE TABLE Users (
    UserID long auto_increment,
    First_Name varchar(255) NOT NULL,
    Last_Name varchar(255) NOT NULL,
    User_Name varchar(255) NOT NULL,
    Lock boolean,
    Locked_Date datetime NULL,
    PRIMARY KEY (UserID)
);

CREATE TABLE Pricing (
    PricingID long auto_increment,
    Trading_Pair varchar(255) NOT NULL,
    Ask_Price double,
    Bid_Price double,
    PRIMARY KEY (PricingID)
);

CREATE TABLE Wallet (
	UserID long NOT NULL,
	WalletID long auto_increment,
	Crypto_Currency varchar(255) NOT NULL,
	Amount double NOT NULL,
	PRIMARY KEY (WalletID)
);

CREATE TABLE Transaction_History (
	UserID long NOT NULL,
	TransactionID long auto_increment,
	Transaction_Date datetime NOT NULL,
	Bought_Currency varchar(255) NOT NULL,
	Sold_Currency varchar(255) NOT NULL,
	Amount_Bought double NOT NULL,
	Amount_Sold double NOT NULL,
	PRIMARY KEY (TransactionID)
);