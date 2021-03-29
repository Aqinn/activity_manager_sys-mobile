package com.aqinn.actmanagersys.mobile.utils;

/**
 * 工具类 - 校验字符串的合法性
 *
 * @author Aqinn
 * @date 2021/3/29 1:04 PM
 */
public class FormatUtil {

    private static final String accountRegExp = "^[a-zA-Z_][0-9a-zA-Z_]{2,19}$";
    private static final String pwdRegExp = "^[0-9a-zA-Z_]{6,20}$";

    /**
     * 校验账号格式
     *
     * @param account
     * @return
     */
    public static boolean verifyAccount(String account) {
        if (account == null || account.isEmpty() || account.contains(" "))
            return false;
        return account.matches(accountRegExp);
    }

    /**
     * 校验密码格式
     *
     * @param pwd
     * @return
     */
    public static boolean verifyPwd(String pwd) {
        if (pwd == null || pwd.isEmpty() || pwd.contains(" "))
            return false;
        return pwd.matches(pwdRegExp);
    }

}
