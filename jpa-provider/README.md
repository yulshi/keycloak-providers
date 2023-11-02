# 自定义JPA的entity

## 说明

我们可以通过SPI来向Keycloak添加JPA的Entity，来满足我们的业务需求。本示例根据官方的examples改编而来，目的是更加清晰地展示如何自定义JPA的Entity。

该示例中，定义了一个新的实体（Entity）- `Company`，用来保存公司信息。为了把它加入到keycloak中，需要实现`JpaEntityProvider`以及`JpaEntityProviderFactory`这两个接口。

然后，定义了一个REST API（从`RealmResourceProviderFactory`而来），来通过JPA的`EntityManager`实现对`Company`实体的CRUD。

## 部署和测试

### 打包

```bash
mvn clean package
```

### 部署

1）把打包出的jar文件拷贝到keycloak的providers目录。

```bash
cp -f target/jpa-provider.jar $KEYCLOAK_HOME/providers
```

2）然后重新启动keycloak

### 测试

- 新建一个公司
    ```bash
    curl --location 'localhost:8180/realms/master/company-rest-provider' \
    --header 'Content-Type: application/json' \
    --data '{
     "name": "ACME" 
    }'
    ```

- 查询所有公司
  ```bash
  curl --location 'localhost:8180/realms/master/company-rest-provider'
  ```
