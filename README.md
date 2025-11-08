# MyBatis plus codegen plugin

[![License](https://img.shields.io/:license-apache-brightgreen.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

Mybatis plus codegen plugin will help you generate your code at every moment without real running database;

## 简介

- 解决mybatis plus codegen依赖于真实数据库环境，变成依赖sql文件，在codegen时启用临时mariaDB进行代码生成
- 解决mybatis plus codegen无法处理联合主键的问题，先通过JsqlParser去除联合主键和分区，联合查询可在代码生成后自己实现
- 配置参数与Mybatis plus codegen官网保持一致，默认值也一致。可直接参考 https://baomidou.com/pages/981406/#%E5%8F%AF%E9%80%89%E9%85%8D%E7%BD%AE
- 解决mybatisplus官方codegen生成字段类型不准确问题，比如tinyint(1)生成为byte而不是Boolean问题
- 允许自定义父类、接口等，使现有代码无缝迁移

## 使用场景

- 插件配置于业务项目内，采用entity每次编译都覆盖，而其他实现类业务代码只生成一次。
- 插件配置于业务项目外，只启用entity生成，每次打包发布后通过jar包引入，实现DDL的版本控制，并可配合Flyway实现无感更新。

## 快速使用
``` xml
            <plugin>
                <groupId>com.htliang</groupId>
                <artifactId>mybatis-plus-codegen-plugin</artifactId>
                <version>1.0.3</version>
                <executions>
                    <execution>
                        <phase>generate-sources</phase>
                        <goals>
                            <goal>generate</goal>
                        </goals>
                    </execution>
                </executions>
                <configuration>
                    <codegenOptions>
                        <sqlPath>${project.basedir}/src/main/resources/db/ddl/all.sql</sqlPath>
                        <globalOption>
                            <disableOpenDir>true</disableOpenDir>
                            <author>HT Liang</author>
                        </globalOption>
                        <packageOption>
                            <parent>com.htliang.testcodegen</parent>
                            <moduleName>db</moduleName>
                        </packageOption>
                        <templateOption>
                            <disableController>true</disableController>
                        </templateOption>
                        <strategyOption>
                            <baseStrategy>
                                <enableCapitalMode>true</enableCapitalMode>
                            </baseStrategy>
                            <entityStrategy>
                                <enableFileOverride>true</enableFileOverride>
                                <disableSerialVersionUID>true</disableSerialVersionUID>
                                <enableChainModel>true</enableChainModel>
                                <enableLombok>true</enableLombok>
                                <enableTableFieldAnnotation>true</enableTableFieldAnnotation>
                            </entityStrategy>
                            <serviceStrategy>
                                <enableFileOverride>true</enableFileOverride>
                            </serviceStrategy>
                            <mapperStrategy>
                                <enableFileOverride>true</enableFileOverride>
                            </mapperStrategy>
                        </strategyOption>
                    </codegenOptions>
                </configuration>
            </plugin>
```
### 进阶使用
``` xml
            <plugin>
                <groupId>com.htliang</groupId>
                <artifactId>mybatis-plus-codegen-plugin</artifactId>
                <version>1.0.7.DEV</version>
                <executions>
                    <execution>
                        <phase>generate-sources</phase>
                        <goals>
                            <goal>generate</goal>
                        </goals>
                    </execution>
                </executions>
                <configuration>
                    <codegenOptions>
                        <sqlPath>${project.basedir}/src/main/resources/db/all.sql</sqlPath>
                        <globalOption>
                            <disableOpenDir>true</disableOpenDir>
                            <author>HT Liang</author>
                        </globalOption>
                        <packageOption>
                            <parent>com.htliang.example.model</parent>
                            <moduleName>db</moduleName>
                        </packageOption>
                        <templateOption>
                            <disableService>true</disableService>
                            <disableServiceImpl>true</disableServiceImpl>
                            <disableMapper>true</disableMapper>
                            <disableXml>true</disableXml>
                            <disableController>true</disableController>
                            <enableCustomTemplate>true</enableCustomTemplate>
                            <entity>tpl/entity</entity>
                        </templateOption>
                        <injectionOption>
                            <injectionBaseDir>${project.basedir}/src/main/resources/tpl/</injectionBaseDir>
                            <supplementFileName>supplement.yaml</supplementFileName>
                            <customClassParamsFileName>customClasses.json</customClassParamsFileName>
                            <customClassTemplateFileName>customClass.ftl</customClassTemplateFileName>
                        </injectionOption>
                        <strategyOption>
                            <baseStrategy>
                                <enableCapitalMode>true</enableCapitalMode>
                                <tablePrefixes>
                                    <tablePrefix>t_</tablePrefix>
                                </tablePrefixes>
                            </baseStrategy>
                            <entityStrategy>
                                <enableFileOverride>true</enableFileOverride>
                                <disableSerialVersionUID>true</disableSerialVersionUID>
                                <enableChainModel>true</enableChainModel>
                                <enableLombok>true</enableLombok>
                                <enableColumnConstant>true</enableColumnConstant>
                                <enableTableFieldAnnotation>true</enableTableFieldAnnotation>
                                <fileNameFormat>%sEntity</fileNameFormat>
                            </entityStrategy>
                            <serviceStrategy>
                                <enableFileOverride>true</enableFileOverride>
                            </serviceStrategy>
                            <mapperStrategy>
                                <enableFileOverride>true</enableFileOverride>
                            </mapperStrategy>
                        </strategyOption>
                    </codegenOptions>
                </configuration>
            </plugin>
```

此处附上所需的模板文件
``` ftl
// customClass.ftl
package ${package};

<#if imports??>
    <#list imports as import>
import ${import};
    </#list>
</#if>

<#if annotations??>
    <#list annotations as annotation>
${annotation}
    </#list>
</#if>
public<#if is_abstract> abstract</#if> ${type} ${name}<#if super_class??> extends ${super_class}</#if><#if interfaces??> implements <#list interfaces as interface>interface<#if interface_has_next>, </#if></#list></#if> {
<#list members as member>
${member}
    <#if member_has_next>

    </#if>
</#list>
}
```

``` ftl
// entity.ftl
package ${package.Entity};

<#assign hasThisTableConfig=false>
<#assign thisTableConfig></#assign>
<#assign superClasses></#assign>
<#assign hasSuperClass=false>
<#if cfg["supers"]??>
    <#assign superClasses=cfg["supers"]>
</#if>
<#if cfg["tables"]??>
    <#list cfg["tables"] as value>
        <#if value.name == table.name>
            <#assign hasThisTableConfig=true>
            <#assign thisTableConfig=value>
            <#if value["imports"]??>
                <#list value["imports"] as customType>
import ${customType};
                </#list>
            </#if>
            <#break>
        </#if>
    </#list>
</#if>
<#assign superFields></#assign>
<#if hasThisTableConfig>
    <#if thisTableConfig["superClass"]??>
        <#assign hasSuperClass=true>
        <#list superClasses as superClass>
            <#if superClass.name == thisTableConfig["superClass"]>
                <#assign superFields=superClass.fields>
                <#break>
            </#if>
        </#list>
    </#if>
</#if>
<#list table.importPackages as pakages>
import ${pakages};
</#list>
<#if springdoc>
import io.swagger.v3.oas.annotations.media.Schema;
<#elseif swagger>
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
</#if>
<#if entityLombokModel>
import lombok.Data;
import lombok.EqualsAndHashCode;
    <#if chainModel>
import lombok.experimental.Accessors;
    </#if>
</#if>

/**
 * <p>
 * ${table.comment!}
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if entityLombokModel>
@Data
@EqualsAndHashCode(callSuper = <#if hasSuperClass>true<#else>false</#if>)
    <#if chainModel>
@Accessors(chain = true)
    </#if>
</#if>
<#if table.convert>
@TableName("${schemaName}${table.name}")
</#if>
<#if springdoc>
@Schema(name = "${entity}", description = "$!{table.comment}")
<#elseif swagger>
@ApiModel(value = "${entity}对象", description = "${table.comment!}")
</#if>
<#if hasThisTableConfig>
    <#if thisTableConfig["annotations"]??>
        <#list thisTableConfig["annotations"] as annotation>
${annotation}
        </#list>
    </#if>
</#if>
<#assign classDefined=false>
<#if hasThisTableConfig>
    <#if hasSuperClass>
        <#assign classDefined=true>
public class ${entity} extends ${thisTableConfig["superClass"]} <#if thisTableConfig["interfaces"]??>implements <#list thisTableConfig["interfaces"] as interfaceName>${interfaceName}<#if interfaceName_has_next>, </#if></#list></#if><#if entitySerialVersionUID><#if thisTableConfig["interfaces"]??>, <#else >implements </#if>Serializable</#if> {
    <#elseif thisTableConfig["interfaces"]??>
        <#assign classDefined=true>
public class ${entity} implements <#list thisTableConfig["interfaces"] as interfaceName>${interfaceName}<#if interfaceName_has_next>, </#if></#list><#if entitySerialVersionUID>, Serializable</#if> {
    </#if>
</#if>
<#if !classDefined>
    <#if superEntityClass??>
public class ${entity} extends ${superEntityClass}<#if activeRecord><${entity}></#if> {
    <#elseif activeRecord>
public class ${entity} extends Model<${entity}> {
    <#elseif entitySerialVersionUID>
public class ${entity} implements Serializable {
    <#else>
public class ${entity} {
    </#if>
</#if>
<#if entitySerialVersionUID>
    private static final long serialVersionUID = 1L;

</#if>
<#list table.fields as column>
    <#assign skip=false>
    <#if hasThisTableConfig>
        <#if hasSuperClass>
            <#list superFields as superField>
                <#if superField == column.annotationColumnName>
                    <#assign skip=true>
                </#if>
            </#list>
        </#if>
    </#if>
    <#if !skip>
        <#if column.keyFlag>
            <#assign keyPropertyName="${column.propertyName}"/>
        </#if>
        <#if column.comment!?length gt 0>
            <#if springdoc>
    @Schema(description = "${column.comment}")
            <#elseif swagger>
    @ApiModelProperty("${column.comment}")
            <#else>
    /**
    * ${column.comment}
    */
            </#if>
        </#if>
        <#assign customAnnotation=false>
        <#assign hasCustomColumn=false>
        <#assign customColumn></#assign>
        <#if hasThisTableConfig>
            <#if thisTableConfig["columns"]??>
                <#list thisTableConfig["columns"] as value>
                    <#if value.name == column.annotationColumnName>
                        <#if value.customAnnotations??>
                            <#assign customAnnotations=true>
                            <#list value.customAnnotations as fieldAnno>
    ${fieldAnno}
                            </#list>
                        </#if>
                        <#assign hasCustomColumn=true>
                        <#assign customColumn=value>
                        <#break>
                    </#if>
                </#list>
            </#if>
        </#if>
        <#if !customAnnotation>
            <#if column.keyFlag>
                <#if column.keyIdentityFlag>
    @TableId(value = "${column.annotationColumnName}", type = IdType.AUTO)
                <#elseif idType??>
    @TableId(value = "${column.annotationColumnName}", type = IdType.${idType})
                <#elseif column.convert>
    @TableId("${column.annotationColumnName}")
                </#if>
            <#elseif column.fill??>
                <#if column.convert>
    @TableField(value = "${column.annotationColumnName}", fill = FieldFill.${column.fill})
                <#else>
    @TableField(fill = FieldFill.${column.fill})
                </#if>
            <#elseif column.convert>
    @TableField("${column.annotationColumnName}")
            </#if>
        </#if>
        <#if column.versionField>
    @Version
        </#if>
        <#if column.logicDeleteField>
    @TableLogic
        </#if>
        <#if hasCustomColumn>
    private <#if customColumn["customType"]??>${customColumn["customType"]}<#else>${propertyType}</#if> <#if customColumn["customName"]??>${customColumn["customName"]}<#else>${column.propertyName}</#if>;
        </#if>
        <#if !hasCustomColumn>
    private ${column.propertyType} ${column.propertyName};
        </#if>
        <#if column_has_next>

        </#if>
    </#if>
</#list>
<#------------  END 字段循环遍历  ---------->
<#if !entityLombokModel>
    <#list table.fields as column>
        <#assign skip=false>
        <#if hasThisTableConfig>
            <#list superFields as superField>
                <#if superField == column.annotationColumnName>
                    <#assign skip=true>
                    <#break>
                </#if>
            </#list>
        </#if>
        <#if !skip>
            <#if column.propertyType == "boolean">
                <#assign getprefix="is"/>
            <#else>
                <#assign getprefix="get"/>
            </#if>

    public ${column.propertyType} ${getprefix}${column.capitalName}() {
        return ${column.propertyName};
    }

            <#if chainModel>
    public ${entity} set${column.capitalName}(${column.propertyType} ${column.propertyName}) {
            <#else>
    public void set${column.capitalName}(${column.propertyType} ${column.propertyName}) {
            </#if>
       this.${column.propertyName} = ${column.propertyName};
            <#if chainModel>
        return this;
            </#if>
    }
        </#if>
    </#list>
</#if>
<#if entityColumnConstant>

    <#list table.fields as column>
        <#assign skip=false>
        <#if hasThisTableConfig>
            <#if hasSuperClass>
                <#list superFields as superField>
                    <#if superField == column.annotationColumnName>
                        <#assign skip=true>
                        <#break>
                    </#if>
                </#list>
            </#if>
        </#if>
        <#if !skip>
   public static final String ${column.name?upper_case} = "${column.name}";
        </#if>
    </#list>
</#if>
<#if hasThisTableConfig>
    <#if thisTableConfig["other"]??>
        <#list thisTableConfig["other"] as customLine>

    ${customLine}
        </#list>
    </#if>
</#if>
<#if activeRecord>

    @Override
    public Serializable pkVal() {
    <#if keyPropertyName??>
        return this.${keyPropertyName};
    <#else>
        return null;
    </#if>
    }
</#if>
<#if !entityLombokModel>

    @Override
    public String toString() {
        return "${entity}{" +
    <#list table.fields as field>
        <#if field_index==0>
            "${field.propertyName} = " + ${field.propertyName} +
        <#else>
            ", ${field.propertyName} = " + ${field.propertyName} +
        </#if>
    </#list>
        "}";
    }
</#if>
}
```

``` yaml
// supplement.yaml
tables:
  - name: t_table
    imports:
      - "com.example.xx.ClassOne"
      - "com.example.xx.ClassTwo"
      - "com.example.xx.ClassThree"
    super_class: MySuperClass
    interfaces:
      - "InterfaceOne"
      - "InterfaceTwo"
      - "InterfaceThree"
    annotations:
      - "@AnnotationOne"
      - "@AnnotationTwo"
      - "@AnnotationThree"
    columns:
      - name: column_one
        custom_name: customNameOne
        custom_type: Boolean
        custom_annotations:
          - "@CustomColumnxxxxxxx"
          - "@CustomColumnyyyyyyy"
supers:
  - name: MySuperClass
    fields:
      - id
      - create_at
      - update_at
```

``` json
# customClasses.json
[
  {
    "package": "com.htliang.xxx.entity",
    "imports": [],
    "annotations": [],
    "is_abstract": false,
    "type": "interface",
    "name": "InterfaceOne",
    "super_class": null,
    "interfaces": null,
    "members": [
      "    Long getId();",
      "    InterfaceOne setId(Long id);",
      "    LocalDateTime getCreateAt();",
      "    InterfaceOne setCreateAt(LocalDateTime createAt);"
      "    LocalDateTime getUpdateAt();",
      "    InterfaceOne setUpdateAt(LocalDateTime updateAt);"
    ]
  }
]
