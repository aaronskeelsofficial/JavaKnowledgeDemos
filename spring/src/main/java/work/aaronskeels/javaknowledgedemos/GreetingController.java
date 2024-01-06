package work.aaronskeels.javaknowledgedemos;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {

    @GetMapping("/greeting") // Maps GET requests to "/greeting" to this method
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) { // Pulls the GET parameter "name" if exists
        model.addAttribute("name", name);
        return "greeting";
    }
    
}
