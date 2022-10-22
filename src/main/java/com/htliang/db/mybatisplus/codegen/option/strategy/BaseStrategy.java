package com.htliang.db.mybatisplus.codegen.option.strategy;

import com.baomidou.mybatisplus.core.enums.SqlLike;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseStrategy {
    private boolean enableCapitalMode = false;

    private boolean enableSkipView = false;

    private boolean disableSqlFilter = false;

    private boolean enableSchema = false;

    private String likeTableValue = "";

    private SqlLike likeTableSqlLike= SqlLike.DEFAULT;

    private String notLikeTableValue = "";

    private SqlLike notLikeTableSqlLike = SqlLike.DEFAULT;

    private String[] includeColumns = new String[0];

    private String[] excludeColumns= new String[0];

    private String[] tablePrefixes= new String[0];

    private String[] tableSuffixes= new String[0];

    private String[] fieldPrefixes= new String[0];

    private String[] fieldSuffixes= new String[0];
}
