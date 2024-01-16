# Spring Cloud OpenFeign 通用参数传递

一款**简单的**feign之间调用时传递公共参数的工具。

目前参数来源和目标，内置两种方式：**query**、**header**

* **query**：获取时从当前http请求query参数中获取，本次feign调用是http请求触发的才可以获取到；传递时直接放到feign请求的query中
* **header**：获取时从当前http请求header参数中获取，本次feign调用是http请求触发的才可以获取到；传递时直接放到feign请求的header中

两者之前可以互相交叉使用。

提供接口可扩展来源（**IParamGettingHandler**）、以及目标（**IParamSettingHandler**
），比如从当前请求上下文中获取，threadlocal中获取，以及各种鉴权工具context中获取参数。
目前不支持请求的body处理，因为需要解析再封装对性能有比较大的影响，不内置，当然可扩展。

## 使用

1. 引入依赖

```xml
<dependency>
    <groupId>com.ruanun</groupId>
    <artifactId>openfeign-params-spring-boot-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```

2. 配置文件

```yaml
feign:
  params:
    #拦截路径，默认值：/** ；所有
    intercept-paths:
      - /**
    param-configs:
      userinfo:
        - source-name: uname
          source-type: QUERY
          target-name: username
          target-type: QUERY
        - source-name: uname
          source-type: QUERY
          target-name: name
          target-type: QUERY
        - source-name: uid
          source-type: HEADER
          target-name: userId
          target-type: QUERY
        - source-name: uid
          source-type: HEADER
          target-name: userId
          target-type: HEADER
        - source-name: uid
          source-type: QUERY
          target-name: user-id
          target-type: HEADER
      tenantInfo:
        - source-name: tenantId
          source-type: HEADER
          target-name: tenant-id
          target-type: HEADER
    param-method-mapping:
      get:
        - userinfo
        - tenantInfo
      post:
        - userinfo
        - tenantInfo
```
