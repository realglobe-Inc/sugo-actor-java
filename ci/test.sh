#!/bin/sh -e

nodejs=${NODEJS:="node"}
port=${PORT:=12345}

(
  cd $(dirname $0)/..

  if ! which ${nodejs} > /dev/null; then
    echo "no node.js:" ${nodejs}
    exit 1
  fi

  export PORT=${port}
  rm -rf var
  ${nodejs} test/hub.js &
  pid_hub=$!
  trap "kill ${pid_hub}" EXIT 2

  mvn exec:java -Dexec.classpathScope=test -Dexec.mainClass=jp.realglobe.sugo.actor.ActorTest &
  pid_actor=$!
  trap "kill ${pid_hub} ${pid_actor}" EXIT 2

  ${nodejs} test/wait-actor.js

  ${nodejs} test/test-caller1.js
)
