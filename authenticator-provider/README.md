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

部署，其实就是把打好包的jar文件拷贝到Keycloak的providers目录下，然后重新启动Keycloak。

```bash
cp authenticator-provider/target/authenticator-provider.jar $KEYCLOAK_HOME/providers
```

## 测试

- keycloak上的设置
  - 在Keycloak的控制台上选择一个realm
  - 从左边菜单中选择`Authentication`，在右边窗口里面选择Flow name为`browser`的Flow，然后从左右边的...中选择`Duplicate`，来克隆一份，命名为`secret-question`
  - 在`secret-question`中的`secret-question forms`这个sub flow中的加号那里点击，选择`Add step`
  - 从弹出的对话框中选择，`Secret Question`的Authenticator并添加
  - 设置`Secret Question`的Requirement为`Required`
  - 然后将这个flow绑定到`Browser flow`
  - 最后，不要忘记，在`Required actions`这个tab里面，启用（enable）`Secret Question`这个required action。
- 登录realm的account，来测试登录的效果
  - 在输入用户名/密码后，会导航到一个标题叫做Setup Secret Question的页面，要求你设置问题的答案。这个页面就是`Secret Question`这个required action来设置的。
  - 在第二次登录的时候，输入用户名/密码，会导航到一个标题为登录名的页面，要求你回答问题，你输入了上一步设置的答案，就能通过验证，否则不能。

