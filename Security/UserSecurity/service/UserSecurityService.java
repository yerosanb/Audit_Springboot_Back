package com.afr.fms.Security.UserSecurity.service;

import java.util.*;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afr.fms.Admin.Entity.NotifyAdmin;
import com.afr.fms.Admin.Entity.Setting;
import com.afr.fms.Admin.Service.SettingService;
import com.afr.fms.Payload.realtime.RealTime;
import com.afr.fms.Payload.realtime.RealTimeMapper;
import com.afr.fms.Security.UserSecurity.entity.UserSecurity;
import com.afr.fms.Security.UserSecurity.mapper.UserSecurityMapper;

@Service
@Transactional
public class UserSecurityService {

	@Autowired
	private UserSecurityMapper userSecurityMapper;

	@Autowired
	private RealTimeMapper realTimeMapper;

	@Autowired
	private SettingService settingService;

	private Setting setting;

	public void increaseFailedAttempts(UserSecurity us) {
		int newFailAttempts = us.getNumber_of_attempts() + 1;
		us.setNumber_of_attempts(newFailAttempts);
		userSecurityMapper.updateAccountLockInfo(us);
		try {
			List<NotifyAdmin> notifyAdmin = userSecurityMapper.isUsernameExist(us.getUser_name());
			if (notifyAdmin != null) {
				userSecurityMapper.updateFailedAttempts(us.getUser_name());
			} else {
				userSecurityMapper.addFailedUserName(us.getUser_name());
			}
		} catch (Exception e) {
			System.out.println("increaseFailedAttempts: " + e);
		}

	}

	public void resetFailedAttempts(UserSecurity us) {
		us.setNumber_of_attempts(0);
		userSecurityMapper.updateAccountLockInfo(us);
	}

	public void lock(UserSecurity us) {
		us.setAccountNonLocked(false);
		us.setLock_time(new Date());
		userSecurityMapper.updateAccountLockInfo(us);

		try {
			List<NotifyAdmin> notifyAdmin = userSecurityMapper.isUsernameExist(us.getUser_name());
			if (notifyAdmin != null) {
				userSecurityMapper.updateLockedStatus(us.getUser_name());
			} else {
				userSecurityMapper.addLockedUserName(us.getUser_name());
			}
		} catch (Exception e) {
			System.out.println("Lock: " + e);
		}

	}


	public boolean unlockWhenTimeExpired(UserSecurity us) {
		setting = settingService.getSetting();
		long lockTimeInMillis = us.getLock_time().getTime();
		long currentTimeInMillis = System.currentTimeMillis();
		if ((lockTimeInMillis + setting.getLock_time()) < currentTimeInMillis) {
			us.setAccountNonLocked(true);
			us.setLock_time(null);
			us.setNumber_of_attempts(0);
			userSecurityMapper.updateAccountLockInfo(us);
			return true;
		}
		return false;
	}

	public void checkCredentialTimeExpired(UserSecurity us) {
		setting = settingService.getSetting();
		Date storedDate;
		if (us.getPassword_modified_date() == null) {
			storedDate = us.getPassword_created_date();
		} else {
			storedDate = us.getPassword_modified_date();
		}
		long storedTimeInMillis = storedDate.getTime() / 1000;
		long currentTimeInMillis = System.currentTimeMillis() / 1000;
		if ((currentTimeInMillis - storedTimeInMillis) > setting.getCredential_expiration()) {
			us.setCredentialsNonExpired(false);
			userSecurityMapper.updateCredentialStatus(us);
			try {
				List<NotifyAdmin> notifyAdmin = userSecurityMapper.isUsernameExist(us.getUser_name());
				if (notifyAdmin != null) {
					userSecurityMapper.updateExpiredStatus(us.getUser_name());
				} else {
					userSecurityMapper.addExpiredUserName(us.getUser_name());
				}
			} catch (Exception e) {
				System.out.println("checkCredentialTimeExpired: " + e);
			}
		}
	}

	public List<NotifyAdmin> notifyAdmin(RealTime realtime) {
		List<NotifyAdmin> notification_list = userSecurityMapper.notifyAdmin();
		if (!notification_list.isEmpty()) {
			Long notification_id = notification_list.get(0).getId();
			if (realtime.getIs_saved()) {
				if (realTimeMapper.retrieveRealTimeInfoNotifyAdmin(realtime) == null) {
					realtime.setLast_notification_admin(notification_id);
					realTimeMapper.insertLastNotificationAdmin(realtime);
				} else {
					Long last_notification_id = realTimeMapper.retrieveRealTimeInfoNotifyAdmin(realtime)
							.getLast_notification_admin();
					if (Long.compare(last_notification_id, notification_id) != 0) {
						realtime.setLast_notification_admin(last_notification_id);
						realTimeMapper.updateRealTimeInfoAdminNotification(realtime);
					}
				}
			} else {
				if (Long.compare(notification_id, realtime.getLast_notification_admin()) == 0) {
					return null;
				} else {
					realtime.setLast_notification_admin(notification_id);
					realTimeMapper.updateRealTimeInfoAdminNotification(realtime);
				}
			}
			return notification_list;
		}
		return new ArrayList<>();
		// return userSecurityMapper.notifyAdmin();
	}

	public void viewedNotificationsByAdmin(List<NotifyAdmin> adminNotification) {
		for (NotifyAdmin notification : adminNotification) {
			if (Integer.compare(notification.getLocked_status(), 1) == 0) {
				userSecurityMapper.viewLockedNotifications(notification.getId());
			}
			if ((Integer.compare(notification.getFailed_status(), 1) == 0)) {
				userSecurityMapper.viewFailedNotifications(notification.getId());
			}
			if ((Integer.compare(notification.getExpired_status(), 1) == 0)) {
				userSecurityMapper.viewExpiredNotifications(notification.getId());
			}
		}

	}

}
