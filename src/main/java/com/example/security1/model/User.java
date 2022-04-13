package com.example.security1.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * author : cadqe13@gmail.com
 * date : 2022-04-11
 * description :
 */

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String username;
	private String password;
	private String email;
	private String role;

	private String provider;
	private String providerId;

	@CreatedDate()
	private LocalDateTime createDate;


}
