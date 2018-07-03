# Monitor

Monitor is a REST monitoring service that checks for server status at a given URL and interval.

## Installation

You can start the server directly on your machine, or inside docker.

### On Your Machine

```
$ git clone https://github.com/louis-ver/paysafe-backend-test.git && cd paysafe-backend-test
$ ./run
```

### In Docker

```
$ git clone https://github.com/louis-ver/paysafe-backend-test.git && cd paysafe-backend-test
$ ./docker-run
```
## Usage (API)

### `/start`

#### POST
Starts monitoring a server. The URL and interval of the server are given in the body of the request.


#### Payload
```
{
  url: URL,
  interval: INTERVAL
}
```
- `url`: Valid URL, as defined in [RFC 2396](https://www.ietf.org/rfc/rfc2396.txt)
- `interval`: Integer, between 1 and 100000

##### Example
```
curl -X "POST" "http://localhost:8080/start" \
     -H 'Content-Type: application/json; charset=utf-8' \
     -d $'{
  "url": "https://api.test.paysafe.com/accountmanagement/monitor",
  "interval": "5"
}'
```

#### PATCH

Modifies the interval at which the server status is checked.

#### Payload

```
{
  url: URL,
  interval: INTERVAL
}
```
- `url`: URL of the monitored server for which we want to modify the interval
- `interval`: Integer, between 1 and 100000

##### Example
```
curl -X "PATCH" "http://localhost:8080/start" \
     -H 'Content-Type: application/json; charset=utf-8' \
     -d $'{
  "url": "https://api.test.paysafe.com/accountmanagement/monitor",
  "interval": "10"
}'
```

### `/stop`

#### POST

Stops monitoring the server at the specified URL.

#### Payload
```
{
  url: URL
}
```
- `url`: URL of the monitored server for which we want to stop monitoring

##### Example
```
curl -X "POST" "http://localhost:8080/stop" \
     -H 'Content-Type: application/json; charset=utf-8' \
     -d $'{
  "url": "https://api.test.paysafe.com/accountmanagement/monitor"
}'
```

### `/uptime`

#### GET

Returns current status information on the requested URL.

#### Parameters

`url`: URL of the monitored server for which we want a summary

##### Example
```
curl "http://localhost:8080/uptime?url=https:%2F%2Fapi.test.paysafe.com%2Faccountmanagement%2Fmonitor"
```