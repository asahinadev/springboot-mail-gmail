package com.example.spring.controller;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import javax.mail.internet.MimeMessage;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.example.spring.form.MailSenderForm;

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

		javaMailSender.send(new MimeMessagePreparator() {

			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {

				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, StandardCharsets.UTF_8.name());

				helper.setFrom(form.from);
				helper.setTo(form.to);
				helper.setSubject("件名");
				helper.setText(templateEngine.process("mail/test", context), true);

			}
		});

		return "redirect:/";
	}

}
