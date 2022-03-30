package com.noone.skins;

import com.noone.skins.controller.ShareController;
import com.noone.skins.controller.ShareControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TheRealSkinsSquareOfWuhuSpringbootApplicationTests {

	@Test
	void contextLoads() {
		ShareControllerTest test = new ShareControllerTest();
		test.changeLikes();
	}

}
