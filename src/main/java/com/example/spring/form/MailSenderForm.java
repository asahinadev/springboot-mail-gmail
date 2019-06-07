package com.example.spring.form;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@SuppressWarnings("serial")
public class MailSenderForm implements Serializable {

	@NotEmpty
	@Email
	public String from;

	@NotEmpty
	@Email
	public String to;
}
