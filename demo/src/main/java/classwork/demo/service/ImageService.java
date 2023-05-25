package classwork.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int uploadImage(byte[] imageBytes, String fileName, String fileType) {
        String sql = "INSERT INTO images (file_name, file_type, data) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, fileName, fileType, imageBytes);
        int imageId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        return imageId;
    }

    public byte[] getImage(int imageId) {
        String sql = "SELECT data FROM images WHERE id = ?";
        byte[] imageData = jdbcTemplate.queryForObject(sql, new Object[]{imageId}, byte[].class);
        return imageData;
    }

}