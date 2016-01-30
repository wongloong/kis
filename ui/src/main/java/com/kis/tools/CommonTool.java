package com.kis.tools;

import com.kis.model.system.Result;
import com.kis.tools.Exception.ParamsIsNullException;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 工具类
 * Created by yks on 15-10-10.
 */
public class CommonTool {
    public static Logger log = Logger.getLogger(CommonTool.class);

    /**
     * 判断对象不为空
     *
     * @param o 接受Object类型不定参数
     * @return boolean
     */
    public static boolean assertIsNotNull(Object... o) {
        boolean result = true;
        for (int i = 0; i < o.length; i++) {
            if (null == o[i]) {
                throw new ParamsIsNullException("参数不能为空");
            }
            String className = o[i].getClass().toString();
            if (className.endsWith("List")) {
                List list = (List) o[i];
                if (list.size() == 0) {
                    throw new ParamsIsNullException("参数List不能为空");
                }
            }
            if (className.endsWith("Map")) {
                Map map = (Map) o[i];
                if (map.size() == 0) {
                    throw new ParamsIsNullException("参数Map不能为空");
                }
            }

        }
        return result;
    }

    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    public static Result uploadFile(String path, MultipartFile[] files) {
        String msg = "操作成功";
        String saveName = "";
        boolean hasSuccess = true;
        try {
            File file = new File(path);
            if (null != path && file.isDirectory()) {
                for (MultipartFile multipartFile : files) {
                    if (multipartFile.getSize() == 0) {
                        msg = "上传文件为空";
                        hasSuccess = false;
                        log.error(msg);
                        break;
                    }
                    String fileName = multipartFile.getOriginalFilename();
                    if (fileName.indexOf(".") >= 0) {
                        String exName = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
                        saveName=getUUID()+exName;
                        multipartFile.transferTo(new File(path + "/" +saveName));
                    } else {
                        hasSuccess = false;
                        msg = "上传文件没有扩展名";
                        log.error(msg);
                        break;
                    }
                }
            } else {
                msg = "上传路径错误";
                hasSuccess = false;
                log.error(msg + path);
            }
        } catch (Exception e) {
            e.printStackTrace();
            hasSuccess = false;
        }
        return new Result(hasSuccess, msg,saveName);
    }


}
