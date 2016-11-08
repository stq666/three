package com.drink.cornerstone.security;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.SimpleHash;

public class PasswordService {
	
	private HashedCredentialsMatcher HashedCredentialsMatcher;

	public HashedCredentialsMatcher getHashedCredentialsMatcher() {
		return HashedCredentialsMatcher;
	}

	public void setHashedCredentialsMatcher(HashedCredentialsMatcher hashedCredentialsMatcher) {
		HashedCredentialsMatcher = hashedCredentialsMatcher;
	}

	public String generate(String username,String password){
		return new SimpleHash(HashedCredentialsMatcher.getHashAlgorithmName(),password,username,HashedCredentialsMatcher.getHashIterations()).toHex();
	}
	
	public static void main(String[] args){
		System.out.println(new SimpleHash("SHA-256","admin", "111111",1024).toHex());
		System.out.println(new PasswordService().generate("zhaosi@126.com", "123456")); 
	}

}
