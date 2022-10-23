# MyBatis plus codegen plugin

[![License](https://img.shields.io/:license-apache-brightgreen.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

Mybatis plus codegen plugin will help you generate your code at every moment without real running database;

## 简介

- 解决mybatis plus codegen依赖于真实数据库环境，变成依赖sql文件，在codegen时启用临时mariaDB进行代码生成
- 解决mybatis plus codegen无法处理联合主键的问题，先通过JsqlParser去除联合主键和分区，联合查询可在代码生成后自己实现
- 配置参数与Mybatis plus codegen官网保持一致，默认值也一致。可直接参考 https://baomidou.com/pages/981406/#%E5%8F%AF%E9%80%89%E9%85%8D%E7%BD%AE

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
