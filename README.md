# Idle Project

这是一个二手闲置交易系统后端项目，包含用户端服务和管理端服务两个 Maven 模块。

## 模块

- `idle`: 用户端接口服务，默认端口 `8080`
- `idle-admin`: 管理端接口服务，默认端口 `9090`

## 环境要求

- JDK 21
- Maven 3.9+
- MySQL
- Redis
- RabbitMQ（用户端涉及消息队列功能时需要）

## 本地配置

项目中的 `application.yml` 不保存真实密码或云服务密钥，敏感配置通过环境变量注入。

常用环境变量：

- `DB_URL`
- `DB_USERNAME`
- `DB_PASSWORD`
- `REDIS_HOST`
- `REDIS_PORT`
- `REDIS_PASSWORD`
- `RABBITMQ_HOST`
- `RABBITMQ_PORT`
- `RABBITMQ_USERNAME`
- `RABBITMQ_PASSWORD`
- `ALIYUN_OSS_ACCESS_KEY_ID`
- `ALIYUN_OSS_ACCESS_KEY_SECRET`
- `ALIYUN_OSS_BUCKET_NAME`
- `ALIPAY_APP_ID`
- `ALIPAY_APP_PRIVATE_KEY`
- `ALIPAY_PUBLIC_KEY`
- `ALIPAY_NOTIFY_URL`

可以参考：

- `idle/src/main/resources/application-local.example.yml`
- `idle-admin/src/main/resources/application-local.example.yml`

真实的 `application-local.yml`、`.env`、IDE 配置和 Maven 构建产物已经加入 `.gitignore`，不要提交到仓库。

## 构建

```bash
mvn -DskipTests package
```
