package com.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * Created by 赵梦杰 on 2018/7/24.
 */
@Controller
public class producer {

    @Resource(name = "amqpTemplate")
    private AmqpTemplate amqpTemplate;

    @RequestMapping("/producer")
    public void send(HttpServletResponse httpServletResponse){
        try{
            amqpTemplate.convertAndSend("RABBITMQ_MESSAGE_QUEUE_KEY", "hello rabbitmq发送消息");
            String data = "成功发送消息了";
            OutputStream outputStream = httpServletResponse.getOutputStream();
            httpServletResponse.setHeader("content-type", "text/html;charset=UTF-8");
            byte[] dataByteArr = data.getBytes("UTF-8");
            outputStream.write(dataByteArr);
        }catch (Exception e){
            System.out.println("发送消息失败了");
        }

    }
}
