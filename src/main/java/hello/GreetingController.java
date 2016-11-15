package hello;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import entities.Picture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import repositories.PictureRepository;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Controller
public class GreetingController {

    @Autowired
    private PictureRepository pictureRepository;


    Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "dlqdldxbw",
            "api_key", "696585694432693",
            "api_secret", "fCyfZITBxypoZoyU_0Il5pL_uD8"));

    @GetMapping("/greeting")
    public String greetingForm(Model model) {
        model.addAttribute("greeting", new Greeting());
        return "greeting";
    }

    @PostMapping("/greeting")
    public String greetingSubmit(@ModelAttribute Greeting greeting) {
        return "result";
    }

    @GetMapping("/index")
    public String index(Model model) {

        return "index";
    }

    @PostMapping("/index")
    public String uploadingSubmit(@RequestParam(value = "image", required = true) MultipartFile uploadedPhoto,
                                  @RequestParam(value = "title", required = true) String title,
                                  Model model)
    {
        //image URL
        String URL;
        Picture picture = new Picture();

        Map options = ObjectUtils.asMap(
                "public_id", "temp/" + new SimpleDateFormat("yyyy-MM-dd hh-mm-ss").format(new Date()) + "-" + title
        );

        try {
            Map uploadResult = cloudinary.uploader().upload(uploadedPhoto.getBytes(), options);
            URL = (String) uploadResult.get("URL");

            picture.setID((String)uploadResult.get("public_id") );
            picture.setName(title);
            pictureRepository.save(picture);

            model.addAttribute("picture", picture);


        } catch (IOException e) {
            e.printStackTrace();
        }
        return "index";

    }

}
