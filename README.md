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

# 3、访问swagger页面

[swagger3.0版本访问地址](http://localhost:8080/swagger-ui/index.html)

[swagger2.x版本访问地址](http://localhost:8080/swagger-ui.html)

# 4、配置swagger

新建配置类，注入Docket实例

```java
@Configuration
@EnableOpenApi
public class SwaggerConfiguration {
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.OAS_30)
                // enable：是否开启swagger
                .enable(true)
                // apiInfo：配置apiInfo
                .apiInfo(apiInfo())
                // host：接口的调试地址
                .host("http://localhost:8080")
                // select：过滤接口
                .select().apis(RequestHandlerSelectors.basePackage("com.zds.controller")).paths(PathSelectors.any()).build();
    }

    /**
     * 配置swagger api相关信息，对应页面最上面swagger信息展示区域
     * 推荐使用ApiInfoBuilder().build()方法创建ApiInfo实例，可以避免使用构造方法指定所有的属性
     *
     * @return apiInfo
     */
    private ApiInfo apiInfo() {
        Contact contact = new Contact("zhongdongsheng", "", "zhongds01@163.com");
        Contact defaultContact = new Contact("", "", "");
        // 默认的ApiInfo实例
        ApiInfo defaultApiInfo = new ApiInfo("Api Documentation", "Api Documentation", "1.0", "urn:tos", defaultContact, "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0", new ArrayList());
        // 通过ApiInfoBuilder创建ApiInfo实例
        ApiInfo apiInfoBuilder = new ApiInfoBuilder().title("swagger-core").description("swagger-core project description").contact(contact).build();
        // 通过ApiInfo的构造方法构造ApiInfo实例。
        ApiInfo apiInfo = new ApiInfo("swagger-core", "swagger-core project description", "1.0", "urn:tos", contact, "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0", new ArrayList());
        return apiInfoBuilder;
    }
}
```

# 5、常用注解使用

| 注解               | 说明                                                         |
| ------------------ | ------------------------------------------------------------ |
| @Api               | 用在controller类，描述API接口                                |
| @ApiOperation      | 描述接口方法                                                 |
| @ApiModel          | 描述实体类对象                                               |
| @ApiModelProperty  | 描述实体类对象属性                                           |
| @ApiImplicitParams | 多个@ApiImplicitParam                                        |
| @ApiImplicitParam  | 描述接口参数，用于方法名上                                   |
| @ApiParam          | 描述接口参数，用于方法参数上                                 |
| @ApiResponses      | 描述接口响应（@ApiResponse(code = 200,message = "响应成功")） |
| @ApiIgnore         | 忽略接口方法（页面不再显示该接口的说明）                     |

```java
@ApiModel(value = "用户实体类")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class User {
    @ApiModelProperty(value = "用户id")
    private Long id;

    @ApiModelProperty(value = "用户名称",required = true,example = "zhongdongsheng")
    private String userName;

    @ApiModelProperty(value = "用户密码")
    private String password;
}
```

```java
@Api(tags = "用户请求处理控制层")
@RestController
public class UserController {

    @ApiOperation(value = "根据id获取用户信息")
    @ApiImplicitParam(name = "id", value = "用户id", dataType = "Long", paramType = "query", example = "1")
    @GetMapping(value = "/user/{id}")
    public User getUserById(@PathVariable Long id) {
        return new User().setId(id).setUserName("zhongdongsheng").setPassword("******");
    }

    @ApiOperation(value = "查询所有的用户信息")
    @GetMapping(value = "/user/all")
    public List<User> getAllUser() {
        User user1 = new User().setId(1000L).setUserName("zhongdongsheng").setPassword("******");
        User user2 = new User().setId(1001L).setUserName("zhouliang").setPassword("******");
        User user3 = new User().setId(1002L).setUserName("yongjian").setPassword("******");
        return Arrays.asList(user1, user2, user3);
    }

    @ApiOperation(value = "添加用户", notes = "返回：新增用户的id")
    @PostMapping(value = "/user/save")
    public Long saveUser(@RequestBody User user) {
        System.out.println(user);
        return user.getId();
    }

    @ApiOperation(value = "根据用户id删除用户")
    @ApiResponse(code = 200, message = "响应成功")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "username", value = "用户名称", dataType = "String", paramType = "query")
    })
    @DeleteMapping("/user/delete")
    public void deleteUser(
            @ApiParam(value = "用户id", required = true, example = "1")
            @RequestParam("id") Long id) {
        System.out.println(id);
    }

    @ApiIgnore(value = "忽略该请求")
    @GetMapping("/ignore")
    public void ignore() {
    }
}
```

# 6、自定义swagger配置，并注入配置实体类中

- 定义SwaggerProperties类，封装自定义swagger的相关配置

  ```java
  @Component
  @ConfigurationProperties(prefix = "swagger")
  @Data
  public class SwaggerProperties {
      private String host;
      private String port;
      private boolean enabled;
  }
  ```

- 使用自定义配置类

  ```java
  @Configuration
  @EnableOpenApi
  public class SwaggerConfiguration {
      private final SwaggerProperties swaggerProperties;
  
      public SwaggerConfiguration(SwaggerProperties swaggerProperties) {
          this.swaggerProperties = swaggerProperties;
      }
  
      @Bean
      public Docket docket() {
          return new Docket(DocumentationType.OAS_30)
              // enable：是否开启swagger
              .enable(swaggerProperties.isEnabled())
              // apiInfo：配置apiInfo
              .apiInfo(apiInfo())
              // host：接口的调试地址
              .host(swaggerProperties.getHost()+swaggerProperties.getPort())
              // select：过滤接口
              .select().apis(RequestHandlerSelectors.basePackage("com.zds.controller")).paths(PathSelectors.any()).build();
      }
  
      /**
       * 配置swagger api相关信息，对应页面最上面swagger信息展示区域
       * 推荐使用ApiInfoBuilder().build()方法创建ApiInfo实例，可以避免使用构造方法指定所有的属性
       *
       * @return apiInfo
       */
      private ApiInfo apiInfo() {
          Contact contact = new Contact("zhongdongsheng", "", "zhongds01@163.com");
          Contact defaultContact = new Contact("", "", "");
          // 默认的ApiInfo实例
          ApiInfo defaultApiInfo = new ApiInfo("Api Documentation", "Api Documentation", "1.0", "urn:tos", defaultContact, "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0", new ArrayList());
          // 通过ApiInfoBuilder创建ApiInfo实例
          ApiInfo apiInfoBuilder = new ApiInfoBuilder().title("swagger-core").description("swagger-core project description").contact(contact).build();
          // 通过ApiInfo的构造方法构造ApiInfo实例。
          ApiInfo apiInfo = new ApiInfo("swagger-core", "swagger-core project description", "1.0", "urn:tos", contact, "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0", new ArrayList());
          return apiInfoBuilder;
      }
  }
  ```
