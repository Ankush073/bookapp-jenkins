package com.coforge.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
//@Table(name="booktable")
@NamedQuery(name="findByTitle",query="select b from Book b where title=:title")
@NamedQuery(name="getAllBooksByAuthor",query="select b from Book b where author=:author")
public class Book {
	
	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long bookId;
//	@Column(name="firstTitle")
	
	@Size(min=3,message="title can't be less than 3 chars")
	@NotBlank
	private String title;
	
//	@Column(name="authorNmae")
	
	@NotBlank
	private String author;
	
   @Pattern(regexp = "^[6789][0-9]{9}$", message = "Mobile number must be 10 digits and start with 6, 7, 8, or 9")
	private String authorMobile;
	
	@Email(message="Please enter valid email")
	private String email;
	
	@DecimalMin(value ="100",inclusive=true,message="boook min cost is 100rs/-")
	private double price;
}
