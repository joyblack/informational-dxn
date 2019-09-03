# 简介
统计月度数据，需要注意的是，由于前年没有数据信息，因此在此处我们不展示同比数据（红色折线），因此只需展示堆叠柱状图即可。

# 访问地址
statistic-drill/getEveryMonth

# 请求参数

## 请求方式
POST

## 请求格式
JSON

## 请求数据
无

## 请求示例
无

# 返回结果
**成功**
```json
{
    "state": true,
    "message": "操作成功",
    "detailMessage": "",
    "data": [
        {
            "name": "01月",
            "value": 0
        },
        {
            "name": "02月",
            "value": 0
        },
        {
            "name": "03月",
            "value": 0
        },
        {
            "name": "04月",
            "value": 0
        },
        {
            "name": "05月",
            "value": 0
        },
        {
            "name": "06月",
            "value": 0
        },
        {
            "name": "07月",
            "value": 0
        },
        {
            "name": "08月",
            "value": 0
        },
        {
            "name": "09月",
            "value": 0
        },
        {
            "name": "10月",
            "value": 0
        },
        {
            "name": "11月",
            "value": 0
        },
        {
            "name": "12月",
            "value": 0
        }
    ],
    "code": 200
}
```

# 备注
错误码参见错误码对照表。