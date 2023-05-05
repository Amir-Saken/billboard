package com.example.demo;

import com.example.demo.contorller.MainController;
import com.example.demo.models.User;
import com.example.demo.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.example.demo.Repo.UserRepo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DemoApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private MainController mainController;
	@MockBean
	private UserService userService;
	@Test
	public void startTest() throws Exception {
		assertThat(mainController).isNotNull();
	}

	@Test
	public void loginTest() throws Exception {
		this.mockMvc.perform(formLogin("/signin").user("qwe").password("1233"))
				.andDo(print())
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/"));
	}

	@Test
	public void greetingTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("posts"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("title"))
				.andExpect(MockMvcResultMatchers.view().name("index"));
	}

	@Autowired
	private UserRepo userRepo;
	@Test
	public void registrationTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/signup"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("signup"));
	}

	@Test
	public void addUserTest() throws Exception {
		User user = new User();
		user.setUsername("testuse");
		user.setPassword("passwor");


		mockMvc.perform(MockMvcRequestBuilders.post("/signup")
						.flashAttr("user", user))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.redirectedUrl("/signin"));

		// Проверить, что пользователь был сохранен в репозитории
		User savedUser = userRepo.findByUsername("testuse");
		assertThat(savedUser).isNotNull();
		assertThat(savedUser.getUsername()).isEqualTo("testuse");
	}
//	@Test
//	public void userListTest() throws Exception {
//		mockMvc.perform(MockMvcRequestBuilders.get("/user"))
//				.andExpect(MockMvcResultMatchers.status().isOk())
//				.andExpect(MockMvcResultMatchers.model().attributeExists("users"))
//				.andExpect(MockMvcResultMatchers.model().attributeExists("loggedIn"))
//				.andExpect(MockMvcResultMatchers.view().name("userList"));
//	}
//
//	@Autowired
//	private UserRepo userRepository;
//	@Test
//	public void userEditTest() throws Exception {
//		User user = new User();
//		user.setUsername("testuser");
//		user.setPassword("password");
//		userRepository.save(user);
//
//		mockMvc.perform(MockMvcRequestBuilders.get("/user/edit/{id}", user.getId()))
//				.andExpect(MockMvcResultMatchers.status().isOk())
//				.andExpect(MockMvcResultMatchers.model().attributeExists("user"))
//				.andExpect(MockMvcResultMatchers.view().name("userEdit"));
//	}
//
//	@Test
//	public void userRemoveTest() throws Exception {
//		User user = new User();
//		user.setUsername("testuser");
//		user.setPassword("password");
//		userRepository.save(user);
//
//		mockMvc.perform(MockMvcRequestBuilders.get("/user/remove/{id}", user.getId()))
//				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
//				.andExpect(MockMvcResultMatchers.redirectedUrl("/user"));
//
//		// Проверить, что пользователь был удален из репозитория
//		User removedUser = userRepository.findById(user.getId()).orElse(null);
//		assertThat(removedUser).isNull();
//	}

//	@Test
//	public void userSaveTest() throws Exception {
//		User user = new User();
//		user.setUsername("testuser");
//		user.setPassword("password");
//		userRepository.save(user);
//
//		mockMvc.perform(MockMvcRequestBuilders.post("/user/save/{id}", user.getId())
//						.flashAttr("user", user))
//				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
//				.andExpect(MockMvcResultMatchers.redirectedUrl("/user"));
//
//		// Проверить, что данные пользователя были сохранены в репозитории
//		User savedUser = userRepository.findById(user.getId()).orElse(null);
//		assertThat(savedUser).isNotNull();
//		assertThat(savedUser.getUsername()).isEqualTo("testuser");
//	}


}
