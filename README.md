#目的
作为业务和技术分离的技术支撑
sgw负责内外系统通讯的协议透明，

#架构设计
##层次
###接口层
    对内部系统提供业务抽象
###渠道路由层 
    从抽象的业务接口，转为具体的渠道服务提供    
###业务适配层
    适配业务接口在不同渠道的业务实现，主要包括：参数要素，实现方式
###协议通讯层
    屏蔽通讯协议上的差异性
    
举例
```groovy
payservice.pay(62251112xxxxxx,6335xxxxxx,10)
//这里的信息可以抽象为:biz/业务,request/业务请求
``` 
```groovy
Channel channel = RouteProviderFactory.get(biz).route(request,context)
//
```   
```groovy
   String zigzagCode = channelService.getZigzagCodeof(biz,channel)
   //下面是任务触发和流程引擎
   execute(zigzagCode,request)/
       scheduler(execute(zigzagCode,request))/
       on(execute(zigzagCode,request))
```  
```groovy
exchange(exchangeCode,request)
```   
            
        
