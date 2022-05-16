package it.polito.tdp.borders.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.borders.model.Border;
import it.polito.tdp.borders.model.Country;

public class BordersDAO {

	public Map<Integer, Country> loadAllCountries() {

		String sql = "SELECT ccode, StateAbb, StateNme FROM country ORDER BY StateAbb";
		Map<Integer, Country> result = new HashMap<Integer, Country>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Country c = new Country(rs.getInt("ccode"), rs.getString("StateNme"), rs.getString("StateAbb"));
				result.put(c.getCountryId(), c);
			}
			
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Border> getCountryPairs(int anno) {

		String sql = "Select  "
				+ "From `contiguity` "
				+ "Where `year` <= ? AND `conttype` = 1";
		
		List<Border> result = new ArrayList<>();
		Connection conn = ConnectDB.getConnection();
		
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Border border = new Border(rs.getInt("state1no"), rs.getInt("state2no"));
				result.add(border);
			}
			
			conn.close();
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public Map<Integer, Country> getCountryWithBorders(Map<Integer, Country> idMap, int anno){
		String sqlString = "SELECT c1.* "
				+ "FROM contiguity c1, contiguity c2 "
				+ "WHERE c1.`year` = c2.`year` AND c2.`year` <= ? "
				+ "AND c1.`conttype` = c2.`conttype` AND c2.`conttype` = 1 "
				+ "AND c1.`dyad` = c2.`dyad` AND c1.`state1no` < c2.`state1no`";
		Map<Integer, Country> result = new HashMap<>();
		Connection conn = ConnectDB.getConnection();
		try {
			PreparedStatement st = conn.prepareStatement(sqlString);
			st.setInt(1, anno);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Country c1 = idMap.get(rs.getInt("state1no"));
				Country c2 = idMap.get(rs.getInt("state2no"));
				
				result.put(c1.getCountryId(), c1);
				result.put(c2.getCountryId(), c2);
			}
			
			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Border> getCoppieAdiacenti(int anno) {
		String sql = "select state1no, state2no " +
				"from contiguity " +
				"where year<=? " +
				"and conttype=1 " +
				"and state1no < state2no";

		List<Border> result = new ArrayList<>();

		try {
			Connection conn = ConnectDB.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);

			st.setInt(1, anno);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(new Border(rs.getInt("state1no"), rs.getInt("state2no")));
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}
	
	public Map<Country, Integer> getGradoVertice(Map<Integer, Country> idMap, int anno){
		String sqlString = "SELECT c.`CCode`, COUNT(*) as Grado "
				+ "FROM country c, contiguity b "
				+ "WHERE c.`CCode` = b.`state1no` AND b.`year` <= ? "
				+ "GROUP BY c.`CCode`";
		Map<Country, Integer> resultMap = new HashMap<>();
		
		Connection conn = ConnectDB.getConnection();
		
		try {
			PreparedStatement st = conn.prepareStatement(sqlString);
			st.setInt(1, anno);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Country c = idMap.get(rs.getInt("CCode"));
				resultMap.put(c, rs.getInt("Grado"));
			}
			
			conn.close();
			return resultMap;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
