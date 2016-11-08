package org.apache.shiro.realm.service;

import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

public class SimpleAuthenticationInfo extends org.apache.shiro.authc.SimpleAuthenticationInfo {

	private static final long serialVersionUID = 3241329753744395912L;

	private Long userId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public SimpleAuthenticationInfo() {
	}

	public SimpleAuthenticationInfo(Object principal, Object credentials, String realmName) {
		super(principal, credentials, realmName);
	}

	public SimpleAuthenticationInfo(Object principal, Object hashedCredentials, ByteSource credentialsSalt, String realmName) {
		super(principal, hashedCredentials, credentialsSalt, realmName);
	}

	public SimpleAuthenticationInfo(PrincipalCollection principals, Object credentials) {
		super(principals, credentials);
	}

	public SimpleAuthenticationInfo(PrincipalCollection principals, Object hashedCredentials, ByteSource credentialsSalt) {
		super(principals, hashedCredentials, credentialsSalt);
	}

}
