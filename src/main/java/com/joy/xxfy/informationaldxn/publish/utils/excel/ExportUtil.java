package com.joy.xxfy.informationaldxn.publish.utils.excel;

import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Excel导出工具类
 */
public class ExportUtil {

    public static void exportData(HttpServletRequest request, HttpServletResponse response, String fileName,
                                  ExcelWriter writer) {
        response.setContentType("application/force-download;charset=utf-8");
        String agent = request.getHeader("USER-AGENT").toLowerCase();
        String codedFileName = null;
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
                        "attachment;filename=" + new String(fileName.getBytes(), "ISO8859-1") + ".xls");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            response.setHeader("content-disposition", "attachment;filename=" + codedFileName + ".xls");
        }
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        writer.flush(out);
        writer.close();
        IoUtil.close(out);
    }
}
