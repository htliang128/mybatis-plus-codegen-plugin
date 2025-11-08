package com.htliang.db.mybatisplus.codegen.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.codehaus.plexus.util.ExceptionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class JacksonUtil {
    private static final ObjectMapper yamlReader = new ObjectMapper(new YAMLFactory());

    private static final ObjectMapper jsonMapper = new ObjectMapper();

    @SneakyThrows
    public static <T> T yaml2Pojo(String yamlStr, Class<T> className) {
        return yamlReader.readValue(yamlStr, className);
    }

    @SneakyThrows
    public static <T> List<T> yamlArray2PojoList(String yamlStr, Class<T> className) {
        if (Strings.isEmpty(yamlStr)) {
            return null;
        }

        CollectionType listType = yamlReader
            .getTypeFactory()
            .constructCollectionType(List.class, className);
        return yamlReader.readValue(yamlStr, listType);
    }

    @SneakyThrows
    public static <K, V> Map<K, V> json2Map(String jsonData, Class<K> keyType, Class<V> valueType) {
        JavaType javaType = jsonMapper
            .getTypeFactory()
            .constructMapType(Map.class, keyType, valueType);
        return jsonMapper.readValue(jsonData, javaType);
    }

    public static String pojo2Json(Object obj) {
        String jsonStr = "";

        try {
            jsonStr = jsonMapper.writeValueAsString(obj);
        } catch (Exception exception) {
            log.error(ExceptionUtils.getStackTrace(exception));
        }

        return jsonStr;
    }
}
