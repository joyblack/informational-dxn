# 简介

# 访问地址
staff-entry/get

# 请求参数

## 请求方式
POST

## 请求格式
JSON

## 请求数据
|参数名|类型|必填|说明|
|-|-|-|-|
|id|[number]|是|记录ID|

## 请求示例
```json
{
	"id": 1
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
        "createTime": "2019-08-18 18:42:25",
        "updateTime": "2019-08-18 18:42:25",
        "remarks": null,
        "company": {
            "id": 1,
            "isDelete": false,
            "createTime": "2019-08-18 08:46:12",
            "updateTime": "2019-08-18 08:46:12",
            "remarks": null,
            "departmentName": "贵州采矿平台_change",
            "code": "",
            "parentId": 0,
            "responseUser": "赵小艺2_change",
            "phone": "13535565497",
            "departmentType": "CM_PLATFORM",
            "path": null,
            "children": null
        },
        "department": {
            "id": 3,
            "isDelete": false,
            "createTime": "2019-08-18 16:47:03",
            "updateTime": "2019-08-18 16:47:03",
            "remarks": null,
            "departmentName": "信息分院561",
            "code": "00",
            "parentId": 2,
            "responseUser": "jake",
            "phone": "13535565497",
            "departmentType": "CM_PLATFORM",
            "path": "null2-3-",
            "children": null
        },
        "position": {
            "id": 3,
            "isDelete": false,
            "createTime": "2019-08-17 14:06:58",
            "updateTime": "2019-08-17 14:06:58",
            "remarks": "备注信息...",
            "positionName": "业务员3",
            "describes": "测试职位数据"
        },
        "entryTime": "2015-06-06 16:04:04",
        "physicalExaminationHospital": "贵阳医科院111122222",
        "physicalExaminationTime": "2015-06-06 16:04:04",
        "remuneration": 10000,
        "staffStatus": "INCUMBENCY",
        "staffPersonal": {
            "id": 1,
            "isDelete": false,
            "createTime": "2019-08-18 18:03:35",
            "updateTime": "2019-08-18 18:03:35",
            "remarks": null,
            "idNumber": "522401199401025931",
            "username": "赵小艺11111",
            "sex": "MAN",
            "nationality": "汉族11111111111111",
            "birthDate": "2015-06-06 16:04:04",
            "education": "MASTER",
            "accountCharacter": "CITY_CHARACTER",
            "phone": "13535565497",
            "homeAddress": "33333333333333333333333333",
            "insured": "YES",
            "insuredTime": "2011-09-06 16:04:04",
            "nativePlace": "贵州省毕节市",
            "graduationCollege": "贵州大学",
            "graduationTime": "2015-06-06 16:04:04",
            "profession": "电子商务111111"
        },
        "reviewStatus": "PASS",
        "reviewReasons": null,
        "reviewTime": null,
        "reviewUser": null
    },
    "code": 200
}
```

**失败**
```json
{
    "state": false,
    "message": "员工信息不存在",
    "detailMessage": "员工信息不存在",
    "data": null,
    "code": 103000
}
```

# 备注
错误码参见错误码对照表。