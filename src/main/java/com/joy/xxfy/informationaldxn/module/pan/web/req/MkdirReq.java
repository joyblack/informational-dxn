package com.joy.xxfy.informationaldxn.module.pan.web.req;

import com.joy.xxfy.informationaldxn.module.common.web.req.BaseAddReq;
import com.joy.xxfy.informationaldxn.module.pan.domain.enums.PermissionTypeEnum;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ToString
public class MkdirReq extends BaseAddReq {

    @NotNull(message = "父文件夹ID不能为空")
    private Long parentId;

    @NotBlank(message = "文件夹名称不能为空")
    private String fileName;

    @NotNull(message = "文件权限类型不能为空")
    private PermissionTypeEnum permissionType;

    // 若选择的是公开权限：并且当前的账户是集团账户，则还需提交当前操作的文件是属于哪个平台/公司。
    // 主要是由于集团可以操作其他公司的文件，导致后台不能单纯的通过当前登录的账户来判断文件的所属平台，因此需要设置该选项。
    private Long belongCompanyId;

}
