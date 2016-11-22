#!/bin/sh -e

deploy() {
    tar cvzf ${1}.tar.gz -C target/site ${1}
    curl -H "Content-Type: application/octet-stream" --data-binary @${1}.tar.gz \
         ${DOCUMENT_SERVER}/${TRAVIS_REPO_SLUG}/${TRAVIS_BUILD_NUMBER}/${1}
}

mvn deploy -DskipTests=true -Dmaven.javadoc.skip=true -B --settings .travis-deploy-settings.xml

mvn javadoc:javadoc -B --settings .travis-settings.xml
deploy apidocs

mvn jacoco:report -B --settings .travis-settings.xml
deploy jacoco
