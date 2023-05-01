package com.music.shiro;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.music.common.util.JwtToken;
import com.music.entity.Admin;
import com.music.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * JwtRealm 只负责校验 JwtToken
 */
@Slf4j
public class JwtRealm extends AuthorizingRealm {

	@Autowired
	private AdminService adminService;
 
	/**
	 * 限定这个 Realm 只处理我们自定义的 JwtToken
	 */
	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof JwtToken;
	}
 
	/**
	 * 此处的 SimpleAuthenticationInfo 可返回任意值，密码校验时不会用到它
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
			throws AuthenticationException {
		JwtToken jwtToken = (JwtToken) authcToken;
		if (jwtToken.getPrincipal() == null) {
			throw new AccountException("JWT token参数异常！");
		}
		// 从 JwtToken 中获取当前用户
		String username = jwtToken.getPrincipal().toString();
		// 查询数据库获取用户信息，此处使用 Map 来模拟数据库
//		UserEntity user = ShiroRealm.userMap.get(username);
		LambdaQueryWrapper<Admin> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.eq(Admin::getName, username);
		Admin user = adminService.getOne(lambdaQueryWrapper);



 
		// 用户不存在
		if (user == null) {
			throw new UnknownAccountException("用户名或密码错误");
		}
 
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, username, getName());
		return info;
	}
 
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// 获取当前用户
		Admin admin = (Admin) SecurityUtils.getSubject().getPrincipal();


		String permission = admin.getPermission();
		Set<String> roles = new HashSet<>();
		Set<String> perms = new HashSet<>();
		//获取权限信息
		roles.add(admin.getRoles());
		perms.addAll(Arrays.asList(permission.split(",")));

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

		info.setRoles(roles);
		info.setStringPermissions(perms);
		return info;
	}
 
}