  assertCurl 200 "curl -s  http://$HOST:$PORT/
    openapi/v3/api-docs"
  assertEqual "http://$HOST:$PORT" "$(echo $RESPONSE 
    | jq -r .servers[].url)"