# 简介
获取职位列表，分页查询。

# 访问地址
position/getAllList

# 请求参数

## 请求方式
POST

## 请求格式
JSON

## 请求数据
|参数名|类型|必填|说明|
|-|-|-|-|
|search|string|否|通用查询子串，可对职位名称进行模糊查询。|

## 请求示例
```json
{
	"search": "xiaoyao",
	"page": 2,
	"size": 3,
	"sort": "createTime",
	"order": "desc"

}
```

# 返回结果
**成功**
```json
{
    "state": true,
    "message": "操作成功",
    "detailMessage": "",
    "data": [
        {
            "id": 10,
            "isDelete": false,
            "createTime": "2019-08-17 14:06:58",
            "updateTime": "2019-08-17 14:06:58",
            "remarks": "备注信息...",
            "positionName": "业务员10",
            "describes": "测试职位数据"
        },
        {
            "id": 100,
            "isDelete": false,
            "createTime": "2019-08-17 14:06:59",
            "updateTime": "2019-08-17 14:06:59",
            "remarks": "备注信息...",
            "positionName": "业务员100",
            "describes": "测试职位数据"
        }
    ],
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
错误码参见错误码对照表。