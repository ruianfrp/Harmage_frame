package com.harmonycloud.bean.account;

import java.util.Arrays;

public class Admin {
    private String account;

    private byte[] password;

    public Admin(String account) {
        this.account = account;
    }

    public Admin(String account, byte[] password) {
        this.account = account;
        this.password = password;
    }

    public Admin() {
        super();
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "account='" + account + '\'' +
                ", password=" + Arrays.toString(password) +
                '}';
    }
}