package com.joy.xxfy.informationaldxn.module.document.web.req;

import com.joy.xxfy.informationaldxn.module.common.web.req.BasePageReq;
import com.joy.xxfy.informationaldxn.module.document.domain.enums.PermissionTypeEnum;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@ToString(callSuper = true)
public class FileGetListReq extends BasePageReq {
    /**
     * 权限类型
     */
    @NotNull(message = "权限类型不能为空")
    private PermissionTypeEnum permissionType;

    /**
     * 父文件夹ID
     */
    @NotNull(message = "父文件夹信息不能为空")
    private Long parentId;

    /**
     * 所属公司ID
     */
    private Long belongCompanyId;

    /**
     *  是否区分文件夹：
     *  - 若只想获取文件，只需将此值设置为false
     *  - 若只想获取文件夹，只需将此值设置为true
     *  - 若想获取全部，忽略设置此值，或者设置为null
     */
    private Boolean isFolder;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 上传日期区间
     */
    private Date createTimeStart;
    private Date createTimeEnd;



}
