package com.sky.controller.admin;

import com.aliyun.oss.OSSException;
import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * @Author {liukun}
 * @e-mail:liukunjsj@163.com
 * @Date: 2026/01/02/ 20:01
 * @description  通用接口
 */
@RestController
@RequestMapping("/admin/common")
@Api(tags = "通用接口")
@Slf4j
public class CommonController {

    @Autowired
    private AliOssUtil aliOssUtil;

    /**
     * 文件上传
     * @param file
     * @return
     */
    @PostMapping("/upload")
    @ApiOperation("文件上传")
    public Result<String> upload(MultipartFile file){
        log.info("文件上传：{}", file);

        try {
            //原始文件名
            String originFileName = file.getOriginalFilename();
            //截取原始文件名后缀
            String suffix = originFileName.substring(originFileName.lastIndexOf("."));
            //生成新的文件名
            String objectName = UUID.randomUUID().toString() + suffix;
            //文件的请求路径
            String filePath = aliOssUtil.upload(file.getBytes(), objectName);

            return Result.success(filePath);
        } catch (OSSException ossEx) {
            log.error("OSS 上传被拒绝或权限问题：{}, RequestId={}, ErrorCode={}", ossEx.getMessage(), ossEx.getRequestId(), ossEx.getErrorCode());

        } catch (IOException e) {
            log.error("文件读取失败：{}", e.getMessage(), e);

        } catch (Exception e) {
            log.error("未知上传异常：{}", e.getMessage(), e);

        }
        return Result.error(MessageConstant.UPLOAD_FAILED);
    }
}
