#!/bin/bash

if [ "$API_SERVER" ] && [ "$API_PORT" ]; then
	echo "var ngUrl=\"http://"$API_SERVER:$API_PORT"/\";"  > /usr/local/tomcat/webapps/src/main/webapp/recom/js/ngUrl.js
fi

catalina.sh run
