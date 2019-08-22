package com.joy.xxfy.informationaldxn.common.web.res;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class FileInfoRes {
    // 文件的存储路径
    private String filePath;

    // 文件的名称
    private String filename;
}
