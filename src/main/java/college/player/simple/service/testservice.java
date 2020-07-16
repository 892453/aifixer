package college.player.simple.service;

import college.player.simple.config.RabbitmqConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin
public class testservice {
    public static void main(String[] args) throws Exception{
        String fullFileName="Z:/Ai_removie_front_backend/aiKanJiuYing_backend/1582955706884.jpg";
        Process process = Runtime.getRuntime().exec("python Client.py" + ' ' + fullFileName);
        System.out.println(process==null);
        System.out.println("ok!");
    }


}
