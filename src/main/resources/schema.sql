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
	WalletID int auto_increment,
	Crypto_Currency varchar(255) NOT NULL,
	Amount double NOT NULL,
	PRIMARY KEY (WalletID)
)