package com.webank.weid.constant;

/**
 * @author tonychen 2020年2月11日
 *
 */
public enum CredentialType {

    /**
     * the field is existed.
     */
    ORIGINAL(0, "original"),

    /**
     * the field is disclosed.
     */
    ZKP(1, "zkp"),

    /**
     * the field is not disclosed.
     */
    CONSUMABLE(2, "consumable");

    /**
     * credential type code .
     */
    private Integer code;
    
    /**
     * credential type name.
     */
    private String name;

    /**
     * constructor.
     * @param code the credential type code.
     * @param name the credential type name.
     */
    CredentialType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * get field disclosure status.
     *
     * @return disclosure status of the field.
     */
    public Integer getCode() {
        return code;
    }
    
    /**
     * get field disclosure status.
     *
     * @return disclosure status of the field.
     */
    public String getName() {
        return name;
    }
}
