### How to Run?

- Download source code from github

	    git clone https://github.com/royalflash5150/cariq-test.git

- Go to directory you cloned.

    	cd /cariq-test
  
- Start docker compose.

    	docker compose up


### API Specification

- BaseURL
        
        http://localhost:8080

- Create a wallet.

	POST `/wallets`
	
	Response:
	 
		wallet_id
		e.g: 195c938b-0e05-4d16-9cff-52f2b073b64a

- Make a cash deposit.

 	POST `/wallets/{wallet_id}/deposit`

	Request: 
	
		{
        "amount": 500
		}		

	response:
	
		"success"
		
- Retrieve a fare.
	
	GET `/fare`
	
	request: 
	
		{
        "origin": "12th",
        "destination": "embr"
		}

	response:

		{
        "origin": "12th",
        "destination": "embr",
        "fare": 3.85
		}
	
- Buy a ticket.

	POST `/wallets/{wallet_id}/ticket`
	
	request:
	
		{
        "origin": "12th",
        "destination": "embr"
		}

	response:

		{
        "origin": "12th",
        "destination": "embr"
		}

- Retrieve a wallet.

	GET `/wallets/{wallet_id}`
	
	response:

		{
        "balance": 459.10,
        "tickets": [
            {
                "origin": "antc",
                "destination": "embr"
            },
            {
                "origin": "antc",
                "destination": "colm"
            },
            {
                "origin": "oakl",
                "destination": "colm"
            },
            ...
        ]
		}
