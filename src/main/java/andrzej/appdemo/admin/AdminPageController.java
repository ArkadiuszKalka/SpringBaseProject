package andrzej.appdemo.admin;

import java.util.List;

import javax.ws.rs.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import andrzej.appdemo.user.User;

@Controller
public class AdminPageController {
    private static int ELEMENTS = 4;

    @Autowired
    private AdminService adminService;

    @GET
    @RequestMapping(value = "/admin")
    @Secured(value = {"ROLE_ADMIN"})
    public String openAdminMainPage() {
        return "admin/admin";
    }

    @GET
    @RequestMapping(value = "/admin/users/{page}")
    @Secured(value = {"ROLE_ADMIN"})
    public String openAdminAllUsersPage(@PathVariable("page") int page, Model model) {
        Page<User> pages = getAllUserPagable(page - 1);
        int totalPages = pages.getTotalPages();
        int currentPage = pages.getNumber();
        List<User> userList = pages.getContent();
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", currentPage + 1);
        model.addAttribute("userList", userList);
        model.addAttribute("recordStartCounter", currentPage * ELEMENTS);
        return "admin/users";
    }


    private Page<User> getAllUserPagable(int page) {
        Page<User> pages = adminService.findAll(PageRequest.of(page, ELEMENTS));
        for (User users : pages) {
            int numerRoli = users.getRoles().iterator().next().getId();
            users.setNrRoli(numerRoli);
        }
        return pages;
    }
}
