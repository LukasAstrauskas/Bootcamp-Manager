package com.bootcamp.bootcampmanager.filedb;

import com.bootcamp.bootcampmanager.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class FileDBServiceImpl implements FileDBService{

    @Autowired
    private FileDBRepository fileDBRepository;

    public FileDB saveFile(MultipartFile file, Task task) {
        String fileName = file.getOriginalFilename();
        try {
            FileDB fileDB = new FileDB(fileName,file.getContentType(),file.getBytes(), task);
            if(fileDB.getType().equals("application/octet-stream")) {
                return null;
            } else {
                return fileDBRepository.save(fileDB);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Optional<FileDB> getFile(Long fileId) {
        return fileDBRepository.findById(fileId);
    }

    public List<FileDB> getFiles(){
        return fileDBRepository.findAll();
    }

    @Override
    public void deleteFileById(long id) {
        this.fileDBRepository.deleteById(id);
    }
}
