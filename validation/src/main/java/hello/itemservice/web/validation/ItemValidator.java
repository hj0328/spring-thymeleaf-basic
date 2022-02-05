package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Item target(객체)에 대한 검증 클래스
 *  - controller 의 검증 작업을 따로 분리
 *  - Validator 를 구현하여 Spring 지원을 일부 받는다.
 */
@Component
public class ItemValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        // Item 및 Item 하위 클래스에 대해서만 validate 메서드 지원
        return Item.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        Item item = (Item) target;

        /* 검증 로직
         - StringUtils.hasText는 null과 빈 문자열 모두 체크해준다.
         - errors제거 -> BindingResult 적용
        */
//        if(!StringUtils.hasText(item.getItemName())) {
//            bindingResult.rejectValue("itemName", "required");
//        }

        // 위와 같이 if hasText 검사 대신 ValidationUtils 에거 제공하는 메서드를 통해 간단하게 한 줄로 검증할 수 있다.
        // 내부적으로 들어가면 결과적으로는 동일한 메서드를 호출하고 있음
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "itemName", "required");

        if(item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            errors.rejectValue("price", "range", new Object[]{1000, 1000000}, null);
        }
        if(item.getQuantity() == null || item.getQuantity() > 9999) {
            errors.rejectValue("quantity", "max", new Object[]{9999}, null);
        }

        // 특정 필드가 아닌 복합 룰 검증
        // ex 로직 계산 중 값의 범위가 max를 넘거나 min보다 작아지는 경우가 있다.
        if(item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if(resultPrice < 10000) {
                errors.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }
    }
}
