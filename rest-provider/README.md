# REST Provicer示例

## 说明

本示例根据官方提供的examples改编而来，主要目的是能够更加清晰地演示如何编写、部署和测试一个REST provider。

> REST provider factory的id即为REST API的根路径

## 部署和测试

### 打包

```bash
mvn clean package
```

### 部署

1）把打包出的jar文件拷贝到keycloak的providers目录。

```bash
cp -f target/rest-provider.jar $KEYCLOAK_HOME/providers
```

2）然后重新启动keycloak

### 测试

```bash
curl http://localhost:8180/realms/master/hello/realm
```

上述访问会返回：

```json
{
  "realmName": "master"
}
```
