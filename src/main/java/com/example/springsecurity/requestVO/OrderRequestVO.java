package com.example.springsecurity.requestVO;

import com.example.springsecurity.models.BillDetail;

public class OrderRequestVO {

	 private Long bookId;
	 
	 private BillDetail billDetail;

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public BillDetail getBillDetail() {
		return billDetail;
	}

	public void setBillDetail(BillDetail billDetail) {
		this.billDetail = billDetail;
	}
	 
	 
}
