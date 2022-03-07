package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController  {
    private final ItemRepository itemRepository;

//    @Autowired
//    생성자없이 @RequiredArgsConstructor를 붙이면 생성자를 넣지 않아도 된다
//    public BasicItemController(ItemRepository itemRepository) {
//        this.itemRepository = itemRepository;
//    }

    @GetMapping
    public String items(Model model){
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";

    }

    @GetMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";

    }

    @PostMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, @ModelAttribute Item item){
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";

    }

    @GetMapping("/add")
    public String addForm(){
        return "basic/addForm";

    }
//    @PostMapping("/add")
    public String addItemV1(@RequestParam String itemName,@RequestParam int price, @RequestParam Integer quantity, Model model){
        Item item =  new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);
        itemRepository.save(item);
        model.addAttribute("item", item);

        return "basic/item";

    }

//    @PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item, Model model){
        itemRepository.save(item);
//        자동추가로 생략 가능
//        model.addAttribute("item", item);

        return "basic/item";

    }

//    @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item){
//      @ModelAttribute 여기에 담기는 "item" 이거는 Item의 앞을 소문자로 바꾼 item이렇게 바뀌게 됨
        itemRepository.save(item);
//        자동추가로 생략 가능
//        model.addAttribute("item", item);

        return "basic/item";

    }

//    @PostMapping("/add")
    public String addItemV4(Item item){
//      @ModelAttribute 이걸 빼도 알아서 담김
        itemRepository.save(item);

        
//        자동추가로 생략 가능
//        model.addAttribute("item", item);

        return "basic/item";

    }

//  redirect 시키지 않으면 새로고침시 계속 저장된다.
    @PostMapping("/add")
    public String addItemV5(Item item){
        itemRepository.save(item);
        return "redirect:/basic/items/"+item.getId();
    }

//  테스트용 데이터 추가
    @PostConstruct
    public void init(){
        itemRepository.save(new Item("itemA",10000,20));
        itemRepository.save(new Item("itemB",10000,10));
    }
}
