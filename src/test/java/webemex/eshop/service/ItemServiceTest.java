package webemex.eshop.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import webemex.eshop.model.Item;
import webemex.eshop.repository.ItemRepository;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DisplayName("Item service test")
@ActiveProfiles("local")
class ItemServiceTest {

    @Autowired
    private ItemService itemService;

    @MockBean
    private ItemRepository itemRepository;

    @Test
    void givenOneItemInRepository_whenFindAllItems_thenReturnsOneItem() {
        // Prepare a test item with a specific product name
        String productName = "TestItem";
        Item item = new Item();
        item.setProductName(productName);

        // Mock the behavior of the repository to return a predefined list
        Mockito.when(itemRepository.findAll()).thenReturn(Collections.singletonList(item));

        // Call the method to be tested and verify the results
        List<Item> items = itemService.findAllItems();
        assertEquals(1, items.size(), "Expected one item");
        assertEquals(productName, items.get(0).getProductName(), "Expected product name to match");
    }
}
