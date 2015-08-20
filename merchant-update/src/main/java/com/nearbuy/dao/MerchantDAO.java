package com.nearbuy.dao;

import com.nearbuy.merchant.model.Merchant;

public interface MerchantDAO {

	public void save(Merchant employee);

	public void update(Merchant employee);

	public void deleteById(int id);
	
	public Merchant findByID(int id);
}
