package com.noone.skins.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.noone.skins.common.Result;
import com.noone.skins.entity.Share;
import com.noone.skins.mapper.ShareMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author xad
 * @create 2022-03-22 23:05
 * 写HTTP接口
 */
@RestController
@RequestMapping("/share")//定义路由
public class ShareController {

//    @Value("${server.port}")
//    private String port;
//
//    @Value("localhost")
//    private String ip;

    @Resource//引入sharemapper
    private ShareMapper shareMapper;

    //保存用户上传的分享数据到数据库
    @PostMapping("/dataShare")
    //定义一个post接口,此函数的参数是前台传过来的json数据，通过@RequestBody注解将参数转化为java对象，所以要定义一个java对象做参数///*泛型？：表示任何一种类型*/
    public Result<?> saveShare(@RequestBody Share share) {
        shareMapper.insert(share);
        return Result.success();
    }

    //通过ID查找
    @GetMapping("/listById/{id}")
    public Result<?> listById(@PathVariable Integer id) {
        Share share = shareMapper.selectById(id);
        return Result.success(share);
    }

    //按关键字分页查询，返回可分页的结果
    @GetMapping("/listAll")
    public Result<?> listPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        //pageNum对应前端传入的当前页数，pageSize对应前端传入的每页多少条,search前端传入的关键字：按关键字查询  并均设置默认值
        LambdaQueryWrapper<Share> wrapper = Wrappers.<Share>lambdaQuery();
        wrapper.like(Share::getDataName, search);      //再查找模糊匹配名称为关键字的
//        .eq(Share::getDataType, type)  //查找匹配数据库中data_type="video"的
        Page<Share> videoPage = shareMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(videoPage);
    }

    //点赞&点踩更新,put更新按钮
    @PutMapping("uLikes")
    public synchronized Result<?> changeLikes(@RequestParam Integer id, @RequestParam Integer type) {
        Integer likes = shareMapper.selectById(id).getLikes();
        Share share = shareMapper.selectById(id);
        if (type == 0) {
            share.setLikes(likes - 1);
            shareMapper.updateById(share);
        }
        if (type == 1) {
            share.setLikes(likes + 1);
            shareMapper.updateById(share);
        }
        return Result.success();
    }

    //新增图片更新,put更新按钮
    @PutMapping("uImgUrl")
    public synchronized Result<?> addImg(@RequestParam Integer id, @RequestParam String url) {
        String imgUrl = shareMapper.selectById(id).getImgUrl();
        Share share = shareMapper.selectById(id);
        imgUrl += ("," + url);
        if (imgUrl.charAt(0) == ',')
            imgUrl = imgUrl.substring(1);

        if (imgUrl.substring(0, 5).equals("null,"))
            imgUrl = imgUrl.substring(5);

        share.setImgUrl(imgUrl);
        shareMapper.updateById(share);
        return Result.success();
    }

    //图片上传接口，前端传入图像文件，后端返回url
    @PostMapping("/imgUpload")
    public Result<?> upload(MultipartFile file) throws IOException {
        String devUrl = "http://localhost:9090";
        String prodUrl = "http://wuhuback.vip.frp.wlphp.com:88";
        String eCloudUrl = "http://36.133.29.83:9090";

        String originalFilename = file.getOriginalFilename();  // 获取源文件的名称
        // 定义文件的唯一标识（前缀）防止同名文件上传被替换
        String flag = IdUtil.fastSimpleUUID();
        String rootFilePath = System.getProperty("user.dir") + "/files/shareData/" + flag + "_" + originalFilename;  // 获取上传的路径
        File saveFile = new File(rootFilePath);
        if (!saveFile.getParentFile().exists()) {
            saveFile.getParentFile().mkdirs();
        }
        FileUtil.writeBytes(file.getBytes(), rootFilePath);  // 把文件写入到上传的路径 //使用工具类，不需要自己进行数据流操作读写
        return Result.success(eCloudUrl /*+ ip + ":" + port */ + "/share/imgDownload/" + flag);  // 返回结果 url
    }

    //图片下载接口，前端传入参数：文件唯一表示flag（uuid），response对象：通过response对象可以把当前文件通过流的方式输出到浏览器，就实现了文件的下载
    @GetMapping("/imgDownload/{flag}")
    public void getFiles(@PathVariable String flag, HttpServletResponse response) {
        OutputStream os;  // 新建一个输出流对象
        String basePath = System.getProperty("user.dir") + "/files/shareData/";  // 定于文件上传的根路径
        List<String> fileNames = FileUtil.listFileNames(basePath);  // 获取所有的文件名称
        String fileName = fileNames.stream().filter(name -> name.contains(flag)).findAny().orElse("");  // 找到跟参数一致的文件
        try {
            if (StrUtil.isNotEmpty(fileName)) {
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
                response.setContentType("application/octet-stream");
                byte[] bytes = FileUtil.readBytes(basePath + fileName);  // 通过文件的路径读取文件字节流
                os = response.getOutputStream();   // 通过输出流返回文件
                os.write(bytes);
                os.flush();
                os.close();
            }
        } catch (Exception e) {
            System.out.println("文件下载失败");
        }
    }
}
