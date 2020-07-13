package com.example.spring.controller;

import java.nio.charset.*;
import java.time.*;

import javax.validation.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.mail.javamail.*;
import org.springframework.stereotype.*;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.*;
import org.thymeleaf.spring5.*;

import com.example.spring.form.*;

@Controller
@RequestMapping("/")
public class MailSenderController {

	@Autowired
	SpringTemplateEngine templateEngine;

	@Autowired
	private JavaMailSender javaMailSender;

	@GetMapping
	public String doGet(@ModelAttribute("form") MailSenderForm form) {
		return "view/index";
	}

	@PostMapping
	public String doPost(
			@Valid @ModelAttribute("form") MailSenderForm form,
			BindingResult result) {

		if (result.hasErrors()) {
			return "view/index";
		}

		Context context = new Context();

		context.setVariable("date", LocalDateTime.now().toString());

		javaMailSender.send(mimeMessage -> {
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, StandardCharsets.UTF_8.name());
			helper.setFrom(form.from);
			helper.setTo(form.to);
			helper.setSubject("件名");
			helper.setText(templateEngine.process("mail/test", context), true);
		});

		return "redirect:/";
	}

}
