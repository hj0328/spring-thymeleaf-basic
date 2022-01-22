package hello.itemservice.message;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class MessageSourceTest {

    @Autowired
    MessageSource ms;

    /*
    springBoot 기본 MessageSource를 이용한 messages.properties 조회
     */
    @Test
    void helloMessage() {
        String result = ms.getMessage("hello", null, null);
        assertThat(result).isEqualTo("안녕");
    }

    /*
    메시지가 없는 경우 예외 발생 검사
    "no_code" 메시지가 없기 때문에 예외 발생 ( 예외도 일종의 객체 )
     */
    @Test
    void notFoundMessageCode() {
        assertThatThrownBy(()-> ms.getMessage("no_code", null, null))
                .isInstanceOf(NoSuchMessageException.class);
    }

    /*
    메시지 없는 경우 defaultMessage로 대체했는지 검사
    "no_code" 메시지가 없지만 디폴트 메시지가 있기 때문에 예외 발생하지 않음
     */
    @Test
    void notFoundMessageCodeDefaultMessage() {
        String result = ms.getMessage("no_code", null, "기본 메시지", null);
        assertThat(result).isEqualTo("기본 메시지");
    }

    /*
    메시지 조회 시 매개변수 전달 및 추가
    message.properties에 {0}로 매개변수가 추가됨
     */
    @Test
    void argumentMessage() {
        String result = ms.getMessage("hello.name", new Object[]{"Spring"},null);
        assertThat(result).isEqualTo("안녕 Spring");
    }

    /*
    국제화
    국제화 정보를 매개변수로 넘겨서 나라별 언어를 조회
    locale이 null이라면 messages.properties 사용
     */
    @Test
    void defaultLang() {
        assertThat(ms.getMessage("hello", null, null)).isEqualTo("안녕");
        assertThat(ms.getMessage("hello", null, Locale.KOREA)).isEqualTo("안녕");
    }

    /*
    국제화
    영문 국제화 파일 선택
     */
    @Test
    void enLang() {
        assertThat(ms.getMessage("hello", null, Locale.ENGLISH))
                .isEqualTo("hello");
    }
}
