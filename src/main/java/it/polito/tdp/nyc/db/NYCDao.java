package it.polito.tdp.nyc.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import it.polito.tdp.nyc.model.Hotspot;

public class NYCDao {
	
	public List<Hotspot> getAllHotspot(){
		String sql = "SELECT * FROM nyc_wifi_hotspot_locations";
		List<Hotspot> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Hotspot(res.getInt("OBJECTID"), res.getString("Borough"),
						res.getString("Type"), res.getString("Provider"), res.getString("Name"),
						res.getString("Location"),res.getDouble("Latitude"),res.getDouble("Longitude"),
						res.getString("Location_T"),res.getString("City"),res.getString("SSID"),
						res.getString("SourceID"),res.getInt("BoroCode"),res.getString("BoroName"),
						res.getString("NTACode"), res.getString("NTAName"), res.getInt("Postcode")));
			}
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return result;
	}
	
	public List<String> getBorghi(){
		
		String sql = "SELECT DISTINCT Borough "
				+ "FROM nyc_wifi_hotspot_locations";
		List<String> result = new LinkedList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				result.add(rs.getString("Borough"));
			}
			
			conn.close();
			return result;
		}catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
	}

	public List<String> getNTA(String borgo) {
		
		String sql = "SELECT DISTINCT NTACode "
				+ "FROM nyc_wifi_hotspot_locations "
				+ "WHERE Borough=?";
		
		List<String> result = new LinkedList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, borgo);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				result.add(rs.getString("NTACode"));
			}
			
			conn.close();
			return result;
		}catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
	}
	
	public int getPeso(String nta1, String nta2) {
		
		String sql = "SELECT count(distinct ssid) AS n "
				+ "FROM nyc_wifi_hotspot_locations "
				+ "WHERE NTACode=? OR NTACode=?";
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, nta1);
			st.setString(2, nta2);
			ResultSet rs = st.executeQuery();
			
			rs.first();
			
			conn.close();
			return rs.getInt("n");
		}catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
	}
	
}
