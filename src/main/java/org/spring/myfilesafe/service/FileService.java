package org.spring.myfilesafe.service;

import org.spring.myfilesafe.entity.File;
import org.spring.myfilesafe.entity.User;
import org.spring.myfilesafe.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Supplier;

public class FileService {
    private static final String FILE_DIRECTORY = "/uploads";

    private FileRepository fileRepository;
    public FileService(@Autowired FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public File saveFile(MultipartFile file, User user) throws IOException {
        String userDirectory = FILE_DIRECTORY + "/" + user.getUsername();
        Path userDirectoryPath = Paths.get(userDirectory);

        if (!Files.exists(userDirectoryPath)) {
            Files.createDirectories(userDirectoryPath);
        }

        Path filePath = userDirectoryPath.resolve(file.getOriginalFilename());
        file.transferTo(filePath.toFile());

        File newFile = new File();
        newFile.setFileName(file.getOriginalFilename());
        newFile.setUser(user);
        newFile.setFilePath(filePath.toString());

        fileRepository.save(newFile);
        return newFile;
    }

    public Path getFilePathByID(long id) {
        File file = fileRepository.findById(id).orElseThrow(()->new RuntimeException("нет id у файла"));
        return Paths.get(file.getFilePath());
    }

    public List<File> getAllFilesForUserID(long userID) {
        return fileRepository.getFilesByUserId(userID);
    }

}
