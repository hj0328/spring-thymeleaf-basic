package hello.itemservice.web.form;

import hello.itemservice.domain.item.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/form/items")
@RequiredArgsConstructor
@Slf4j
public class FormItemController {

    private final ItemRepository itemRepository;

    /*
     * @ModelAttribute 공통 작업으로 빼내기
     * 메소드 컨트롤러에서 중복으로 model.addAttribute 작업을 모아준다.
     * 만약 해당 소속 값들이 동적으로 변하지 않는다면 static으로 따로 처리해도 하나의 방법이다.
     */
    @ModelAttribute("regions")
    public Map<String, String> regions() {
        return ItemRegionsSingleton.getInstance();
    }

    /*
     * Enum 타입은 정적인 값에 대해서 정의하여 사용
     * 동적인 값에 대해서는 (db 값)은 객체로 다룸
     */
    @ModelAttribute("itemTypes")
    public ItemType[] itemTypes() {
        return ItemTypeSingleton.getInstance();
    }

    @ModelAttribute("deliveryCodes")
    public List<DeliveryCode> deliveryCodes() {
        return DeliveryCodeSingleton.getInstance();
    }

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "form/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "form/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        Item item = new Item();
        item.setItemType(ItemType.ETC);
        model.addAttribute("item", item);
        return "form/addForm";
    }

//     addForm2메서드는 addForm메서드와 거의 동일하다.
//     ModelAttribute이 Model 객체를 생성해서 넘겨주는 점은 동일
//     하지만 차이점은 쿼리 파라미터로 Item 객체 값이 넘어오게 되면 채워서 response 함
//    @GetMapping("/add")
//    public String addForm2(@ModelAttribute Item item) {
//        log.info("add form2");
//        return "form/addForm";
//    }

    @PostMapping("/add")
    public String addItem(@ModelAttribute Item item, RedirectAttributes redirectAttributes) {
        log.info("item.open={}", item.getOpen());
        log.info("item.regions={}", item.getRegions());
        log.info("item.itemType={}", item.getItemType());
        log.info("item.deliveryCode={}", item.getDeliveryCode());

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/form/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "form/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/form/items/{itemId}";
    }

}

