package com.joy.xxfy.informationaldxn.publish.utils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 13562
 */
public class PermissionUtil {

    private static final String REGEX_PERMISSION = "";

    private static final String PERMISSION_DELIMITER = "-";

    public static boolean isPermission(String tel) {
        return true;
    }

    /**
     *
     */
    public static String transToString(List<Long> permission) {
        return String.join(PERMISSION_DELIMITER,
                permission.stream().map(n -> n.toString()).collect(Collectors.toList()));
    }
}
