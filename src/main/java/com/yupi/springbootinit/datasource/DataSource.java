package com.yupi.springbootinit.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * 天天进步
 *
 * @Author: ztbox
 * @Date: 2024/09/18/22:19
 * @Description: 数据源接口（新接入的数据源必须实现）
 */
public interface DataSource<T> {

    Page<T> doSearch(String searchText, long pageNum, long pageSize);

}
