package com.yupi.springbootinit.job.once;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yupi.springbootinit.esdao.PostEsDao;
import com.yupi.springbootinit.model.dto.post.PostEsDTO;
import com.yupi.springbootinit.model.entity.Post;
import com.yupi.springbootinit.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 获取初始帖子数据
 *

 */
// todo 取消注释后，每次启动springBoot 项目时候会执行异常run方法
//@Component
@Slf4j
public class FetchlnitPostList implements CommandLineRunner {

    @Resource
    private PostService postService;


    @Override
    public void run(String... args) {
        //Todo 1.数据获取
        String url = "https://api.code-nav.cn/api/post/list/page/vo";
        String json = "{\"pageSize\":12,\"sortOrder\":\"descend\",\"sortField\":\"createTime\",\"tags\":[],\"current\":1,\"reviewStatus\":1,\"category\":\"文章\",\"hiddenContent\":true}";
        String result = HttpRequest.post(url)
                .body(json)
                .execute().body();

        //Todo 2.解析Json数据 转换为对象
        Map<String,Object> JsonMap = JSONUtil.toBean(result,Map.class);
        JSONObject data = (JSONObject) JsonMap.get("data");
        JSONArray records = (JSONArray) data.get("records");
        List<Post> postList = new ArrayList<>();
        for (Object record : records) {
            JSONObject tempRecord = (JSONObject) record;
            Post post = new Post();
            post.setTitle(tempRecord.getStr("title"));
            post.setContent(tempRecord.getStr("content"));
            //集合转换一下
            JSONArray tags = (JSONArray) tempRecord.get("tags");
            List<String> tagList = tags.toList(String.class);
            post.setTags(JSONUtil.toJsonStr(tagList));
            post.setUserId(1L);
            postList.add(post);
        }
        //Todo 3.数据入库
        boolean code = postService.saveBatch(postList);
        if (code) {
            log.info("获取初始初始化帖子成功，条数：{}", postList.size());
        } else {
            log.error("获取初始初始化帖子失败！");
        }
    }
}
