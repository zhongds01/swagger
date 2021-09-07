# 1、导入依赖

```xml
<!-- 引入swagger依赖 -->
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-boot-starter</artifactId>
    <version>3.0.0</version>
</dependency>
```

# 2、开启swagger

- 方式一、在SpringBoot启动类上加上**@EnableOpenApi**注解（该注解3.0版本支持且官方推荐，之前版本使用@EnableSwagger2）

  ```java
  @SpringBootApplication
  @EnableOpenApi
  public class SwaggerCoreApplication {
      public static void main(String[] args) {
          SpringApplication.run(SwaggerCoreApplication.class, args);
      }
  }
  ```

- 方式二、自定义配置类，在该类上使用**@EnableOpenApi**注解

  ```java
  @Configuration
  @EnableOpenApi
  public class SwaggerConfiguration {
  }
  ```

# 3、测试

[swagger默认地址](http://localhost:8080/swagger-ui/index.html#/)

