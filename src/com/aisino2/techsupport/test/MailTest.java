package com.aisino2.techsupport.test;

import java.io.InputStream;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.aisino2.sysadmin.service.IDict_itemService;
import com.aisino2.techsupport.common.CommonUtil;
import com.aisino2.techsupport.domain.Mail;
import com.aisino2.techsupport.service.MailService;

public class MailTest {
	private MailService mailService;
	private ApplicationContext context;
	private IDict_itemService dict_itemService;
	private CommonUtil util;
	
	@Before
	public void init(){
		context = new ClassPathXmlApplicationContext(new String[] {
				"/config/spring/applicationContext.xml",
				"/config/spring/techsupport-common.xml",
				"/config/spring/sysadmin-*.xml" });
		mailService = (MailService) context.getBean("mailServiceImpl");
		util = (CommonUtil)context.getBean("CommonUtil");
		dict_itemService = (IDict_itemService) context.getBean(
				"dict_itemService");
	}
	@Test
	public void testMailSendByDaemon() throws Exception {
		Mail mail = new Mail();
		// 读取邮件内容信息
		InputStream in = this.getClass().getClassLoader()
				.getResourceAsStream("mailContent.properties");
		Properties properties = new Properties();
		properties.load(in);
		// 读取邮件配置信息
		Properties mailConfig = new Properties();
		InputStream config = this.getClass().getClassLoader()
				.getResourceAsStream("mailConfig.properties");
		mailConfig.load(config);
		mail.setUser(properties.getProperty("company_send_name"));
		mail.setEmail(properties.getProperty("company_Address"));
		mail.setPassword(properties.getProperty("compnay_password"));
		// mail.setEmail(email);
		mail.setProtocol(mailConfig.getProperty("protocol"));
		mail.setSmtphost(mailConfig.getProperty("smtphost"));
		mail.setHost(mailConfig.getProperty("host"));
		mailService.connect(mail, true, true, false);
		String mailContent = properties.getProperty("automessage.tracking_content");
		mailContent = util.getMsg(mailContent, new String[]{"ffmmx","2012-12-21"});
		mailService.sendByDaemon(mail, properties.getProperty("automessage.tracking_subject"), "firefoxmmx@gmail.com,huxin@aisino.com", null, mailContent, false);
		Thread.sleep(8000);
	}
	
}
