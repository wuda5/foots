package com.cdqckj.gmis.bizcenter.temp.counter.validation;

import org.springframework.stereotype.Component;

/**
 * @author songyz
 */
@Component("SpecialSign")
public class SpecialSign implements ValidationHandler {
    @Override
    public boolean validate(String str) {
        boolean result = true;
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if(ch == '：' || ch == ':' || ch == ';' || ch == '；' || ch == ',' || ch == '，' || ch == '.'
                    || ch == '？'|| ch == '?' || ch == '、' || ch == '\\' || ch == '…' || ch == '。' || ch == '\n'|| ch == '\t'
                    || ch == '&' || ch == '@' || ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '=' || ch == '|' || ch == '%'
                    || ch == '`' || ch == ' ' || ch == '^' || ch == '<' || ch == '《' || ch == '》' ||ch =='>' || ch == '$' || ch == '￥'
                    || ch == '!' || ch == '！' || ch == '[' || ch == '【' || ch == '{' || ch == '(' || ch == '（' || ch == '#'
                    || ch == ']' ||ch == '】' || ch == '}'  || ch == '）' || ch == ')' || ch == '~'
                    || ch == '_' || ch == '—' || ch == '\r' || ch == '\'' || ch == '‘' || ch == '"' || ch == '“'){
                result = false;
            }
        }
        return result;
    }
}
