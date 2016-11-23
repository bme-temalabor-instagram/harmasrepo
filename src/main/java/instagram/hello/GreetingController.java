package instagram.hello;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.sun.org.apache.xpath.internal.operations.Mod;
import instagram.entities.Picture;
import oracle.jrockit.jfr.StringConstantPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import instagram.repositories.PictureRepository;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class GreetingController {

    @Autowired
    private PictureRepository pictureRepository;


    private Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "dlqdldxbw",
            "api_key", "696585694432693",
            "api_secret", "fCyfZITBxypoZoyU_0Il5pL_uD8"));

    @GetMapping("/index")
    public String index(Model model) {

        return "index";
    }

    @PostMapping("/index")
    public String uploadingSubmit(@RequestParam(value = "image", required = true) MultipartFile uploadedPhoto,
                                  @RequestParam(value = "title", required = true) String title,
                                  Model model)
    {
        String URL;
        Picture picture = new Picture();

        try {

            Map options = ObjectUtils.asMap(
                "public_id", "temp/" + new SimpleDateFormat("yyyy-MM-dd hh-mm-ss").format(new Date()) + "-" + title/*,
                    "allowed_formats", new String[]{"jpg","png","bmp"}*/
            );


            Map uploadResult = cloudinary.uploader().upload(uploadedPhoto.getBytes(), options);
            URL = (String) uploadResult.get("URL");

            picture.setID((String)uploadResult.get("public_id") );
            picture.setName(title);
            pictureRepository.save(picture);

            model.addAttribute("picture", picture);


        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:error";
        }
        return "redirect:pictures";

    }

    @GetMapping("/pictures")
    public String loadPictures(Model model) {
        List<Picture> pictures = pictureRepository.findAll();

        List<PhotoWithUrl> urls = new ArrayList<>();
        for (Picture p : pictures) {
            urls.add(new PhotoWithUrl(p, new Transformation().width(300).height(300).crop("fill")));
        }

        model.addAttribute("images",urls);

        return "pictures";
    }

    @GetMapping("/error")
    public String error(Model model){
        return "redirect:index";
    }



    public class PhotoWithUrl {
        private Picture photo;
        private String url;

        public PhotoWithUrl(Picture photo, Transformation transformation) {
            this.photo = photo;
            this.url = cloudinary.url()
                    .transformation(transformation).imageTag(photo.getID());
        }

        public Picture getPhoto() {
            return photo;
        }

        public void setPhoto(Picture photo) {
            this.photo = photo;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

}
