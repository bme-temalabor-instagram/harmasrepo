package instagram.hello;

import instagram.entities.User;
import instagram.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/")
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    static Facebook facebook;
    private ConnectionRepository connectionRepository;

    public LoginController(Facebook facebook, ConnectionRepository connectionRepository) {
        this.facebook = facebook;
        this.connectionRepository = connectionRepository;
    }

    @GetMapping
    public String helloFacebook(Model model) {
        if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {
            return "redirect:/connect/facebook";
        }


       // String id = facebook.fetchObject("me", String.class, "id");
        Map<String,String> userinfo = facebook.fetchObject("me", Map.class, "first_name");


        User current_user = new User();
        current_user.setName(userinfo.get("first_name"));
        current_user.setUserID(userinfo.get("id"));
        userRepository.save(current_user);


        model.addAttribute(

                "current_user", current_user
        );

       /* PagedList<Post> feed = facebook.feedOperations().getFeed();
        model.addAttribute("feed", feed);*/
        return "index";
    }
}
