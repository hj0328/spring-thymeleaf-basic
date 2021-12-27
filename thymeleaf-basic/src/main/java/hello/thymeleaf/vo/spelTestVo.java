package hello.thymeleaf.vo;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class spelTestVo {
    public String testValue;
    public spelTestVo () {
        testValue = "spelValue vo";
        age = 1;
        name = "hello";
    }

    private int age = 1;
    private String name = "hello";
}
