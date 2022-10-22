package com.htliang.db.mybatisplus.codegen.option;

import com.baomidou.mybatisplus.generator.config.OutputFile;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class PackageOption {
    private String parent;

    private String moduleName;

    private String entity = "entity";

    private String service = "service";

    private String serviceImpl = "service.impl";

    private String mapper = "mapper";

    private String xml = "mapper.xml";

    private String controller = "controller";

    private Map<OutputFile, String> pathInfo;
}
