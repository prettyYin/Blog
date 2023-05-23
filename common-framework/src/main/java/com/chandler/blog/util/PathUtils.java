package com.chandler.blog.util;
import java.util.UUID;

public class PathUtils {

    /**
     * 生成文件路径
     * @param fileName
     * @param pathName
     * @return
     */
    public static String generateFilePath(String fileName,String pathName){
        //根据日期生成路径   xxxx/xx/xx/
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
//        String datePath = sdf.format(new Date());
        //uuid作为文件名
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        //后缀和文件后缀一致
        int index = fileName.lastIndexOf(".");
        // test.jpg -> .jpg
        String fileType = fileName.substring(index);
//        return new StringBuilder().append(datePath).append(uuid).append(fileType).toString();
        return new StringBuilder().append(pathName + "/").append(uuid).append(fileType).toString();
    }

}