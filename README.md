# aliyun-fc-advisor
aliyun-fc-advisor


# Advisor API
## 通用搜索
### 搜索联想词

- 接口名：/api/v1/health/search/suggest
- 请求方法： GET
- 入参： 
	- q | String | 查询文本
- 响应： 

		  {
		    "message":"查询成功",// 响应文本消息
		    "payload":{ // 响应消息主体
		       list:[
		        {"title":"苹果", // 名称
				"type":"i",     // 类型：f:food,d:disease,i:ingredient
				"id":1111},     // 主键值
				{"title":"感冒","type":"d","id":2222},
                ...
		       ]
		    }
		  }
	     

### 全域搜索
- 接口名： /api/v1/health/search
- 请求方法：GET
- 入参： 
	- q | String | 查询文本
	- type(Optional) | String{f,d} | 类型
	- id(Optinal) | Number | 主键
	- pageSize(Optional) | Number | 分页数,默认10
	- page(Optional) | Number | 页码,默认1
- 请求示例
	- /api/v1/health/search?q=感冒&type=d&page=2
- 响应：

		{
			"message":"查询成功",
			"payload":{
				"pagination":{
					"page":1,
					"pageSize":10,
					"total":102
				},
				list:[
					{
						"name":"苹果",
						"type":"i",
						"id":1111,
						"imgs":[
							"http://example.com/cdn/f/apple.png?h=60&w=60",
							"http://example.com/cdn/f/apple2.png?h=60&w=60",
							"http://example.com/cdn/f/apple3.png?h=60&w=60"
						],
						"remark":"苹果是一种营养丰富的北方水果,在我国北方广袤的平原上，生长着广阔的苹果园..."
					},
					{
						"name":"感冒"
						"type":"d",
						"id":2222,
						"imgs":[
							"http://example.com/cdn/d/gm.png?h=60&w=60",
							"http://example.com/cdn/d/gm2.png?h=60&w=60",
							"http://example.com/cdn/d/gm3.png?h=60&w=60",
						],
						"department":"内科",
						"remark":"感冒是一种常见病，感冒分风热感冒和风寒感冒..."
					},
					...
				]
			}
		}

### 健康建议
- 接口名：/api/v1/health/advisor
- 请求方法：GET
- 入参：
	- id | Number | 主键
	- type | String | 类型
	- filters | String | 查询过滤(more,less,forbidden)
	- pageSize | Number | 页大小
	- page | Number | 页码
- 请求示例： /api/v1/health/advisor?id=1111&type=f&filters=more&page=1
- 响应

		{
			"message":"查询成功",
			"payload":{
				"advises"{
					"pagination":{
						"page":1,
						"pageSize":10,
						"total":102
					},
					"list":[
						{
							"name":"感冒",
							"id":2222,
							"type":"d"
							"imgs":[
								"http://example.com/cdn/d/img.png"
							]
						},
						{
							"name":"高血压",
							"id":2333,
							"type":"d"
							"imgs":[
								"http://example.com/cdn/d/img.png"
							]
						}
						...
					]
					
				}
			}
		}

### 食品详情
- 接口名：/api/v1/health/ingredient
- 请求方法：GET
- 入参：
	- id | Number | 主键
- 请求示例： /api/v1/health/ingredient?id=1111
- 响应
	
		{
			"message":"查询成功",
			"payload":{
				"ingredient":{
					"name":"苹果",
					"enName":"apple",
					"kind":"水果",
					"origin":"山西"    // 原产地
					"ediblePart":65.4,
					"energy":12.2,
					"water":10.7,
					"protein":4.5,
					"fat":0.3,
					"dietaryFiber":5.5,
					"carbohydrate":3.2,
					"retinolEquivalent":0.1,
					"vb1":0.2,
					"vb2":0.1,
					"vpp":0.3,
					"vitaminA":0.4,
					"vitaminB1":2.25,
					"vitaminB2":0.21,
					"vitaminB6":6.1,
					"vitaminB12":9.2,
					"vitaminC":12.8,
					"vitaminD":4.1,
					"vitaminE":2.5,
					"vitaminK":2.2,
					"vitaminB1":1.2,
					"natrium":0.01,
					"calcium":0.01,
					"ferrum":0.01,
					"vc":0.3,
					"cholestenone":0.9,
					"phosphorus":3.3,
					"potassium":4.2,
					"magnesium":1.8,
					"zinc":7.2
				},
				imgs:[
					"http://example.com/i/apple1.png?h=50&w=50",
					"http://example.com/i/apple2.png?h=50&w=50",
					"http://example.com/i/apple3.png?h=50&w=50"
				]
				
			}
		}

### 疾病详情
- 接口名：/api/v1/health/disease
- 请求方法：GET
- 入参：
	- id | Number | 主键
- 请求示例： /api/v1/health/disease?id=2222
- 响应


		{
			"message":"查询成功",
			"payload":{
				"disease":{
					"name":"感冒",
					"enName":"cough",
					"department":"内科",
					"summary":"感冒简介",
					"treatment":"治疗方案说明"
				},
				imgs:[
					"http://example.com/i/cough1.png?h=50&w=50",
					"http://example.com/i/cough2.png?h=50&w=50",
					"http://example.com/i/cough3.png?h=50&w=50"
				]
			}
		}
