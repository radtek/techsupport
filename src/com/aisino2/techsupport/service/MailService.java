package com.aisino2.techsupport.service;

import com.aisino2.techsupport.domain.Mail;

public interface MailService {
	
	/**单独发送邮件
	 * @param subject 主题
	 * @param to 收件人（支持多个收件人，若需多个收件人，使用","隔开）
	 * @param cc 抄送 (支持多个抄送,若需多个抄送，使用“,”隔开)
	 * @param pgp 是否使用公密钥加密
	 * @param signature 是否使用数字签名
	 * @param text 正文 
	 * @param attach 附件
	 * @param html 是否为HTML邮件
	 * @return 是否发送成功
	 */
	public Boolean send(Mail mail,String subject,String to,String cc,Boolean pgp,Boolean signature,String text,String attach,Boolean html) throws Exception;
	
	
	/**
	 * 发送邮件（短版）,默认不使用 PGP加密和数字签名
	 * @param subject 主题
	 * @param to 收件人（支持多个收件人，若需多个收件人，使用","隔开）
	 * @param cc 抄送 (支持多个抄送,若需多个抄送，使用“,”隔开)
	 * @param text 正文
	 * @param html 是否为HTML邮件
	 * @return 是否发送成功
	 */
	public boolean send(Mail mail,String subject, String to, String cc, String text,boolean html) throws Exception;
	
	/**
	 * 发送邮件带有发送附件的短版
	 * @param mail
	 * @param subject 主题
	 * @param to 收件人
	 * @param cc 抄送
	 * @param text 正文
	 * @param attach 附件
	 * @param html 是否发送html邮件
	 * @return 是否发送成功
	 */
	public boolean send(Mail mail,String subject, String to, String cc, String text,String attach,boolean html) throws Exception;
	
	/**
	 * 连接邮件服务器
	 * @param mail
	 * @return 是否连接成功
	 */
	public Boolean connect(Mail mail,Boolean sendAuth,Boolean debug,Boolean ssl) throws Exception;
	
	/**
	 * 判断是否连接
	 * @return
	 */
	public boolean isConnected();
	
	/**
	 * 关闭连接
	 */
	public void close();

	/**
	 * 线程方式发送
	 * @param mail
	 * @param subject
	 * @param to
	 * @param cc
	 * @param text
	 * @param attach
	 * @param html
	 * @return
	 * @throws Exception
	 */
	public void sendByDaemon(Mail mail,String subject, String to, String cc, String text,String attach,boolean html) throws Exception;
	
	/**
	 * 发送邮件（短版）,默认不使用 PGP加密和数字签名(线程方式)
	 * @param mail
	 * @param subject
	 * @param to
	 * @param cc
	 * @param text
	 * @param html
	 * @return
	 * @throws Exception
	 */
	public void sendByDaemon(Mail mail,String subject, String to, String cc, String text,boolean html) throws Exception;
	
	/**
	 * 单独发送邮件(线程方式)
	 * @param mail
	 * @param subject
	 * @param to
	 * @param cc
	 * @param pgp
	 * @param signature
	 * @param text
	 * @param attach
	 * @param html
	 * @return
	 * @throws Exception
	 */
	public void sendByDaemon(Mail mail,String subject,String to,String cc,Boolean pgp,Boolean signature,String text,String attach,Boolean html) throws Exception;
}
