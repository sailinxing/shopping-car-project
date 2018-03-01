package com.lixinxin.domain;

public class User {
private String username;
private String password;
private String name;
private String email;
private String birthday;
private String sex;
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getBirthday() {
	return birthday;
}
public void setBirthday(String birthday) {
	this.birthday = birthday;
}
public String getSex() {
	return sex;
}
public void setSex(String sex) {
	this.sex = sex;
}
public User() {
	super();
	// TODO Auto-generated constructor stub
}
public User(String username, String password, String name, String email, String birthday, String sex) {
	super();
	this.username = username;
	this.password = password;
	this.name = name;
	this.email = email;
	this.birthday = birthday;
	this.sex = sex;
}
@Override
public String toString() {
	return "User [username=" + username + ", password=" + password + ", name=" + name + ", email=" + email
			+ ", birthday=" + birthday + ", sex=" + sex + "]";
}

}
