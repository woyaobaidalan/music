package com.music.shiro;

import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.music.entity.Admin;
import com.music.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 同时开启身份验证和权限验证，需要继承 AuthorizingRealm
 * 并实现其  doGetAuthenticationInfo()和 doGetAuthorizationInfo 两个方法
 */
@SuppressWarnings("serial")
@Slf4j
public class ShiroRealm extends AuthorizingRealm {

	@Autowired
	private AdminService adminService;

	/**
	 * 限定这个 Realm 只处理 UsernamePasswordToken
	 */
	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof UsernamePasswordToken;
	}

	/**
	 * 查询数据库，将获取到的用户安全数据封装返回
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// 从 AuthenticationToken 中获取当前用户
		String username = (String) token.getPrincipal();
		// 查询数据库获取用户信息，此处使用 Map 来模拟数据库
		LambdaQueryWrapper<Admin> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.eq(Admin::getName, username);
		Admin user = adminService.getOne(lambdaQueryWrapper);

		// 用户不存在
		if (user == null) {
			throw new UnknownAccountException("用户名或密码错误");
		}


		// 使用用户名作为盐值
		ByteSource credentialsSalt = ByteSource.Util.bytes(username);

		/**
		 * 将获取到的用户数据封装成 AuthenticationInfo 对象返回，此处封装为 SimpleAuthenticationInfo 对象。
		 *  参数1. 认证的实体信息，可以是从数据库中获取到的用户实体类对象或者用户名
		 *  参数2. 查询获取到的登录密码
		 *  参数3. 盐值
		 *  参数4. 当前 Realm 对象的名称，直接调用父类的 getName() 方法即可
		 */
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), credentialsSalt,
				getName());
		return info;
	}

	/**
	 * 查询数据库，将获取到的用户的角色及权限信息返回
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// 获取当前用户
		Admin admin = (Admin) SecurityUtils.getSubject().getPrincipal();

		String permission = admin.getPermission();
		Set<String> roles = new HashSet<>();
		roles.add(admin.getRoles());

		String[] split = permission.split(",");
		Set<String> perms = new HashSet<>(Arrays.asList(split));
		//获取权限信息
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

		info.setRoles(roles);
		info.setStringPermissions(perms);
		return info;
	}

}