# CryptoTradingSystem

Task 2: Create an api to retrieve the latest best aggregated price.
Call API using GET request to return JSON with best aggregated price for specified trading pair
e.g. http://localhost:8080/pricing/BTCUSDT

Task 3: Create an api which allows users to trade based on the latest best aggregated
price.
Call API using POST request with a JSON in the request body
e.g. http://localhost:8080/transact
{
    "userId": 1,
    "buying": "ETH",
    "selling": "USDT",
    "amount": "1000"
}

Task 4: Create an api to retrieve the user’s crypto currencies wallet balance.
Call API using GET request to return JSON with wallets as a JSON array using user's id as a path variable
e.g. http://localhost:8080/user/wallets/1

Task 5: Create an api to retrieve the user trading history.
Call API using GET request to return JSON with transaction history as a JSON array using user's id as a path variable
e.g. http://localhost:8080/user/transactions/1