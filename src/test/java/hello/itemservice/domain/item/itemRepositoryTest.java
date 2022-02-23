package hello.itemservice.domain.item;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class itemRepositoryTest {
    ItemRepository itemRepository = new ItemRepository();

    // 테스트가 하나 끝날때마다 실행된다. 꼭 해줘야 다음 테스트시 문제 없음
    @AfterEach
    void afterEach(){
        itemRepository.crearStore();
    }

    @Test
    void save(){
        // given
        // 아이템 하나 만들기
        Item item = new Item("itemA", 10000, 10);

        // when
        Item savedItem = itemRepository.save(item);
        // then
        Item findItem = itemRepository.findById(item.getId());
        // assertios 위에서 option + enter하면
//        Assertions.assertThat(findItem).isEqualTo(savedItem); 가 아래로 바뀜
        assertThat(findItem).isEqualTo(savedItem);
    }

    @Test
    void findAll(){
        // given
        Item item1 = new Item("item1", 10000, 10);
        Item item2 = new Item("item2", 20000, 20);

        itemRepository.save(item1);
        itemRepository.save(item2);
        // when
        List<Item> result = itemRepository.findAll();

        // then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(item1, item2);
    }
    @Test
    void updateItem(){
        // given
        Item item = new Item("item", 1000, 20);
        Item savedItem = itemRepository.save(item);
        Long itemId = savedItem.getId();

        // when
        Item updateParam = new Item("item2", 10000,33);
        itemRepository.update(itemId, updateParam);

        // then
        Item findItem = itemRepository.findById(itemId);
        assertThat(findItem.getItemName()).isEqualTo(updateParam.getItemName());
        assertThat(findItem.getPrice()).isEqualTo(updateParam.getPrice());
        assertThat(findItem.getQuantity()).isEqualTo(updateParam.getQuantity());
    }
}
