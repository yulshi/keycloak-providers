# 自定义SPI

## 说明

在keycloak中，可以自定义SPI，这些自定义SPI通常可以被REST的自定义Provider配合使用，来达到一定的业务目的。

首先，本示例展示了一个自定义的SPI（`ExampleSpi`），该SPI声明了Provider（`ExampleServcieProvider`
）以及ProviderFactory（`ExampleServiceProviderFactory`）。

然后，编写上述Provider和ProviderFactory的实现类，实现业务逻辑。

最后，在自定义的REST provider中通过`KeycloakSession#getProvider`方法获取上述provider的实现类，调用其上的方法。

## 部署和测试

### 打包

```bash
mvn clean package
```

### 部署

1）把打包出的jar文件拷贝到keycloak的providers目录。

```bash
cp -f target/custom-spi.jar $KEYCLOAK_HOME/providers
```

2）然后重新启动keycloak

### 测试

```bash
curl http://localhost:8180/realms/master/example-rest?name=Jimmy
```

上述访问会返回：

```text
Hello, Jimmy from master
```
