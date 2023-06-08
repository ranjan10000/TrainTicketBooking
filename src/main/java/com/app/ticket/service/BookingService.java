package com.app.ticket.service;

import java.util.List;

import com.app.ticket.model.HistoryBean;

public interface BookingService {

	public List<HistoryBean> getAllBookingsByCustomerId(String customerEmailId) throws Exception;

	public HistoryBean createHistory(HistoryBean bookingDetails) throws Exception;

}
