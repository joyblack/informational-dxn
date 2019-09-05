package com.joy.xxfy.informationaldxn.publish.constant;

import java.io.File;

// 文件存放地址
public class StoreFilePathConstant {
    /**
     * 员工
     */
    public static final String STAFF_IDENTITY_PHOTO = "staff" + File.separator + "identity_photo";
    public static final String STAFF_ONE_INCH_PHOTO = "staff" + File.separator + "one_inch_photo";

    // 培训
    // == 培训图片
    public static final String TRAINING_PHOTO = "train_photo";
    // == 培训附件
    public static final String TRAINING_ATTACHMENT = "train_attachment";

    // == 云盘文件：公共模块
    public static final String PAN_PUBLIC = "pan_public";

    // == 云盘文件：私有模块
    public static final String PAN_PRIVATE = "pan_private";

}
