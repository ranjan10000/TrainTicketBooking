package com.app.ticket.serviceimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.ticket.db.DbConnection;
import com.app.ticket.model.HistoryBean;
import com.app.ticket.service.BookingService;

@Service
public class BookingServiceImpl implements BookingService {

	@Autowired
	DbConnection dbconnection;

	@Override
	public List<HistoryBean> getAllBookingsByCustomerId(String customerEmailId) throws Exception {
		List<HistoryBean> transactions = null;
		String query = "SELECT * FROM RESERVATION_HISTORY WHERE MAILID=?";
		try {
			Connection con = dbconnection.getConnection();
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, customerEmailId);
			ResultSet rs = ps.executeQuery();
			transactions = new ArrayList<HistoryBean>();
			while (rs.next()) {
				HistoryBean transaction = new HistoryBean();
				transaction.setTransId(rs.getString("transid"));
				transaction.setFrom_stn(rs.getString("from_stn"));
				transaction.setTo_stn(rs.getString("to_stn"));
				transaction.setDate(rs.getString("date"));
				transaction.setMailId(rs.getString("mailid"));
				transaction.setSeats(rs.getInt("seats"));
				transaction.setAmount(rs.getInt("amount"));
				transaction.setTr_no(rs.getString("tr_no"));
				transactions.add(transaction);
			}

			ps.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new Exception(e.getMessage());
		}
		return transactions;
	}

	@Override
	public HistoryBean createHistory(HistoryBean details) throws Exception {
		HistoryBean history = null;
		String query = "INSERT INTO RESERVATION_HISTORY VALUES(?,?,?,?,?,?,?,?)";
		try {
			Connection con = dbconnection.getConnection();
			PreparedStatement ps = con.prepareStatement(query);
			String transactionId = UUID.randomUUID().toString();
			ps.setString(1, transactionId);
			ps.setString(2, details.getMailId());
			ps.setString(3, details.getTr_no());
			ps.setString(4, details.getDate());
			ps.setString(5, details.getFrom_stn());
			ps.setString(6, details.getTo_stn());
			ps.setInt(7, details.getSeats());
			ps.setInt(8, details.getAmount());
			int response = ps.executeUpdate();
			if (response > 0) {
				history = (HistoryBean) details;
				history.setTransId(transactionId);
			} else {
				throw new Exception();
			}
			ps.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new Exception(e.getMessage());
		}
		return history;
	}

}
