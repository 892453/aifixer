package college.player.simple.service;


import college.player.simple.config.RabbitmqConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import javax.sound.midi.Soundbank;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

@RestController
@CrossOrigin
public class uploadservice {
    @Autowired
    RabbitTemplate rabbitTemplate;



    //正确版监听队列
    @RabbitListener(queues = RabbitmqConfig.QUEUE_uploadA)
    public void listenupload(Message message)throws Exception{
        String originFileName= new String(message.getBody()).replace("\"", "");
        System.out.println("监听到A队列："+originFileName);
        String[] fileNames = originFileName.split("\\.");
        String userDir = System.getProperty("user.dir");
        String fullFileName = userDir + '/' + System.currentTimeMillis() + '.' + fileNames[1];

        //处理图片起
        //处理图片完成

        rabbitTemplate.convertAndSend(RabbitmqConfig.EXCHANGE_uploadB,RabbitmqConfig.ROUTEKEY_uploadB,fullFileName);
        System.out.println("send B ok!");
    }


}
