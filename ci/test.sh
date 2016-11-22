#!/bin/sh -e

npm=${NPM:="npm"}
nodejs=${NODEJS:="node"}
port=${PORT:=12345}

(
  cd $(dirname $0)/..

  if ! which ${npm} > /dev/null; then
    echo "no npm:" ${npm}
    exit 1
  elif ! which ${nodejs} > /dev/null; then
    echo "no node.js:" ${nodejs}
    exit 1
  fi

  ${npm} install

  export PORT=${port}
  rm -rf var
  ${nodejs} test/hub.js &
  pid_hub=$!
  trap "kill ${pid_hub}" EXIT

  mvn exec:java -Dexec.classpathScope=test -Dexec.mainClass=jp.realglobe.sugo.actor.ActorTest &
  pid_actor=$!
  trap "kill ${pid_hub} ${pid_actor}" EXIT

  ${nodejs} test/wait-actor.js

  ${nodejs} test/test-caller1.js
)
