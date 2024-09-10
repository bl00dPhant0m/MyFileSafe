package org.spring.myfilesafe.repository;


import org.spring.myfilesafe.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    List<File> getFilesByUserId(long id);
}
