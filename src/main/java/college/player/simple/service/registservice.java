package college.player.simple.service;

import college.player.simple.config.RabbitmqConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;

@RestController
@CrossOrigin
public class registservice {

    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    String from;
    @RabbitListener(queues = RabbitmqConfig.QUEUE)
    public void sendmail(Message mess) throws Exception{
         String to = new String(mess.getBody()).replace("\"", "");//注意这里的同字符串不能有前后双引号
         System.out.println("接收方邮箱："+to);
        Calendar cal=Calendar.getInstance();

        SimpleMailMessage message = new SimpleMailMessage();
         message.setFrom(from);
         message.setTo(to);
         message.setSubject("标题：欢迎加入焕影科技！");
         message.setText("在"+cal.get(Calendar.YEAR)+"年"+cal.get(Calendar.MONTH)+"月"+cal.get(Calendar.DATE)+"月"+cal.get(Calendar.HOUR_OF_DAY)+"日"+cal.get(Calendar.MINUTE)+"分"+cal.get(Calendar.SECOND)+"秒"+
                 "，恭喜您已经成功注册了焕影科技，请牢记您的用户名和密码。（本邮件为机器自动发送，不需回复）");
         try {
             //javaMailSender.send(message);
             System.out.println("简单邮件已经发送。");

             String result="success";
             rabbitTemplate.convertAndSend("exchange_reg","routekey_reg",result);
         } catch (Exception e) {
             System.err.println("发送简单邮件时发生异常！"+e);
         }


    }

}
