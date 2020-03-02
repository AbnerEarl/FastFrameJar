# MybatisPlus 自动配置和代码生成
复制 mp 文件夹中的 MybatisPlusConfig 到项目的相关包路径下，即可自动完成配置。修改 MpGenerator 中的数据库连接配置信息，即可进行自动生成代码。

# 相关依赖
根据项目山的相关情况进行添加，如 传统的 SSM 项目 和基于 SpringBoot 的项目略有不同，这里是所有用到的依赖，根据需要添加：

```
	<!--代码生成开始-->
    <dependency>
      <groupId>com.baomidou</groupId>
      <artifactId>mybatis-plus</artifactId>
      <version>2.1.8</version>
    </dependency>
    <dependency>
      <groupId>com.baomidou</groupId>
      <artifactId>mybatis-plus-generator</artifactId>
      <version>3.1.1</version>
    </dependency>
    <!-- 模板引擎 -->
    <dependency>
      <groupId>org.apache.velocity</groupId>
      <artifactId>velocity-engine-core</artifactId>
      <version>2.1</version>
    </dependency>
    <dependency>
      <groupId>org.mybatis.spring.boot</groupId>
      <artifactId>mybatis-spring- boot-starter</artifactId>
    </dependency>
    <dependency>
      <groupId>org.mybatis.spring.boot</groupId>
      <artifactId>mybatis-spring-boot-autoconfigure</artifactId>
      <version>1.3.2</version>
    </dependency>

    <dependency>
      <groupId>org.mybatis.generator</groupId>
      <artifactId>mybatis-generator-core</artifactId>
      <version>1.3.6</version>
    </dependency>

```


# 问题讨论
## [点击传送](https://github.com/YouAreOnlyOne/FastFrameJar/issues)

# 技术使用
https://blog.csdn.net/u014374009

# 其它信息
1.项目还有很多不完善的地方，欢迎大家指导。

2.项目持续更新开源，有兴趣加入项目或者跟随项目的伙伴，可以邮件联系！

3.项目不定期更新和迭代，感兴趣的伙伴可以点击上方的 ~ fork、star ~。

4.有些设计和功能的实现，参考了GitHub上开源项目。

# 作者邮箱
ycj52011@outlook.com
