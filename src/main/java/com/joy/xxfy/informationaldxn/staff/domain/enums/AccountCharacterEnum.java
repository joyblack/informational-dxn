package com.joy.xxfy.informationaldxn.staff.domain.enums;

public enum AccountCharacterEnum {
    /**
     * 农业户口
     */
    COUNTRYSIDE_CHARACTER("农业户口"),
    /**
     * 城市户口
     */
    CITY_CHARACTER("城市户口");

    private String accountCharacter;

    AccountCharacterEnum() {
    }

    AccountCharacterEnum(String accountCharacter) {
        this.accountCharacter = accountCharacter;
    }

    public String getAccountCharacter() {
        return accountCharacter;
    }

    public void setAccountCharacter(String accountCharacter) {
        this.accountCharacter = accountCharacter;
    }
}
