# 简介
获取入职审核列表，支持分页获取。其实和员工列表的获取没有多大区别，只不过该接口默认情况下只会返回未审核和审核失败的两类员工数据。

# 访问地址
staff-review/getPagerList

# 请求参数

## 请求方式
POST

## 请求格式
JSON

## 请求数据
|参数名|类型|必填|说明|
|-|-|-|-|
|username|[string]||查询关键字：姓名|
|idNumber|[string]||查询关键字：身份证|
|phone|[string]||查询关键字：联系方式（电话号码）|
|birthDateStart|[date]||查询关键字：出生日期开始时间|
|birthDateEnd|[date]||查询关键字：出生日期截止时间|
|entryDateStart|[date]||查询关键字：入职开始时间|
|entryDateEnd|[date]||查询关键字：入职截止时间|
|sex|[string]||性别：0-MAN 1-WOM|
|nationality|[string]||民族：例如汉族|
|education|[string]||教育程度、学历，参考参数表|
|reviewStatus|[number]||审核状态：参考参数表|
|departmentId|[number]||部门ID|
|positionId|[number]||职位ID|

**分页参数参考《参数参考表》中分页参数模块。**

## 请求示例
```json
{
	"username": "",
	"birthDateStart": "1916-01-01",
	"birthDateEnd": "2020-01-01",
	"entryTimeStart": "2016-01-01",
	"entryTimeEnd": "2020-01-01",
	"sex": 0,
	"nationality": "汉族",
	"education": 0,
	"reviewStatus": 0,
    "departmentId": 2,
    "postionId": 11
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
        "content": [
            {
                "id": 2,
                "isDelete": false,
                "createTime": "2019-08-18 16:36:20",
                "updateTime": "2019-08-18 16:36:20",
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
                    "id": 2,
                    "isDelete": false,
                    "createTime": "2019-08-18 16:21:10",
                    "updateTime": "2019-08-18 16:21:10",
                    "remarks": null,
                    "departmentName": "信息分院561",
                    "code": "00",
                    "parentId": 1,
                    "responseUser": "jake",
                    "phone": "13535565497",
                    "departmentType": "CM_PLATFORM",
                    "path": "null2-",
                    "children": null
                },
                "position": {
                    "id": 1,
                    "isDelete": false,
                    "createTime": "2019-08-17 14:06:58",
                    "updateTime": "2019-08-17 14:06:58",
                    "remarks": "备注信息...",
                    "positionName": "业务员1",
                    "describes": "测试职位数据"
                },
                "entryTime": "2015-06-06 16:04:04",
                "physicalExaminationHospital": "贵阳医科院",
                "physicalExaminationTime": "2015-06-06 16:04:04",
                "remuneration": 10000,
                "staffStatus": "INCUMBENCY",
                "staffPersonal": {
                    "id": 1,
                    "isDelete": false,
                    "createTime": "2019-08-19 08:32:41",
                    "updateTime": "2019-08-19 08:32:41",
                    "remarks": null,
                    "idNumber": "522401199401025931",
                    "username": "赵小艺11111",
                    "sex": "MAN",
                    "nationality": "汉族11111111111111",
                    "birthDate": "2015-06-06 16:04:04",
                    "education": "MASTER",
                    "accountCharacter": "CITY_CHARACTER",
                    "phone": "13535565497",
                    "homeAddress": "777777777777777777777777",
                    "insured": "YES",
                    "insuredTime": "2011-09-06 16:04:04",
                    "nativePlace": "贵州省毕节市",
                    "graduationCollege": "贵州大学",
                    "graduationTime": "2015-06-06 16:04:04",
                    "profession": "电子商务111111"
                },
                "reviewStatus": "WAIT",
                "reviewReasons": "已在贵州采矿平台_change入职，职务为业务员1\r\n",
                "reviewRemarks": null,
                "reviewTime": null,
                "reviewUser": null
            }
        ],
        "pageable": {
            "sort": {
                "sorted": false,
                "unsorted": true,
                "empty": true
            },
            "offset": 0,
            "pageNumber": 0,
            "pageSize": 10,
            "paged": true,
            "unpaged": false
        },
        "last": true,
        "totalPages": 1,
        "totalElements": 1,
        "size": 10,
        "number": 0,
        "sort": {
            "sorted": false,
            "unsorted": true,
            "empty": true
        },
        "numberOfElements": 1,
        "first": true,
        "empty": false
    },
    "code": 200
}
```

**失败**
若传递非法参数，则会出现查询异常。
```json
{
    "code": 406,
    "detailMessage": "\r\n### Error querying...",
    "state": false,
    "data": null,
    "message": "服务器繁忙，请稍后再试"
}
```

# 备注
日期格式只要符合标准格式均可，为了统一模式，可以设置为`%Y-%m-%d %H:%i%s` 这样的形式，例如

错误码参见错误码对照表。