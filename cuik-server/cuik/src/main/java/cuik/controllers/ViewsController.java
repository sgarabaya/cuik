package cuik.controllers;

import cuik.data.pojo.UserView;
import cuik.server.annotations.Controller;
import cuik.server.annotations.View;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import java.io.IOException;
import java.io.StringWriter;

@Controller("/")
public class ViewsController {

    private final Configuration viewCfg;

    public ViewsController() {
        viewCfg = new Configuration(Configuration.VERSION_2_3_35);
        viewCfg.setDefaultEncoding("UTF-8");
        viewCfg.setLogTemplateExceptions(false);
        viewCfg.setWrapUncheckedExceptions(true);
        viewCfg.setFallbackOnNullLoopVariable(false);

        viewCfg.setTemplateExceptionHandler(
            TemplateExceptionHandler.HTML_DEBUG_HANDLER
        );

        viewCfg.setClassForTemplateLoading(this.getClass(), "/views");
    }

    @View
    public String index() throws IOException, TemplateException {
        var user = new UserView();
        user.setName("Santi");
        user.setRoles(new String[] { "admin", "user", "employee" });

        var template = viewCfg.getTemplate("users.ftl");

        var stringWriter = new StringWriter();
        template.process(user, stringWriter);

        return stringWriter.toString();
    }
}
