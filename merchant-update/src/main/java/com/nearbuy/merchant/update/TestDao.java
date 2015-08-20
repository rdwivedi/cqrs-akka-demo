package com.nearbuy.merchant.update;

import java.util.Random;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nearbuy.dao.MerchantDAO;
import com.nearbuy.merchant.model.Merchant;

public class TestDao {

	public static void main(String[] args) {
		// Get the Spring Context
		@SuppressWarnings("resource")
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");

		// Get the EmployeeDAO Bean
		MerchantDAO merchantDao = (MerchantDAO) ctx.getBean("merchantDAO");

		Merchant merchant = new Merchant();
		merchant.setId(new Random().nextInt(1000));
		merchant.setName("Merchant4");
		merchant.setVertical("Movie");

		merchantDao.save(merchant);
		
		
	}
}
