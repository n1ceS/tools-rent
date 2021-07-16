package pl.marczuk.toolsrent.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import pl.marczuk.toolsrent.dto.ItemDto;
import pl.marczuk.toolsrent.model.Item;
import pl.marczuk.toolsrent.model.PriceList;
import pl.marczuk.toolsrent.repository.ItemRepository;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ToolsRentItemControllerApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private ItemRepository itemRepository;

	private static ItemDto itemDto;

	@BeforeEach
	public void setupItem(){
		itemDto = new ItemDto("TEST", "TEST description", BigDecimal.valueOf(100));
	}


	@WithMockUser(authorities = "ADMIN")
	@Test
	void shouldAddItem() throws Exception {
		// given
			itemRepository.deleteByName(itemDto.getName());
		// when
		MvcResult mvcResult = mockMvc.perform(post("/items")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(itemDto)))
				.andDo(print())
				.andExpect(status().isCreated())
				.andReturn();
		// then
			ItemDto itemDtoResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ItemDto.class);
			assertThat(itemDtoResult).isNotNull();
			assertThat(itemDtoResult.getName()).isEqualTo("TEST");
	}

	@WithMockUser(authorities = "USER")
	@Test
	void shouldNotAddItemAndReturn403Forbidden() throws Exception {
		// given
		itemRepository.deleteByName("TEST");
		// when
		mockMvc.perform(post("/items")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(itemDto)))
				.andDo(print())
		// then
				.andExpect(status().isForbidden());

	}

	@WithMockUser(authorities = "ADMIN")
	@Test
	void shouldNotAddItemAndReturn400BadRequest() throws Exception {
		// given
		itemRepository.deleteByName("TEST");
		itemDto.setPrice(null);
		itemDto.setName(null);
		// when
		mockMvc.perform(post("/items")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(itemDto)))
				.andDo(print())
				// then
				.andExpect(status().isBadRequest());

	}

	@WithMockUser(authorities = "ADMIN")
	@Test
	void shouldEditItem() throws Exception {
		// given
		Item item = new Item();
		item.setDescription("Test Description");
		item.setName("TEST");
		item.setPriceList(new PriceList(item, BigDecimal.valueOf(11)));
		itemRepository.save(item);

		itemDto.setDescription("New Description");
		itemDto.setPrice(BigDecimal.valueOf(10));
		// when
		MvcResult mvcResult = mockMvc.perform(put("/items")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(itemDto)))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
		// then
		ItemDto itemDtoResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ItemDto.class);
		assertThat(itemDtoResult).isNotNull();
		assertThat(itemDtoResult.getPrice()).isEqualTo(BigDecimal.valueOf(10));
		assertThat(itemDtoResult.getDescription()).isEqualTo("New Description");
	}

	@WithMockUser(authorities = "USER")
	@Test
	void shouldNotEditItemAndReturn403Forbidden() throws Exception {
		// given
		itemDto.setDescription("New Description");
		itemDto.setPrice(BigDecimal.valueOf(10));
		// when
		mockMvc.perform(put("/items")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(itemDto)))
				.andDo(print())
		//then
				.andExpect(status().isForbidden());
	}

	@WithMockUser(authorities = "ADMIN")
	@Test
	void shouldNotEditItemAndReturn400BadRequest() throws Exception {
		// given
		itemDto.setPrice(null);
		itemDto.setName(null);
		// when
		mockMvc.perform(put("/items")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(itemDto)))
				.andDo(print())
				// then
				.andExpect(status().isBadRequest());

	}



}
