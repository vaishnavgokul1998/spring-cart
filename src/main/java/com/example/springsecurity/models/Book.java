package com.example.springsecurity.models;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "books")
public class Book {
	
	    @Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "id", insertable = false, updatable = false)
		private Long id;

	    @Column(name = "isbn")
		private Long isbn;

	    @Column(name="title")
		private String title;
	    
	    @Column(name="subtitle")
		private String subtitle;
	    
	    @Column(name="author")
		private String author;
	    
	    @Column(name="publisher")
		private String publisher;
	    
	    @Column(name="pages")
		private Integer pages;
	    
	    @Column(name="description")
		private String description;
	    
	    @Column(name="website")
		private String website;
	    
	    @Column(name="image_url")
		private String image_url;
	    
	    @Column(name="price")
	    BigDecimal price;
	    
	    @Column(name="discount")
	    Integer discount;
	    
	    
		@Temporal(TemporalType.TIMESTAMP)
		@Column(name="published", updatable = false)
		private Date published;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Long getIsbn() {
			return isbn;
		}

		public void setIsbn(Long isbn) {
			this.isbn = isbn;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getSubtitle() {
			return subtitle;
		}

		public void setSubtitle(String subtitle) {
			this.subtitle = subtitle;
		}

		public String getAuthor() {
			return author;
		}

		public void setAuthor(String author) {
			this.author = author;
		}

		public String getPublisher() {
			return publisher;
		}

		public void setPublisher(String publisher) {
			this.publisher = publisher;
		}

		public Integer getPages() {
			return pages;
		}

		public void setPages(Integer pages) {
			this.pages = pages;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getWebsite() {
			return website;
		}

		public void setWebsite(String website) {
			this.website = website;
		}

		public Date getPublished() {
			return published;
		}

		public void setPublished(Date published) {
			this.published = published;
		}

		public String getImage_url() {
			return image_url;
		}

		public void setImage_url(String image_url) {
			this.image_url = image_url;
		}

		public BigDecimal getPrice() {
			return price;
		}

		public void setPrice(BigDecimal price) {
			this.price = price;
		}

		public Integer getDiscount() {
			return discount;
		}

		public void setDiscount(Integer discount) {
			this.discount = discount;
		}
		

}
