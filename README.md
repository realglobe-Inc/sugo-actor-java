# sugo-actor-java

Java 用の sugo actor。

https://realglobe-inc.github.io/javadoc/jp/realglobe/sugo-actor/


## コンパイル

依存ライブラリを取得するために Maven の設定ファイル（~/.m2/settings.xml 等）に以下のような記述を加える。

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<settings xsi:schemaLocation='http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd'
          xmlns='http://maven.apache.org/SETTINGS/1.0.0' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'>
  <profiles>
    <profile>
      <repositories>
        <repository>
          <snapshots>
            <enabled>false</enabled>
          </snapshots>
          <id>jcenter</id>
          <name>jcenter</name>
          <url>https://jcenter.bintray.com/</url>
        </repository>
      </repositories>
      <id>jcenter</id>
    </profile>
  </profiles>
  <activeProfiles>
    <activeProfile>jcenter</activeProfile>
  </activeProfiles>
</settings>
```

で、

```shell-session
$ mvn clean install
```


## ライセンス

Apache License, Version 2.0
