package college.player.simple.controller;


import college.player.simple.config.RabbitmqConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class registController {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    RabbitTemplate rabbitTemplate;

    @RequestMapping(value = "/user/regist",method = RequestMethod.POST)
    public String login(@RequestParam("user") String username,
                        @RequestParam("pwd") String password,
                        @RequestParam("mail") String mail,
                        Map<String,Object> map

    ) {
        System.out.println("user:" + username + "  password:" + password +"mail:"+mail+ "请求注册");
        //将邮箱加入到消息队列
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.convertAndSend(RabbitmqConfig.EXCHANGE,RabbitmqConfig.ROUTEKEY,mail);
        System.out.println("注册邮箱mail已加入到消息队列！");
        //将用户名密码插入到数据库
        String sql="insert into user(username,password) VALUES ('"+username+"','"+password+"')";
        jdbcTemplate.update(sql);
        System.out.println("注册成功啦");

        Message message = null;
        while(message==null) {
            try {
                message = rabbitTemplate.receive("queue_reg",1);
            }catch (Exception e){
            }
        }
        String content = new String(message.getBody()).replace("\"", "");
        System.out.println("message内容:" + content);
       return content;
    }}


