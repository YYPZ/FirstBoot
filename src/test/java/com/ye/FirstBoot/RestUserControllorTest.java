package com.ye.FirstBoot;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ye.FirstBoot.controllor.RestUserControllor;
import com.ye.FirstBoot.dataAccess.jpa.repository.UserRepository;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = FirstBootApplication.class) // 指定spring-boot的启动类
public class RestUserControllorTest {
	private MockMvc mvc;

	@Autowired
	private UserRepository userRepositoryNative;
	
	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private RestUserControllor restUserControllor;

	@Before
	public void setUp() throws Exception {
		mvc = MockMvcBuilders.standaloneSetup(restUserControllor).build();
	}

	@Test
	public void testUserRepository() throws Exception {
		Assert.assertNotNull(userRepositoryNative.findById(Long.parseLong("3")).get());
	}

	@Test
	public void TestGetUserInfoRest() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/getUserInfoRest").param("id", "3").accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
	}

}
