package com.rabbitmq;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;

/**
 * Created by 赵梦杰 on 2018/7/24.
 */
public class Consumer implements ChannelAwareMessageListener {

    public void onMessage(Message message, Channel channel) throws Exception {
        String message1=new String(message.getBody(),"UTF-8");
        if(message1.equals("hello rabbitmq发送消息")){
            basicACK(message,channel);
        }else{
            basicNACK(message,channel);
        }
        System.out.println(message1+"********消费消息");
    }

    //正常消费掉后通知mq服务器移除此条mq
    private void basicACK(Message message,Channel channel){
        try{
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        }catch(Exception e){
            System.out.println("进行消息的接受确认");
        }
    }
    //处理异常，mq重回队列
    private void basicNACK(Message message,Channel channel){
        try{
            channel.basicRecover(true);
            //channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,true);
        }catch(Exception e){
            System.out.println("消息接受异常，消息回滚");
        }
    }
}
