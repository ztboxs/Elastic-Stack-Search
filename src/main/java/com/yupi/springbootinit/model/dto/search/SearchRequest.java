package com.yupi.springbootinit.model.dto.search;

import com.yupi.springbootinit.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 天天进步
 *
 * @Author: ztbox
 * @Date: 2024/09/17/14:54
 * @Description: 聚合搜索请求实体返回
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SearchRequest  extends PageRequest implements Serializable {

    /**
     * 搜索词
     */
    private String searchText;

    /**
     * 类型
     */
    private String type;

    private static final long serialVersionUID = 1L;

}
