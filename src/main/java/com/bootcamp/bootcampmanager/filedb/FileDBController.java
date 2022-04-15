package com.bootcamp.bootcampmanager.filedb;

import com.bootcamp.bootcampmanager.task.Task;
import com.bootcamp.bootcampmanager.task.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class FileDBController {

    @Autowired
    private FileDBService fileDBService;

    @Autowired
    public TaskService taskService;

    @GetMapping("/task")
    public String getFiles(Model model) {
        model.addAttribute("filesList", fileDBService.getFiles());
        return "task";
    }

    /* Make single file upload */
    @PostMapping("/upload-file")
    public String uploadMultipleFiles(@ModelAttribute("task") Task task,
                                      @RequestParam("files") MultipartFile[] files
    ) {
        task.setFileDB(fileDBService.saveFile(files[0], task));
        return "redirect:/tasks";
    }

    @GetMapping("/task/{taskId}/file/{fileId}")
    public ResponseEntity<ByteArrayResource> downloadFile(
            @PathVariable long taskId,
            @PathVariable Long fileId
    ) {
        taskService.getTaskById(taskId);
        FileDB fileDB = fileDBService.getFile(fileId).get();
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileDB.getType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment:filename=\"" + fileDB.getName() + "\"")
                .body(new ByteArrayResource(fileDB.getData()));
    }
}
