package college.player.simple.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin
public class testController{
    @RequestMapping(value = "test", method = RequestMethod.GET)
    @CrossOrigin
    public String test() {
        String str = "hello,world";
        System.out.println(str);
        return "test finish";
    }
}

