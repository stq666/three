package com.drink.cornerstone.security;

public class SimpleServicePermissionResource extends SimplePermission {
	
	public static final String SERVICE_PREFIX = "service:";
	
	public SimpleServicePermissionResource(String permissionString, PermissionResources permissionResources) {
		super(permissionString.replace(SERVICE_PREFIX, ""),permissionResources);
	}

	public boolean isResource(){
		return Boolean.TRUE;
	}
}
