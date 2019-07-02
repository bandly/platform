package com.xuhe.platform.gen.helper;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;

/**
 * @author liqiang
 * @date 2019/06/24
 * @description:
 */
public class FreemakerHelper {

    private static Configuration configuration;

    static {
        configuration = new Configuration(Configuration.VERSION_2_3_22);
        configuration.setTemplateLoader(new ClassTemplateLoader(FreemakerHelper.class, "/ftl"));
    }


    public static String render(String ftlName, Map<String, Object> data) throws IOException {
        Template template = configuration.getTemplate(ftlName);
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            template.process(data, new OutputStreamWriter(baos));
            return new String(baos.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
