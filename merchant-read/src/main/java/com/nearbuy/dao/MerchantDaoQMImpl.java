package com.nearbuy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.nearbuy.merchant.model.Merchant;

public class MerchantDaoQMImpl implements MerchantDAO {

	private DataSource dataSourceQM;

	public void setDataSourceQM(DataSource dataSourceQM) {
		this.dataSourceQM = dataSourceQM;
	}

	public void save(Merchant merchant) {

		String sql = "INSERT INTO merchant " + "(id, name, vertical) VALUES (?, ?, ?)";
		Connection conn = null;

		try {
			conn = dataSourceQM.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, merchant.getId());
			ps.setString(2, merchant.getName());
			ps.setString(3, merchant.getVertical());
			ps.executeUpdate();
			ps.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);

		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
	}

	public Merchant findByID(int id) {

		String query = "select * from merchant where id = ?";
		Merchant merchant = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = dataSourceQM.getConnection();
			ps = con.prepareStatement(query);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				merchant = new Merchant();
				merchant.setId(id);
				merchant.setName(rs.getString("name"));
				merchant.setVertical((rs.getString("vertical")));
				System.out.println("Merchant Found::" + merchant);
			} else {
				System.out.println("No Merchant found with id=" + id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return merchant;
	}

	public void update(Merchant employee) {

	}

	public void deleteById(int id) {

	}

}
