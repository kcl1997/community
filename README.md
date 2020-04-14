## 码匠社区

## 资料
https://spring.io/guides
https://spring.io/guides/gs/serving-web-content/
https://elasticsearch.cn/explore
https://developer.github.com/v3/guides/managing-deploy-keys/#deploy-key

# 工具
[Git](https://git-scm.com/download) 

[Flyway](https://flywaydb.org/getstarted/firststeps/maven)
[PostMan](https://chrome.google.com/webstore/detail/tabbed-postman-rest-clien/coohjcphdfgbiolnekdpbcijmhambjff)

## 脚本
```sql
CREATE TABLE USER (
    ID int AUTO_IINCREMENT PRIMARY KEY NOT NULL ,
    ACCOUNT_ID VARCHAR (100),
    NAME VARCHAR (50),
    TOKEN VARCHAR (36),
    GMT_CREATE BIGNIT,
    GMT_MODEIFED BIGINT
    );
```
```bash
mvn flyway:migrate
```
mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate
