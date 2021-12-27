package hello.thymeleaf.basic;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/property")
public class propertyController {

    @Value("${custom.myname}")
    private String myName;

    @Value("${custom.myage: 1}")
    private String myAge;

    @Value("${server.port}")
    private int serverPort;

    @Value("${db.port}")
    private int dbPort;

    @Value("#{spelTestVo.testValue}")
    private String spelValue;

    @GetMapping("/application")
    public String property() {
        StringBuilder sb = new StringBuilder();
        sb.append("my name: ").append(myName)
                .append(", my default age: ").append(myAge)
                .append(", server port: ").append(serverPort)
                .append(", db port: ").append(dbPort)
                .append("\n")
                .append("spel value: ")
                .append(spelValue);

        return sb.toString();
    }

    @Value("${spelTestVo}")
    public void methodTarget(int age, String name){
        System.out.println("age " + age);
        System.out.println("name " + name);
    }
}
