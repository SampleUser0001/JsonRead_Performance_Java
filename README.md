# JsonRead_Performance_Java
Javaでjsonファイルを読み込む。CPU使用率を見たい。  
ホストとDockerコンテナでCPU使用率に差があるか確認する。

## 実行

### ホストで実行

``` sh
mvn clean compile exec:java -Dexec.mainClass="sample.json.performance.App" -Dexec.args="'10'"
```

### Dockerコンテナで実行

``` sh
docker-compose run maven mvn compile exec:java -Dexec.mainClass="sample.json.performance.App" -Dexec.args="'10'"
```

## CPU使用率について

ホストもDockerコンテナも100%近くまで出る。 = Dockerの設定の問題ではない。

## 参考

- [Use_Json_in_Java:SampleUser0001:Github](https://github.com/SampleUser0001/Use_Json_in_Java/blob/master/src/main/java/sample/json/App.java)