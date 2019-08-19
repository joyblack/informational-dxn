# 简介
获取员工无关入职的个人信息，由于ID是全局唯一的，该接口返回的数据是唯一的。

# 访问地址
staff-personal/get

# 请求参数

## 请求方式
POST

## 请求格式
JSON

## 请求数据
|参数名|类型|必填|说明|
|-|-|-|-|
|id|[number]|是|个人信息的ID|

## 请求示例
```json
{
	"id": "1"
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
        "id": 1,
        "isDelete": false,
        "createTime": "2019-08-19 16:53:12",
        "updateTime": "2019-08-19 16:53:12",
        "remarks": null,
        "idNumber": "522401199401025931",
        "username": "赵小艺",
        "sex": "MAN",
        "nationality": "汉族",
        "birthDate": "2015-06-06 16:04:04",
        "education": "DOCTOR",
        "accountCharacter": "COUNTRYSIDE_CHARACTER",
        "phone": "13535565497",
        "homeAddress": "贵州省毕节市燕子口镇111",
        "insured": "YES",
        "insuredTime": null,
        "nativePlace": null,
        "graduationCollege": null,
        "graduationTime": null,
        "profession": "电子商务",
        "idNumberPhoto": {
            "id": 1,
            "isDelete": false,
            "createTime": "2019-08-19 16:52:14",
            "updateTime": "2019-08-19 16:52:14",
            "remarks": null,
            "uploadModule": "STAFF_ID_NUMBER_PHOTO",
            "storePath": "E:\\informational_dxn\\STAFF_ID_NUMBER_PHOTO\\e69587eb9b044375815a97171dc25c02.jpg",
            "fileName": "e69587eb9b044375815a97171dc25c02.jpg",
            "fileSize": 30811
        },
        "oneInchPhoto": {
            "id": 2,
            "isDelete": false,
            "createTime": "2019-08-19 16:52:19",
            "updateTime": "2019-08-19 16:52:19",
            "remarks": null,
            "uploadModule": "STAFF_ONE_INCH_PHOTO",
            "storePath": "E:\\informational_dxn\\STAFF_ID_NUMBER_PHOTO\\31f304e91b434887ab2639d61a29aabf.jpg",
            "fileName": "31f304e91b434887ab2639d61a29aabf.jpg",
            "fileSize": 30811
        }
    },
    "code": 200
}
```

**失败**
```json
{
    "state": false,
    "message": "请求参数缺失或未空值",
    "detailMessage": "id不能为空",
    "data": null,
    "code": 100000
}
```

# 备注
错误码参见错误码对照表。