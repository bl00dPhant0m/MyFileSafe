package org.spring.myfilesafe.controller;

import org.spring.myfilesafe.entity.File;
import org.spring.myfilesafe.entity.User;
import org.spring.myfilesafe.service.FileService;
import org.spring.myfilesafe.service.UserService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.List;

@Controller
public class FileController {
    private FileService fileService;
    private UserService userService;
    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @GetMapping(value = "/upload")
    public String uploadGet(@RequestParam String username,
                            Model model) {
        long userID = userService.getUserByUsername(username).getId();
        List<File> filesList = fileService.getAllFilesForUserID(userID);
        model.addAttribute("files", filesList);
        return "upload";
    }

    @PostMapping(value = "upload")
    public String uploadPost(@RequestParam MultipartFile file,
                             @RequestParam long userId) {


        return "upload";
    }

    @GetMapping(value = "/download/{id}")
    public Resource downloadGet(@PathVariable long id, Model model) {
        Path path = fileService.getFilePathByID(id);
        if (path == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File Not Found");
        }


        if (!path.toFile().exists() || !path.toFile().canRead()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File Not Found or not readable");
        }


        try {
            return new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }



}
