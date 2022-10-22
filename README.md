这是一个利用mariaDB和DDL文件来生成代码的maven插件

支持联合主键生成（自动处理保留非分区键的第一个主键） 例如primary key (`account_id`,`id`)...partition by hash (`account_id`) -> primary key (`id`)

使用教程后面有空补上，中心仓有空再发布，熟悉plugin使用的同学可以看下 codegenOptions, 几乎所有参数默认值与mybatis plus官网一致

参考如下
``` xml
            <plugin>
                <groupId>com.htliang.db</groupId>
                <artifactId>mybatis-plus-codegen-plugin</artifactId>
                <version>1.0.1-SNAPSHOT</version>
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
                <executions>
                    <execution>
                        <phase>generate-sources</phase>
                        <goals>
                            <goal>generate</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
```
