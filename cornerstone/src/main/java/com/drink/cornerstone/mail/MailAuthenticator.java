/**
 * 公司: 英孚兰信息技术有限公司
 * 项目名称：阿凡提商城
 * 版本：1.0
 * 开发人员：薛贵平 
 * 开发时间：2010-10-26
 * 功能描述：验证smtp
 */
package com.drink.cornerstone.mail;

import javax.mail.PasswordAuthentication;

public class MailAuthenticator extends javax.mail.Authenticator {
	private String mailUser;
	private String mailPwd;

	public String getMailUser() {
		return mailUser;
	}

	public void setMailUser(String mailUser) {
		this.mailUser = mailUser;
	}

	public String getMailPwd() {
		return mailPwd;
	}

	public void setMailPwd(String mailPwd) {
		this.mailPwd = mailPwd;
	}

	public MailAuthenticator(String mailUser, String mailPwd) {
		this.mailUser = mailUser;
		this.mailPwd = mailPwd;
	}

	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(mailUser, mailPwd);
	}
}
