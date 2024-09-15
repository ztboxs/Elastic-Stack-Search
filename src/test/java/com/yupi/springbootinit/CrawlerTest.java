package com.yupi.springbootinit;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yupi.springbootinit.model.entity.Picture;
import com.yupi.springbootinit.model.entity.Post;
import com.yupi.springbootinit.service.PostService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 天天进步
 *
 * @Author: ztbox
 * @Date: 2024/09/15/17:44
 * @Description: 爬虫测试类
 */

@SpringBootTest
public class CrawlerTest {

    @Resource
    private PostService postService;

    /**
     * 爬取帖子文章
     */
    @Test
    void testFetchPassage() {
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
        Assertions.assertTrue(code);
    }

    @Test
    void testFetchPicture() throws IOException {
        int current = 1;
        String url = "https://cn.bing.com/images/search?q=抽象图片&form=HDRSC2&first=" + current;
        Document doc = Jsoup.connect(url).get();
        Elements elements = doc.select(".iuscp.varh.isv ");
        List<Picture> pictures = new ArrayList<>();
        for (Element element : elements) {
            //1.取卡片，类名、取图片地址(murl)
            String m = element.select(".iusc").get(0).attr("m");
            Map<String,Object> map = JSONUtil.toBean(m,Map.class);
            String murl = (String) map.get("murl");
            //2.取标题地址(inflnk)
            String title = element.select(".inflnk").get(0).attr("aria-label");
            //3.封装进实体类中
            Picture picture = new Picture();
            picture.setTitle(title);
            picture.setUrl(url);
            pictures.add(picture);
        }
//        Elements newsHeadlines = doc.select("#mp-itn b a");
//        for (Element headline : newsHeadlines) {
//
//        }
    }

}
