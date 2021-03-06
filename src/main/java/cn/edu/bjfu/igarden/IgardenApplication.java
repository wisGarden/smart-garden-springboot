package cn.edu.bjfu.igarden;

import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Controller
@SpringBootApplication
public class IgardenApplication extends WebMvcConfigurerAdapter {
    private static ConfigurableApplicationContext applicationContext;

    @RequestMapping("/")
    public String goUploadImg() {
        //跳转到 templates 目录下的 uploadimg.html
        return "uploadimg";
    }

    @RequestMapping(value = "/shutdown", method = RequestMethod.POST)
    public @ResponseBody
    String closeApp() {
        final int exitCode = 5;
        ExitCodeGenerator exitCodeGenerator = () -> exitCode;
        SpringApplication.exit(applicationContext, exitCodeGenerator);
        return "Have fun!";
    }

    public static void main(String[] args) {
        applicationContext = SpringApplication.run(IgardenApplication.class, args);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedOrigins("*")
                .allowedMethods("*");

    }
}
