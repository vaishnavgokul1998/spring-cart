package com.example.springsecurity.requestVO;

public class AddressVo {

	private Long id ;
	
	private String address1;

	private String address2;

	private String pincode;

	private String state;

	private String city;

	private String country;


	public AddressVo(Long id, String address1, String address2, String pincode, String state, String city,
			String country) {
		super();
		this.id = id;
		this.address1 = address1;
		this.address2 = address2;
		this.pincode = pincode;
		this.state = state;
		this.city = city;
		this.country = country;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	
}
