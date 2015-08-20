package com.nearbuy.dao;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nearbuy.merchant.model.Merchant;

public class Test {

	public static void main(String[] args) {

		@SuppressWarnings("resource")
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring-qm.xml");

		MerchantDAO merchantDaoCM = (MerchantDAO) ctx.getBean("merchantDAO-CM");

		Merchant merchant = merchantDaoCM.findByID(856);

		MerchantDAO merchantDaoQM = (MerchantDAO) ctx.getBean("merchantDAO-QM");

		if (merchant != null) {
			merchantDaoQM.save(merchant);

		}

	}
}
