
package com.sxp.patMag.entity;

//import lombok.Builder;
//import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * @description:
 * @author: suixingpay<five@suixingpay.com>
 * @date: 2019/11/18 19:11
 * @version: V1.0
 */
//@Data
//@Builder
public class User {

    /**
     * id
     */
    @Size(max = 32,min = 16,message="id超过范围")
    private String userId;
    /**
     * 用户名
     */
    @Size(max = 16,min = 0,message="用户名超过范围")
    @NotNull(message = "用户名不能为空")
    private String userName;
    /**
     * 密码
     */
    @Size(max = 16,min = 0,message="密码超过范围")
    @NotNull(message = "密码不能为空")
    private String userPassword;
    /**
     *角色
     */
    @Size(max = 1,min = 0,message="角色超过范围")
    private String userRole;
    public User(){

    }
    public User(String userId, String userName, String userPassword, String userRole) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userRole='" + userRole + '\'' +
                '}';
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getUserRole() {
        return userRole;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(userId, user.userId) &&
                Objects.equals(userName, user.userName) &&
                Objects.equals(userPassword, user.userPassword) &&
                Objects.equals(userRole, user.userRole);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userName, userPassword, userRole);
    }
}
