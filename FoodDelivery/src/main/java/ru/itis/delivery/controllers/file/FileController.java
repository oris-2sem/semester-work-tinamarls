package ru.itis.delivery.controllers.file;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.delivery.dto.file.FileDto;
import ru.itis.delivery.services.FileService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Controller
@RequestMapping("/file")
public class FileController {

    FileService fileService;

    @GetMapping("/{file-name}")
    public void getFile(@PathVariable("file-name") String fileName, HttpServletResponse response) throws IOException {

        FileDto file = fileService.getFileByName(fileName);

        response.setContentType(file.getMimeType());
        response.setContentLength(file.getSize().intValue());
        response.setHeader("Content-Disposition", "attachment; fileName=\"" + file.getOriginalFileName() + "\"");

        Files.copy(file.getPath(), response.getOutputStream());
        response.flushBuffer();
    }


}
