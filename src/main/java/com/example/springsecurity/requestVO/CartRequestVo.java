package com.example.springsecurity.requestVO;

public class CartRequestVo {
	
	private Long bookId;
	
	private Integer quantity;
	
	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	

}
