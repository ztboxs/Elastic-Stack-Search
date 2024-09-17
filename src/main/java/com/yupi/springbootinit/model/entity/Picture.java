package com.yupi.springbootinit.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

/**
 * 天天进步
 *
 * @Author: ztbox
 * @Date: 2024/09/15/23:43
 * @Description: 爬取图片赋予实体类
 */
@Data
public class Picture implements Serializable {

    private String title;

    private String url;

    private static final long serialVersionUID = 1L;

}
