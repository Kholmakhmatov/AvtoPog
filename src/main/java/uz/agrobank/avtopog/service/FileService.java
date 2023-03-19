package uz.agrobank.avtopog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import uz.agrobank.avtopog.exceptions.UniversalException;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FileService {
    public void download(String path, String name, HttpServletResponse response) {

        try {
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + name);
            FileInputStream inputStream = new FileInputStream(path + "/" + name);
            FileCopyUtils.copy(inputStream, response.getOutputStream());
        } catch (IOException ignored) {
            throw new UniversalException("File read error", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}