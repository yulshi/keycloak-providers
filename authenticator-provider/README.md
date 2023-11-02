# Secret Question Demo

该项目源自Keycloak官方的example，鉴于官方的文档（22.0.4）与example的代码和说明有一些出入，特意重新在自己的环境中编译、打包和运行了一遍。

本文的重点，不是如何实现Secret Question这个Authenticator，而是对官方[examples/providers/authenticator](https://github.com/keycloak/keycloak/tree/main/examples/providers/authenticator)这个example的部署和测试进行说明。

## 与官方example代码不一样的地方

其实我只做了一个地方的修改，就是把`secret-question.ftl`和`secret-question-config.ftl`放到了`resources/theme-resources/templates`目录下，这也是根据[官方文档](https://www.keycloak.org/docs/22.0.5/server_development/#_theme_resource)来对代码做的唯一一个改动。

## 打包和部署

下载代码：
```bash
git clone git@github.com:yulshi/keycloak-providers.git
```

打包
```bash
mvn clean package
```

部署，其实就是把打好包的jar文件拷贝到Keycloak的providers目录下。

```bash
cp 
```