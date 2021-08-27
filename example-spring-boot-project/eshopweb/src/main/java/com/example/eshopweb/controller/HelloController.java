package com.example.eshopweb.controller;

import com.example.eshopweb.exception.NotFoundException;
import com.example.eshopweb.pojo.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Principal;
import java.util.Locale;

@RestController
public class HelloController {

    // http://localhost:8080/hello
//    @RequestMapping(method = RequestMethod.GET, path = "/hello")
    @GetMapping("/hello")
    public Message message() {
        return new Message("Spring Boot Works! :-)");
    }

    // http://localhost:8080/dog
    @GetMapping(path = "/dog", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] dog() throws IOException {
        return Files.readAllBytes(Path.of("files","dog.jpg"));
    }

    // http://localhost:8080/file?name=dog.jpg
    // http://localhost:8080/file?name=pom.xml
    @GetMapping("/file")
    public ResponseEntity<Object> file(@RequestParam String name) throws IOException {
        Path path = Path.of("files", name);
        if(!path.toFile().exists()) {
            throw new NotFoundException("File '" + name + "' does not exist!");
//            return ResponseEntity
//                    .status(HttpStatus.NOT_FOUND)
//                    .body(new Message("File '" + name + "' does not exist!"));
        }
        MediaType mediaType;
        if(name.endsWith(".jpg")) {
            mediaType = MediaType.IMAGE_JPEG;
        } else if(name.endsWith(".xml")) {
            mediaType = MediaType.APPLICATION_XML;
        } else {
            mediaType = MediaType.TEXT_HTML;
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(mediaType)
                .body(Files.readAllBytes(path));
    }

    @GetMapping("/stuff")
    public void doStuff(HttpServletRequest request, HttpServletResponse response, HttpSession session, Principal principal, Locale locale) throws IOException {
        response.getWriter().write("stuff");
    }

}
