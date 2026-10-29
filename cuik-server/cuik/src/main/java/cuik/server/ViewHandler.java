package cuik.server;

import java.io.StringWriter;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

public abstract class ViewHandler {
    private static Configuration config;

    private static Configuration configure() {
        if (config == null) {
            config = new Configuration(Configuration.VERSION_2_3_35);
            config.setDefaultEncoding("UTF-8");
            config.setLogTemplateExceptions(false);
            config.setWrapUncheckedExceptions(true);
            config.setFallbackOnNullLoopVariable(false);

            config.setInterpolationSyntax(Configuration.SQUARE_BRACKET_INTERPOLATION_SYNTAX);
            config.setTagSyntax(Configuration.SQUARE_BRACKET_TAG_SYNTAX);

            config.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);

            config.setClassForTemplateLoading(ViewHandler.class, "/views");
        }

        return config;
    }

    public static String render(String name, Object obj) throws Exception {
        var template = configure().getTemplate(name);

        var stringWriter = new StringWriter();
        template.process(obj, stringWriter);

        return stringWriter.toString();
    }
}
