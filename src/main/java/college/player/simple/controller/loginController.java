package college.player.simple.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class loginController {
    @Autowired
    JdbcTemplate jdbcTemplate;

    // @PostMapping(value = "/user/login")
    @RequestMapping(value = "/user/login",method = RequestMethod.POST)
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Map<String,Object> map

    ){
        System.out.println("user:"+username+"  password:"+password+"�����¼");

        String sql="select * from user where username='"+username+"' and password='"+password+"'";
        List<Map<String,Object>> list_map= jdbcTemplate.queryForList(sql);
        System.out.println(list_map);

        if(!list_map.isEmpty()){
            //��¼�ɹ�
            System.out.println("��¼�ɹ�����");
            return "success";
        }else{
            //��¼ʧ��
            System.out.println("��¼ʧ�ܣ���");
            // map.put("msg","�û��������������");
            return "fail";
        }

    }
}
