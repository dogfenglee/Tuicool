package com.lowwor.tuicool.utils;

import com.orhanobut.logger.Logger;

/**
 * Created by lowworker on 2015/9/20.
 */
public class HtmlParser {
    public static String parse(String text) {
        if (text == null) return null;
        Logger.i(text);
        text = parseSourceCode(text,ORIGINAL_PATTERN_BEGIN,ORIGINAL_PATTERN_END);
       text = parseSourceCode(text,ORIGINAL_PATTERN_BEGIN_2,ORIGINAL_PATTERN_END);
        Logger.i(text);
        return text;
    }

    public static String parseSourceCode(String text, String startPattern, String endPattern) {
        if (text.contains(PARSED_PATTERN_END))

        {
            Logger.i("contains");

            return text;
        }
        StringBuilder result = new StringBuilder();
        int begin;
        int end;
        int beginIndexToProcess = 0;

        while (text.contains(startPattern)) {
            begin = text.indexOf(startPattern);
            end = text.indexOf(endPattern);
            Logger.i("startPattern");
            String code = parseCodeSegment(text, begin, end);

            result.append(text.substring(beginIndexToProcess, begin));
            result.append(PARSED_PATTERN_BEGIN);
            result.append(code);
            result.append(PARSED_PATTERN_END);

            //replace in the original text to find the next appearance
            text = text.replaceFirst(startPattern, PARSED_PATTERN_BEGIN);
            text = text.replaceFirst(endPattern, PARSED_PATTERN_END);

            Logger.i(text);
            //update the string index to process
            beginIndexToProcess = text.lastIndexOf(PARSED_PATTERN_END) + PARSED_PATTERN_END.length();
        }

        //add the rest of the string
        result.append(text.substring(beginIndexToProcess, text.length()));

        return result.toString();
    }

    private static String parseCodeSegment(String text, int begin, int end) {
        String code = text.substring(begin + ORIGINAL_PATTERN_BEGIN.length(), end);
        code = code.replace(" ", "&nbsp;");
        code = code.replace("\n", "<br/>");
        return code;
    }

    private static final String ORIGINAL_PATTERN_BEGIN = "<pre class=\"prettyprint\">";
    private static final String ORIGINAL_PATTERN_BEGIN_2 = "<pre>";
    private static final String ORIGINAL_PATTERN_END = "</pre>";

    private static final String PARSED_PATTERN_BEGIN = "<p><code>";
    private static final String PARSED_PATTERN_END = "</p></code>";
}