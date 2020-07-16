package college.player.simple.controller;


import college.player.simple.config.RabbitmqConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import javax.sound.midi.Soundbank;
import java.io.*;



@RestController
@CrossOrigin
public class MyController {

    @Autowired
    RabbitTemplate rabbitTemplate;


    @RequestMapping(value = "upload", method = RequestMethod.POST)
    @CrossOrigin
    public String upload(@RequestParam("file") MultipartFile file) throws Exception{
        String fullFileName = null;
        try {
            String originFileName = file.getOriginalFilename();
            System.out.println("mycontroller:"+originFileName);
            if (StringUtils.isEmpty(originFileName)) {
                return null;
            }

            //消息队列起
            rabbitTemplate.convertAndSend(RabbitmqConfig.EXCHANGE_uploadA,RabbitmqConfig.ROUTEKEY_uploadA,originFileName);
            System.out.println("send success!");
//拿到了队列处理的部分
//            String[] fileNames = originFileName.split("\\.");
//            String userDir = System.getProperty("user.dir");
//            fullFileName = userDir + '/' + System.currentTimeMillis() + '.' + fileNames[1];
            Message message = null;
            while(message==null) {
                try {
                    message = rabbitTemplate.receive(RabbitmqConfig.QUEUE_uploadB);
                }catch (Exception e){
                }
            }
            fullFileName=new String(message.getBody()).replace("\"", "");
            System.out.println("B 收到的full filename  ：  "+fullFileName);
            //消息队列完成

            File tempFile = new File(fullFileName);
            if (!tempFile.exists()) {
                if (!tempFile.createNewFile()) {
                    return null;
                }
            }
            System.out.println(originFileName);
            FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
            fileOutputStream.write(file.getBytes());
            fileOutputStream.close();
            System.out.println(fullFileName);

            Process process = Runtime.getRuntime().exec("python Client.py" + ' ' + fullFileName);
            System.out.println("tup处理完成");
            InputStream inputStream = process.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(inputStreamReader);
            fullFileName = br.readLine();
            System.out.println("??:"+fullFileName);
            br.close();
            inputStreamReader.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("return ok!");
        return fullFileName;

    }




    @RequestMapping(value = "download", method = RequestMethod.GET)
    @CrossOrigin
    public void ping(String fullFileName, HttpServletResponse response) {
        System.out.println("下载的fullfilename："+fullFileName);

        try {
            if (fullFileName == null || fullFileName.isEmpty()) {
                return;
            }

            byte[] buff = new byte[1024];
            File file1 = new File(fullFileName);

            FileInputStream fileInputStream = new FileInputStream(file1);
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            response.addHeader("Content-Disposition", "attachment;fileName=" + file1.getName());

            int len;
            while ((len = fileInputStream.read(buff)) != -1) {
                response.getOutputStream().write(buff, 0, len);
            }
            fileInputStream.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    @RequestMapping("/test")
    @CrossOrigin
    public String test(){
        System.out.println("test_finish1");
        return "cross_success1";
    }

}
