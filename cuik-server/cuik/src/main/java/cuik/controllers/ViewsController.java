package cuik.controllers;

import cuik.data.MenuItemRepository;
import cuik.data.MenuRepository;
import cuik.data.pojo.MenuItemView;
import cuik.data.pojo.MenuView;
import cuik.data.pojo.UserView;
import cuik.server.annotations.Controller;
import cuik.server.annotations.View;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import java.io.IOException;
import java.io.StringWriter;
import java.util.UUID;

@Controller("/")
public class ViewsController {
    private final Configuration viewCfg;
    private final MenuRepository menuRepository;
    private final MenuItemRepository menuItemRepository;

    public ViewsController(MenuRepository menuRepository, MenuItemRepository menuItemRepository) {
        this.menuRepository = menuRepository;
        this.menuItemRepository = menuItemRepository;

        viewCfg = new Configuration(Configuration.VERSION_2_3_35);
        viewCfg.setDefaultEncoding("UTF-8");
        viewCfg.setLogTemplateExceptions(false);
        viewCfg.setWrapUncheckedExceptions(true);
        viewCfg.setFallbackOnNullLoopVariable(false);

        viewCfg.setTemplateExceptionHandler(
                TemplateExceptionHandler.HTML_DEBUG_HANDLER);

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

    @View("menu/{id}")
    public String viewMenu(String id) throws Exception {
        var menuId = UUID.fromString(id);
        var menu = menuRepository.findById(menuId);
        var menuItems = menuItemRepository.fetchByMenuId(menuId);

        var menuView = MenuView.from(menu, menuItems.stream().map(mi -> MenuItemView.from(mi, null)).toList());

        var template = viewCfg.getTemplate("menu.ftl");

        var stringWriter = new StringWriter();
        template.process(menuView, stringWriter);

        return stringWriter.toString();
    }
}
