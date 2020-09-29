package com.ws.support.base.entity;

/**
 * 人员性别枚举
 *
 * @author Johnny.xu
 *         date 2017/5/18
 */
public enum PersonalGenderMenu {

    Man(0, "男", true),
    Women(1, "女", false);

    public int type;
    public boolean isMan;
    public String gender;

    PersonalGenderMenu(int type, String gender, boolean isMan) {
        this.type = type;
        this.gender = gender;
        this.isMan = isMan;
    }

    public static String getGender(int type) {
        for (PersonalGenderMenu menu : values()) {
            if (menu.type == type) {
                return menu.gender;
            }
        }
        return Man.gender;
    }

    public static boolean isMan(int type) {
        for (PersonalGenderMenu menu : values()) {
            if (menu.type == type) {
                return menu.isMan;
            }
        }
        return Man.isMan;
    }

    public static int getType(String gender) {
        for (PersonalGenderMenu menu : values()) {
            if (menu.gender.equals(gender)) {
                return menu.type;
            }
        }
        return Man.type;
    }
}
