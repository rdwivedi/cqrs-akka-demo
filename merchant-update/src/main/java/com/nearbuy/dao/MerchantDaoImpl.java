package com.nearbuy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.nearbuy.merchant.model.Merchant;

public class MerchantDaoImpl implements MerchantDAO {
	private DataSource dataSourceCM;

	public void setDataSourceCM(DataSource dataSourceCM) {
		this.dataSourceCM = dataSourceCM;
	}

	public Merchant findByID(int id) {

		String query = "select name, role from Merchant where id = ?";
		Merchant merchant = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = dataSourceCM.getConnection();
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

	public void save(Merchant merchant) {

		String sql = "INSERT INTO merchant " + "(id, Name, vertical) VALUES (?, ?, ?)";
		Connection conn = null;

		try {
			conn = dataSourceCM.getConnection();
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

	public void update(Merchant employee) {

	}

	public void deleteById(int id) {

	}

}
