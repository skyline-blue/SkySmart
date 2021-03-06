package com.skyline.skysmart.auth.data.bo.impls;

import com.skyline.skysmart.auth.data.bo.interfaces.IUserBO;
import com.skyline.skysmart.auth.data.dao.User;
import com.skyline.skysmart.core.enums.ResultCode;
import com.skyline.skysmart.core.exception.Asserts;
import com.skyline.skysmart.device.data.bo.interfaces.IDeviceUserRelationBO;
import org.apache.shiro.crypto.hash.Md5Hash;

import java.util.HashMap;

/**
 * [FEATURE INFO]<br/>
 * UserBO, consists of all business info of user, such as UserDAO, user's devices, user's preset
 *
 * @author Skyline
 * @create 2022/6/12 17:43
 * @since 1.0.0
 */
public class UserBO implements IUserBO {

    private User user;
    private final HashMap<String, IDeviceUserRelationBO> devices = new HashMap<>();

    @Override
    public void mapUser(User user) {
        this.user = user;
    }

    @Override
    public User getUser() {
        assertUserNotEmpty();
        return user;
    }

    @Override
    public void mapDevice(IDeviceUserRelationBO device) {
        if (device != null) {
            this.devices.put(device.toString(), device);
        }
    }

    @Override
    public IDeviceUserRelationBO getDevice(String name) {
        return this.devices.get(name);
    }

    @Override
    public String getUid() {
        assertUserNotEmpty();
        return user.getUid();
    }

    @Override
    public String getUsername() {
        assertUserNotEmpty();
        return user.getUsername();
    }

    @Override
    public void setUsername(String username) {
        assertUserNotEmpty();
        user.setUsername(username);
    }

    @Override
    public String getPassword() {
        assertUserNotEmpty();
        return user.getPassword();
    }

    @Override
    public void setPassword(String password) {
        assertUserNotEmpty();

        Md5Hash md5Hash = new Md5Hash(password, user.getSalt(), 1024);
        String md5Password = md5Hash.toHex();
        user.setPassword(md5Password);
    }

    @Override
    public String getEmail() {
        assertUserNotEmpty();
        return user.getEmail();
    }

    @Override
    public void setEmail(String email) {
        assertUserNotEmpty();
        user.setEmail(email);
    }

    @Override
    public String getSalt() {
        assertUserNotEmpty();
        return user.getSalt();
    }

    @Override
    public void assertUserNotEmpty() {
        if (user == null) {
            Asserts.fail(ResultCode.NULL);
        }
    }
}
