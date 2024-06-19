package com.example.springbootdemo.controller;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springbootdemo.common.Result;
import com.example.springbootdemo.entity.Files;
import com.example.springbootdemo.entity.User;
import com.example.springbootdemo.mapper.FileMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * 文件上传相关接口
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${files.upload.path}")
    private String fileUploadPath;

    @Resource
    private FileMapper fileMapper;

    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) throws IOException {
        String originalFileName = file.getOriginalFilename();
        String type = FileUtil.extName(originalFileName);
        long size = file.getSize();

        // 定义文件唯一的标识位
        String uuid = IdUtil.fastSimpleUUID();
        String fileUuid = uuid + StrUtil.DOT + type;
        File uploadFile = new File(fileUploadPath + fileUuid);
        // 判断配置文件的目录是否存在，不存在就创建一个
        if (!uploadFile.getParentFile().exists()) {
            uploadFile.getParentFile().mkdirs();
        }

        String url;
        // 把获取到的文件存储到磁盘目录
        file.transferTo(uploadFile);
        String md5 = SecureUtil.md5(uploadFile);
        // 从数据库中查询是否有相同的记录
        Files dbFiles = getFileMd5(md5);
        if(dbFiles != null){
            url = dbFiles.getUrl();
            // 由于文件已经存在，所以删除刚才上传的重复文件
            uploadFile.delete();
        }else{
            // 数据库不存在该文件，不删除
            url = "http://localhost:9090/file/" + fileUuid;
        }


        // 存储数据库
        Files saveFile = new Files();
        saveFile.setName(originalFileName);
        saveFile.setType(type);
        saveFile.setSize(size/1024);
        saveFile.setUrl(url);
        saveFile.setMd5(md5);
        fileMapper.insert(saveFile);
        return url;
    }

    @GetMapping("/{fileUUID}")
    public void download(@PathVariable String fileUUID, HttpServletResponse response) throws IOException {
        // 根据文件唯一标识码获取文件
        File uploadFile = new File(fileUploadPath + fileUUID);
        ServletOutputStream os = response.getOutputStream();
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileUUID, "UTF-8"));
        response.setContentType("application/octet-stream");
        // 读取文件字节流
        os.write(FileUtil.readBytes(uploadFile));
        os.flush();
        os.close();
    }

    /**
     * 通过md5查询文件是否已经在数据库中
     * @param md5
     * @return
     */
    private Files getFileMd5(String md5){
        // 查询文件的md5是否存在
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("md5", md5);
        List<Files> filesList = fileMapper.selectList(queryWrapper);
        return filesList.isEmpty() ? null : filesList.get(0);
    }

    /**
     * 分页查询接口
     * @param pageNum
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String name){
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_delete", false);
        queryWrapper.orderByDesc("id");
        if(!"".equals(name)){
            queryWrapper.like("name", name);
        }

        return Result.success(fileMapper.selectPage(new Page<>(pageNum, pageSize), queryWrapper));
    }

    // 删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        Files files = fileMapper.selectById(id);
        files.set_delete(true);
        fileMapper.updateById(files);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", ids);
        List<Files> filesList = fileMapper.selectList(queryWrapper);
        for (Files files : filesList){
            files.set_delete(true);
            fileMapper.updateById(files);
        }
        return Result.success();
    }

    @PostMapping("/update")
    public Result save(@RequestBody Files files){
        return Result.success(fileMapper.updateById(files));
    }
}
