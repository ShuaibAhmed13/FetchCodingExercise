# Points System (Fetch Rewards Coding Exercise)

## Downloading and running
After cloning (or downloading) the project onto your computer, you can run the service by navigating to:
`PointsSystem -> src -> main -> java -> com.projects.PointsSystem -> PointsSystemApplication` and running that class.

## Requests


PATH = `localhost:8080`
where 8080 is the default port

Type| Function                                            |Endpoint|Requirements
---|-----------------------------------------------------|---|---
GET| get a list of all the transactions                  |`/api/v1/all_transactions`| `NONE`
POST| insert a new transaction                            |`/api/v1/add_transaction`| JSON values: `payer`,`points`,`timestamp`
GET| view user balance for each payer                    |`/api/v1/balance`| `NONE`
GET| spend points as long as user has sufficient amounts |`/api/v1/spend_points`| RequestParam: `points`

### Example 1 (Add Transaction):
Using Postman (or alternative), select POST request and type in the url `localhost:8080/api/v1/add_transaction`. 
In the body, select raw and of type JSON. Insert transaction in JSON format in the body:

`{"payer":"WARREN INC", "points": 400, "timestamp": "2020-08-01T14:00:00Z" }`

Sending the request will add the transaction to the system

### Example 2 (Spend Points):
In Postman, create a request of type GET and type in the address: 

`localhost:8080/api/v1/spend_points`

In the params section, type in "points" for the `key` and the desired points for `value`. Sending the request will return a list of payers and the points spent with each.