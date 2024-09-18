package com.yupi.springbootinit.datasource;

import com.yupi.springbootinit.model.enums.SearchTypeEnum;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 天天进步
 *
 * @Author: ztbox
 * @Date: 2024/09/18/23:43
 * @Description:
 */
@Component
public class DataSourceRegistry {

    @Resource
    private UserDataSource userDataSource;

    @Resource
    private PostDataSource postDataSource;

    @Resource
    private PictureDataSource pictureDataSource;
    //Todo 设计模型--注册模式
    private Map<String, DataSource<T>> typeDataSourceMap;

    @PostConstruct
    public void  doInit() {
        typeDataSourceMap = new HashMap() {{
            put(SearchTypeEnum.USER.getValue(), userDataSource);
            put(SearchTypeEnum.POST.getValue(), postDataSource);
            put(SearchTypeEnum.PICTURE.getValue(), pictureDataSource);
        }};

    }

    public DataSource getDataSourceByType(String type) {
        if(typeDataSourceMap == null) return null;
        return typeDataSourceMap.get(type);
    }

}
