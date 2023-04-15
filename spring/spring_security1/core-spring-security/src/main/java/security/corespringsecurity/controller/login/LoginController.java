package security.corespringsecurity.controller.login;



import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

//    @RequestMapping(value="/login")
//    public String login(@RequestParam(value = "error", required = false) String error,
//                        @RequestParam(value = "exception", required = false) String exception, Model model){
//        model.addAttribute("error",error);
//        model.addAttribute("exception",exception);
//
//        System.out.println("error = " + error);
//        System.out.println("exception = " + exception);
//        return "login";
//    }

    @RequestMapping(value="/login")
    public String login(@RequestParam(value = "error", required = false) String error, Model model){
        model.addAttribute("error",error);

        System.out.println("error = " + error);
        return "login";
    }

    @GetMapping("/login")
    private String login() {
        return "/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest req, HttpServletResponse res) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(req, res, authentication);
        }
        return "redirect:/login";
    }

//    @GetMapping("/users")
//    private String register() {
//        return "/user/login/register";
//    }
}
