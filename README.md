### 1. 插件介绍
本插件是基于idea 中mybatis的Maven插件的增强版，支持以下操作：
- 1）自定导入数据库对应表字段的中文注释
-  2）Mapper、dao文件的后缀重命名
- 3） 分页类的实现

- 4） Lombok的整合，可以简化代码

### 2. 插件使用
   - 下载：[github-源码地址](https://github.com/liupenghao1205/maven-mybatis-lombok-plugin.git)
   -  从idea或者eclipse中打开项目
   - 安装插件到本地的Maven仓库

<img src="http://ws4.sinaimg.cn//005AQjvJly1fv05anrx10j30hz0cqaaz.jpg" alt="image">

 - 在Maven 项目中使用 pom.xml
  ```
 <!--mybatis 插件  -->
      <plugin>
        <groupId>com.uu.husky</groupId>
        <artifactId>mybatis-plugin</artifactId>
        <version>1.0-SNAPSHOT</version>
        <configuration>
          <configFilePath>src/main/resources/mybatis-generator.xml</configFilePath>
        </configuration>
      </plugin> 
```
 -  mybatis-generator.xml 配置文件编写

```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE generatorConfiguration
        PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
        "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">

<generatorConfiguration>
    <context id="testTables" targetRuntime="MyBatis3">

        <!-- 使用 Lombok 插件 简化代码-->
        <plugin type="com.uu.husky.Lombok.LombokPlugin"/>

        <!-- ===================== 序列化      toString  ======================== -->
       <!-- <plugin type="org.mybatis.generator.plugins.SerializablePlugin"/>
             <plugin type="org.mybatis.generator.plugins.ToStringPlugin" />
        -->

        <!-- ===================== 自定义物理分页  可生成支持Mysql数据的limit  不支持Oracle  ======================== -->
        <plugin type="com.uu.husky.page.PaginationPlugin" />
        <!-- 自定义查询指定字段  -->
        <plugin type="com.uu.husky.field.FieldsPlugin" />

        <!-- 开启支持内存分页   可生成 支持内存分布的方法及参数
        <plugin type="org.mybatis.generator.plugins.RowBoundsPlugin" />
        -->
        <!-- generate entity时，生成hashcode和equals方法
		<plugin type="org.mybatis.generator.plugins.EqualsHashCodePlugin" />
		 -->

        <!-- 此处是将UserMapper.xml改名为UserDao.xml 当然 想改成什么都行~ -->
        <plugin type="com.uu.husky.rename.RenameSqlMapperPlugin">
            <property name="searchString" value="Mapper" />
            <property name="replaceString" value="Dao" />
        </plugin>

     <!-- ===================== UserMapper接口名称修改 ======================== -->
        <plugin type="com.uu.husky.rename.RenameJavaMapperPlugin">
            <property name="searchString" value="Mapper$" />
            <property name="replaceString" value="Dao" />
        </plugin>

       <!-- ===================== 分页扩展类名称修改 ======================== -->
        <plugin type="org.mybatis.generator.plugins.RenameExampleClassPlugin">
            <property name="searchString" value="Example$" />
            <!-- 替换后
            <property name="replaceString" value="Criteria" />-->
            <property name="replaceString" value="Query" />
        </plugin>

        <commentGenerator type="com.uu.husky.comment.ChineseCommentGenerator">
            <!-- 是否去除自动生成的注释 true：是 ： false:否
            <property name="suppressAllComments" value="true" />
            -->
        </commentGenerator>

       <!-- ===================== 数据库信息 ======================== -->
        <jdbcConnection driverClass="com.mysql.jdbc.Driver"
                        connectionURL="jdbc:mysql://localhost:3306/test" userId="root"
                        password="">
        </jdbcConnection>
        <!-- <jdbcConnection driverClass="oracle.jdbc.OracleDriver"
            connectionURL="jdbc:oracle:thin:@127.0.0.1:1521:yycg"
            userId="yycg"
            password="yycg">
        </jdbcConnection> -->

        <!-- 默认false，把JDBC DECIMAL 和 NUMERIC 类型解析为 Integer，为 true时把JDBC DECIMAL 和
            NUMERIC 类型解析为java.math.BigDecimal -->
        <javaTypeResolver>
            <property name="forceBigDecimals" value="false" />
        </javaTypeResolver>


       <!-- ===================== POJO类位置 ======================== -->
        <javaModelGenerator targetPackage="com.uu.domain"
                            targetProject="src/main/java">
            <!-- enableSubPackages:是否让schema作为包的后缀 -->
            <property name="enableSubPackages" value="false" />
            <!-- 从数据库返回的值被清理前后的空格 -->
            <property name="trimStrings" value="true" />
        </javaModelGenerator>

        <!-- ===================== mapper.xml 保存路径 ======================== -->
        <sqlMapGenerator targetPackage="com.uu.dao"
                         targetProject="src/main/java">
            <!-- enableSubPackages:是否让schema作为包的后缀 -->
            <property name="enableSubPackages" value="false" />
        </sqlMapGenerator>

        <!-- 客户端代码，生成易于使用的针对Model对象和XML配置文件 的代码
                 type="ANNOTATEDMAPPER",生成Java Model 和基于注解的Mapper对象
                 type="MIXEDMAPPER",生成基于注解的Java Model 和相应的Mapper对象
                 type="XMLMAPPER",生成SQLMap XML文件和独立的Mapper接口
         -->
        <javaClientGenerator type="ANNOTATEDMAPPER"
                             targetPackage="com.uu.dao"
                             targetProject="src/main/java">
            <!-- enableSubPackages:是否让schema作为包的后缀 -->
            <property name="enableSubPackages" value="true" />
        </javaClientGenerator>




       <!-- ===================== 映射所有表 ======================== -->
        <!--<table tableName="%">
            <generatedKey column="id" sqlStatement="Mysql"/>
        </table>-->

        <!-- ===================== 表名称对应修改 ======================== -->
        <!-- 有些表的字段需要指定java类型
               order 生成目录
            <table schema="" tableName="t_items" domainObjectName="order.items">
                包装清单 大字段映射
               <columnOverride column="package_list" javaType="String" jdbcType="VARCHAR" />
           </table>
        -->
        <table schema="" tableName="user" domainObjectName="User"/>
    </context>
</generatorConfiguration>

```

 - 运行插件生成相应的文件

![输入图片说明](https://static.oschina.net/uploads/img/201803/19225753_Tpq4.png "在这里输入图片标题")
