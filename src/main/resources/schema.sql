CREATE TABLE Users (
    UserID int auto_increment,
    FirstName varchar(255) NOT NULL,
    LastName varchar(255) NOT NULL,
    UserName varchar(255) NOT NULL,
    Lock boolean,
    LockedDate datetime NULL
);

CREATE TABLE Pricing (
    PricingID long auto_increment,
    TradingPair varchar(255) NOT NULL,
    AskPrice double,
    BidPrice double
);

CREATE TABLE Wallet (
	WalletID int auto_increment,
	Symbol varchar(255) NOT NULL,
	Amount double NOT NULL
)