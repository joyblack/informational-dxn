package com.joy.xxfy.informationaldxn.publish.utils;

import com.joy.xxfy.informationaldxn.publish.exception.JoyException;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

public class FileUtil {

    public static void save(byte[] files, String path, String fileName) throws IOException {
        // 目标目录
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        // 二进制流写入
        File file1 = new File(path + File.separator + fileName);
        if (!file1.exists()) {
            file1.createNewFile();
        }
        FileOutputStream fileOut = new FileOutputStream(file1);
        fileOut.write(files);
        fileOut.flush();
        fileOut.close();
    }


    /**
     * By zhaoyi. 下载文件
     */
    public static void downloadFile(String fileName, String path, HttpServletRequest request, HttpServletResponse response) {
            File file = new File(path);
            if (file.exists()) {
                // 设置强制下载不打开
                response.setContentType("application/force-download");
                // 设置文件名
                String codedFileName = null;
                String agent = request.getHeader("USER-AGENT").toLowerCase();
                try {
                    codedFileName = URLEncoder.encode(fileName, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if (agent.contains("firefox")) {
                    response.setCharacterEncoding("utf-8");
                    try {
                        response.setHeader(
                                "content-disposition",
                                "attachment;filename=" + new String(fileName.getBytes(), "ISO8859-1"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                } else {
                    response.setHeader("content-disposition", "attachment;filename=" + codedFileName);
                }
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                } catch (Exception e) {
                    throw new JoyException(JoyResult.buildFailedResult("文件读写错误"));
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            throw new JoyException(JoyResult.buildFailedResult("文件读写错误"));
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            throw new JoyException(JoyResult.buildFailedResult("文件读写错误"));
                        }
                    }
                }
            }
    }



    public static void deleteByFilePath(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
    }

    public static boolean fileNamePostfixCheck(String filePostfix, String postfix) {
        if (StringUtil.isEmpty(filePostfix)) {
            return false;
        }
        String[] filePostfixs = filePostfix.split(";");
        for (String str : filePostfixs) {
            if (StringUtil.equals(str, postfix)) {
                return true;
            }
        }
        return false;
    }


    public static void getOutputStream(String filePath,HttpServletRequest request,HttpServletResponse response) {

    }

    // 获取文件类型后缀
    public static String getFileExtension(String filename){
        if(StringUtil.isEmpty(filename)){
            return "";
        }
        String[] names = filename.trim().split("\\.");
        if (null == names || names.length == 0 || names.length == 1) {
            return "";
        }
        return names[names.length - 1];
    }
}
