package com.example.spring.form;

import java.io.*;

import javax.validation.constraints.*;

import lombok.*;

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
