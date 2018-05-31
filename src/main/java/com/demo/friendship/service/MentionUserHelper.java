package com.demo.friendship.service;

import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class MentionUserHelper {

    static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static Set<String> extractUsers(String text) {
        if (StringUtils.isBlank(text)) {
            return Collections.emptySet();
        }

        Set<String> users = new HashSet<>();
        String[] words = text.split("\\s+");
        for (String word: words) {
            if (VALID_EMAIL_ADDRESS_REGEX.matcher(word).matches()) {
                users.add(word);
            }
        }
        return users;
    }
}
