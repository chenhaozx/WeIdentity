package com.webank.weid.constant;

/**
 * @author tonychen 2020年2月11日
 *
 */
public enum CredentialType {

    /**
     * the field is existed.
     */
    ORIGINAL(0),

    /**
     * the field is disclosed.
     */
    ZKP(1),

    /**
     * the field is not disclosed.
     */
    CONSUMABLE(2);

    /**
     * disclosure status.
     */
    private Integer type;

    CredentialType(Integer type) {
        this.type = type;
    }

    /**
     * get field disclosure status.
     *
     * @return disclosure status of the field.
     */
    public Integer getType() {
        return type;
    }
}
