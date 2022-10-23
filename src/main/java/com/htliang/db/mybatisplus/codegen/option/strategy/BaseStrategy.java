/*
 * Copyright (c) 2022, Hongtao Liang (lhongtao.hometown@gmail.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
