package pl.lpula;

import org.apache.tika.Tika;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class FileUploadController {

    private final Tika tika;

    public FileUploadController() {
        tika = new Tika();
    }

    @GetMapping("/")
    public String listUploadedFiles() {
        return "uploadForm";
    }

    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        System.out.println("Content-Type:" + file.getContentType());

        String detect = tika.detect(file.getInputStream());
        System.out.println(detect);

        return "redirect:/";
    }

}
