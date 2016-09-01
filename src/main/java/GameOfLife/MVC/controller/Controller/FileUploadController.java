package GameOfLife.MVC.controller.Controller;

import GameOfLife.MVC.model.Entity.Player;
import GameOfLife.MVC.model.Repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import sun.awt.image.ToolkitImage;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by sernowm on 25.08.2016.
 */
@Controller
public class FileUploadController {

    public static final String ROOT = "target/classes/static/img/";

    @Autowired
    private PlayerRepository playerRepository;

    @RequestMapping(method = RequestMethod.POST, value = "/profile")
    public String handleFileUpload(@RequestParam("avatar") MultipartFile file,
                                   HttpServletRequest request,
                                   Model model
    ) {
        Player player=playerRepository.findOneByName(request.getUserPrincipal().getName());
        model.addAttribute("profile",player);
        String name = request.getUserPrincipal().getName().toLowerCase().replace(" ","_");
        String type="png";
        switch(file.getContentType()){
            case "image/png":
                name += ".png";
                type="png";
                break;
            case "image/jpeg":
                name += ".jpg";
                type="jpg";
                break;
            case "image/gif":
                name += ".gif";
                type="gif";
                break;
            default:
                name = "";
        }
        if (!file.isEmpty() && name != "") {
            try {
                if(player.getAvatar()!=null){
                    Paths.get(ROOT,name).toFile().delete();
                }
                Files.copy(file.getInputStream(), Paths.get(ROOT,name));
                player.setAvatar(name);
                playerRepository.save(player);
                ImageIcon image = new ImageIcon(Paths.get(ROOT,name).toString());
                image.setImage(image.getImage().getScaledInstance(100,100, Image.SCALE_SMOOTH));
                ImageIO.write(((ToolkitImage)image.getImage()).getBufferedImage(),type,Paths.get(ROOT,name).toFile());
                System.err.println("You successfully uploaded " + file.getOriginalFilename() + "!");
            } catch (IOException|RuntimeException e) {
                System.err.println("Failued to upload " + file.getOriginalFilename() + " => " + e.getMessage());
            }
        } else {
            System.err.println("Failed to upload " + file.getOriginalFilename() + " because it was empty");
        }
        return "redirect:/profile";
    }

}