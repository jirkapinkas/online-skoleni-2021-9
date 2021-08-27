package com.example.eshopweb;

import com.example.eshopweb.dto.ItemDto;
import com.example.eshopweb.service.ItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EshopwebApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private ItemService itemService;

    @Test
    void contextLoads() {
        List<ItemDto> items = itemService.findAll("id", "asc");
        assertThat(items)
                .isNotNull()
                .isNotEmpty();

        ItemDto itemDto = testRestTemplate.getForObject("/item/1", ItemDto.class);
        // https://assertj.github.io/doc/
        assertThat(itemDto)
                .isNotNull();
        assertThat(itemDto.getId())
                .isEqualTo(1);

        // https://www.testcontainers.org/
    }

}
