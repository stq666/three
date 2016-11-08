package com.drink.security;


import com.drink.model.User;
import com.drink.service.UserService;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.realm.service.AuthenticationService;
import org.apache.shiro.realm.service.SaltedPassword;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;




@Component
public class SimpleAuthenticationService implements AuthenticationService {

    @Resource(name="userService")
    UserService userService;

	@Override
	public SaltedPassword findSaltedPasswordByUserName(String username) throws Exception {
		SaltedPassword saltedPassword = null;
		try {
			User user = userService.findUserByName(username);
			if (user == null) {
                throw new Exception("user[" + username + "] doesnt exists.");
            }else{
                saltedPassword = new SaltedPassword(user.getId(), user.getPassword(),user.getLoginname());
            }
		} catch (Exception ex) {
			throw new AccountException("findSaltedPasswordByUserName error:" + ex.getMessage());
		}
		return saltedPassword;
	}

	@Override
	public Set<String> findRoleNamesForUserName(String username) throws Exception {
		List<String> roleNames = null;
		/*try {
            User user = userService.findUserByName(username);
			if (user == null)
				throw new Exception("user[" + username + "] doesnt exists.");
            RoleService subRoleService=(RoleService)beanService.getInstance("subRoleService"+(user.getServer()==null?"":user.getServer()));
            roleNames = subRoleService.findRoleNamesByUserId(user.getDb(), user.getOrgid(),user.getIfmanager(),user.getId());
		} catch (Exception ex) {
			throw new AuthorizationException("findRoleNamesForUserName error:" + ex.getMessage());
		}*/
		return roleNames == null ? null : new HashSet<String>(roleNames);
	}

	@Override
	public Set<String> findPermissions(String username, Set<String> roleNames) throws Exception {
        List<String> perms = null;
		/*try {
			User user = userService.findUserByName(username);
			if (user == null)
				throw new Exception("user[" + username + "] doesnt exists.");
            PermissionService subPermissionService=(PermissionService)beanService.getInstance("subPermissionService"+(user.getServer()==null?"":user.getServer()));
			perms = subPermissionService.findResourceByUserId(user.getDb(),user.getOrgid(),user.getIfmanager(),user.getId());
		} catch (Exception ex) {
			throw new AuthorizationException("findPermissions error:" + ex.getMessage());
		}*/
		return perms == null ? null : new HashSet<String>(perms);
	}

}
