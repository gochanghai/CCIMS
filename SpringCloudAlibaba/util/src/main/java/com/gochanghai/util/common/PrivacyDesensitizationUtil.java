package com.gochanghai.util.common;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * 用来处理敏感信息，进行脱敏
 * @author tyg
 * @date   2019年7月24日下午4:01:22
 */
public class PrivacyDesensitizationUtil {

    // 手机号码正则表达式
    private static final String PHONE_REG = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
    // email正则表达式
    private static final String EMAIL_REG = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    // 银行卡正则表达式
    private static final String BANK_CARD_NUMBER = "^\\d{19}$";
    // 身份证号正则表达式
    private static final String ID_CARD = "(^[1-8][0-7]{2}\\d{3}([12]\\d{3})(0[1-9]|1[012])(0[1-9]|[12]\\d|3[01])\\d{3}([0-9Xx])$)";

    /**
     * 银行账户：显示前六后四，范例：622848******4568
     * @param bankAcct
     * @return
     */
    public static String encryptBankAcct(String bankAcct) {
        if (bankAcct == null) {
            return "";
        }
        return replaceBetween(bankAcct, 6, bankAcct.length() - 4, null);
    }

    /**
     * 银行账户：显示前六后四，范例：622848******4568
     * @param creditCardNo
     * @return
     */
    public static String encryptCreditCardNo(String creditCardNo) {
        if (creditCardNo == null) {
            return "";
        }
        return replaceBetween(creditCardNo, 6, creditCardNo.length() - 4, null);
    }

    /**
     * 身份证号码：显示前六后四，范例：110601********2015
     * @param idCard
     * @return
     */
    public static String encryptIdCard(String idCard) {
        if (idCard == null) {
            return "";
        }
        return replaceBetween(idCard, 6, idCard.length() - 4, null);
    }

    /**
     * 客户email：显示前二后四，范例：abxx@xxx.com
     * @param email
     * @return
     */
    public static String encryptEmail(String email) {
        //判断是否为邮箱地址
        if (email == null || !Pattern.matches(PrivacyDesensitizationUtil.EMAIL_REG, email)) {
            return "";
        }

        StringBuilder sb = new StringBuilder(email);
        //邮箱账号名只显示前两位
        int at_position = sb.indexOf("@");
        if (at_position > 2) {
            sb.replace(2, at_position, StringUtils.repeat("x", at_position - 2));
        }
        //邮箱域名隐藏
        int period_position = sb.lastIndexOf(".");
        sb.replace(at_position + 1, period_position, StringUtils.repeat("x", period_position - at_position - 1));
        return sb.toString();
    }

    /**
     * 手机：显示前三后四，范例：189****3684
     * @param phoneNo
     * @return
     */
    public static String encryptPhoneNo(String phoneNo) {
        if (phoneNo == null) {
            return "";
        }
        return replaceBetween(phoneNo, 3, phoneNo.length() - 4, null);
    }

    /**
     * 护照前2后3位脱敏，护照一般为8或9位
     * @param passportId
     * @return
     */
    public static String encryptPassportId(String passportId) {
        if (StringUtils.isEmpty(passportId) || (passportId.length() < 8)) {
            return passportId;
        }
        return passportId.substring(0, 2) + new String(new char[passportId.length() - 5])
                .replace("\0", "*") + passportId.substring(passportId.length() - 3);
    }

    /**
     * @param object 待处理对象
     * @param fieldNames 需要处理的属性
     * @return
     * 将对象转换为字符串，并对敏感信息进行处理
     */
    public static String encryptSensitiveInfo(Object object, String[] fieldNames) {
        return encryptSensitiveInfo(object, fieldNames, null);
    }

    /**
     * @param object 待处理对象
     * @param fieldNames 需要处理的属性
     * @param excludes 不需要显示的属性
     * @return
     * 将对象转换为字符串，并对敏感信息进行处理
     */
    public static String encryptSensitiveInfo(Object object, String[] fieldNames, String[] excludes) {
        if (fieldNames == null) {
            fieldNames = new String[0];
        }
        //合并数组
        Set<String> set = new HashSet<String>(Arrays.asList(fieldNames));
        if (excludes != null) {
            for (int i = 0; i < excludes.length; i++) {
                set.add(excludes[i]);
            }
        }
        //将对象转换为字符串
        String str = ReflectionToStringBuilder.toStringExclude(object, set.toArray(new String[0]));

        //处理敏感信息
        StringBuilder sb = new StringBuilder(str);
        sb.deleteCharAt(sb.length() - 1);
        for (int i = 0; i < fieldNames.length; i++) {
            String fieldName = fieldNames[i];
            try {
                Field f = object.getClass().getDeclaredField(fieldName);
                f.setAccessible(true);
                String value = encryptSensitiveInfo(String.valueOf(f.get(object)));
                if (i != 0 || sb.charAt(sb.length() - 1) != '[') {
                    sb.append(",");
                }
                sb.append(fieldName).append("=").append(value);
            } catch (Exception e) {
            }
        }
        sb.append("]");
        str = sb.toString();
        return str;
    }

    /**
     * 对敏感信息进行处理。使用正则表达式判断使用哪种规则
     * @param sourceStr 需要处理的敏感信息
     * @return
     * @return String
     * @author tyg
     * @date   2018年5月5日下午3:59:28
     */
    public static String encryptSensitiveInfo(String sourceStr) {
        if (sourceStr == null) {
            return "";
        }
        if (Pattern.matches(PrivacyDesensitizationUtil.PHONE_REG, sourceStr)) {
            return encryptPhoneNo(sourceStr);
        } else if (Pattern.matches(PrivacyDesensitizationUtil.EMAIL_REG, sourceStr)) {
            return encryptEmail(sourceStr);
        } else if (Pattern.matches(PrivacyDesensitizationUtil.BANK_CARD_NUMBER, sourceStr)) {
            return encryptBankAcct(sourceStr);
        } else if (Pattern.matches(PrivacyDesensitizationUtil.ID_CARD, sourceStr)) {
            return encryptIdCard(sourceStr);
        } else {
            return sourceStr;
        }
    }

    /**
     * 将字符串开始位置到结束位置之间的字符用指定字符替换
     * @param sourceStr 待处理字符串
     * @param begin	开始位置
     * @param end	结束位置
     * @param replacement 替换字符
     * @return
     */
    private static String replaceBetween(String sourceStr, int begin, int end, String replacement) {
        if (sourceStr == null) {
            return "";
        }
        if (replacement == null) {
            replacement = "*";
        }
        int replaceLength = end - begin;
        if (StringUtils.isNotBlank(sourceStr) && replaceLength > 0) {
            StringBuilder sb = new StringBuilder(sourceStr);
            sb.replace(begin, end, StringUtils.repeat(replacement, replaceLength));
            return sb.toString();
        } else {
            return sourceStr;
        }
    }

    public static void main(String[] args) {
        System.out.println(PrivacyDesensitizationUtil.encryptBankAcct("6228482462893085616"));//622848*********5616
        System.out.println(PrivacyDesensitizationUtil.encryptPhoneNo("13228116626"));//132****6626
        System.out.println(PrivacyDesensitizationUtil.encryptCreditCardNo("622918008692044"));//622918*****2044
        System.out.println(PrivacyDesensitizationUtil.encryptIdCard("510658199107356847"));//510658********6847
        System.out.println(PrivacyDesensitizationUtil.encryptEmail("1182786142@qq.com"));//11xxxxxxxx@xx.com
        System.out.println(PrivacyDesensitizationUtil.encryptSensitiveInfo("13228116626"));//

    }

}
