package com.yupi.springbootinit.model.vo;

import com.yupi.springbootinit.model.entity.Picture;
import lombok.Data;

import java.util.List;

/**
 * 天天进步
 *
 * @Author: ztbox
 * @Date: 2024/09/17/14:51
 * @Description: 聚合搜索实体封装类
 */
@Data
public class SearchVo {

    private List<UserVO> userList;

    private List<PostVO> postList;

    private List<Picture> pictureList;

}
