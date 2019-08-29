# 简介

# 访问地址
document-pan-file/get

# 请求参数

## 请求方式
POST

## 请求格式
JSON

## 请求数据
|参数名|类型|必填|说明|
|-|-|-|-|
|id|[number]|是|记录的ID|

## 请求示例
```json
{
	"id": 6
}
```

# 返回结果
**成功**
```json
{
    "state": true,
    "message": "操作成功",
    "detailMessage": "",
    "data": {
        "id": 5,
        "isDelete": false,
        "createTime": "2019-08-29 16:17:31",
        "updateTime": "2019-08-29 16:17:31",
        "remarks": null,
        "createUser": {
            "id": 1,
            "isDelete": false,
            "createTime": "2019-08-26 20:07:29",
            "updateTime": "2019-08-26 20:07:29",
            "remarks": "This is a created by sysytem super admin user, it belong to super company, don't delete it!",
            "idNumber": null,
            "loginName": "admin",
            "phone": "13535565497",
            "password": "e10adc3949ba59abbe56e057f20f883e",
            "affirmPassword": null,
            "username": "超级管理员",
            "userType": "CP_ADMIN",
            "status": "START",
            "permissions": null,
            "department": {
                "id": 1,
                "isDelete": false,
                "createTime": "2019-08-26 20:07:29",
                "updateTime": "2019-08-26 20:07:29",
                "remarks": null,
                "departmentName": "大西南矿业集团",
                "code": "SUPER-COMPANY",
                "parentId": 0,
                "responseUser": null,
                "phone": null,
                "departmentType": "CP_GROUP",
                "path": "1-",
                "children": []
            },
            "company": {
                "id": 1,
                "isDelete": false,
                "createTime": "2019-08-26 20:07:29",
                "updateTime": "2019-08-26 20:07:29",
                "remarks": null,
                "departmentName": "大西南矿业集团",
                "code": "SUPER-COMPANY",
                "parentId": 0,
                "responseUser": null,
                "phone": null,
                "departmentType": "CP_GROUP",
                "path": "1-",
                "children": []
            }
        },
        "belongCompany": {
            "id": 1,
            "isDelete": false,
            "createTime": "2019-08-26 20:07:29",
            "updateTime": "2019-08-26 20:07:29",
            "remarks": null,
            "departmentName": "大西南矿业集团",
            "code": "SUPER-COMPANY",
            "parentId": 0,
            "responseUser": null,
            "phone": null,
            "departmentType": "CP_GROUP",
            "path": "1-",
            "children": []
        },
        "parentId": 2,
        "newFileName": "b9942c2fa4a14b13b1aa110f56f2fa81.pdf",
        "fileName": "Rock Fractures and Fluid Flow Contemporary Understanding and Applications.pdf",
        "fileSize": 19851407,
        "fileType": "pdf",
        "path": "/能源大数据/Rock Fractures and Fluid Flow Contemporary Understanding and Applications.pdf",
        "storePath": "E:\\informational_dxn\\pan_public\\1",
        "permissionType": "PER_PUBLIC",
        "isFolder": false
    },
    "code": 200
}
```

# 备注
错误码参见错误码对照表。