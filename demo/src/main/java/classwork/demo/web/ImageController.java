package classwork.demo.web;


import classwork.demo.service.ImageService;
import classwork.demo.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @Autowired
    private RoomService roomService;
    @PostMapping("/rooms/{roomId}/upload-image")
    public String uploadRoomImage(@PathVariable("roomId") Long roomId, @RequestParam("image") MultipartFile file) throws IOException {
        byte[] imageBytes = file.getBytes();
        String fileName = file.getOriginalFilename();
        String fileType = file.getContentType();
        roomService.uploadRoomImage(roomId, imageBytes, fileName, fileType);
        return "Image uploaded successfully for room with ID: " + roomId;
    }
    @GetMapping("/get-image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable("id") int imageId) {
        byte[] imageData = imageService.getImage(imageId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG); // Set the content type based on the actual image type
        return new ResponseEntity<byte[]>(imageData, headers, HttpStatus.OK);
    }


}