package classwork.demo.service;

import classwork.demo.dto.Room;
import classwork.demo.exceptions.ResourceNotFoundException;
import classwork.demo.repositories.RoomRepository;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ImageService imageService;

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Optional<Room> getRoom(Long id) {
        return roomRepository.findById(id);
    }

    public Room saveRoom(Room room) {
        return roomRepository.save(room);
    }

    public Room updateRoom(Long id, Room updatedRoom) {
        return roomRepository.findById(id).map(room -> {
            room.setBasePrice(updatedRoom.getBasePrice());
            room.setType(updatedRoom.getType());
            return roomRepository.save(room);
        }).orElseThrow(() -> new ResourceNotFoundException("Room not found with id " + id));
    }

    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }

    public void uploadRoomImage(Long roomId, byte[] imageBytes, String fileName, String fileType) {
        Room room = getRoom(roomId).orElseThrow(() -> new ResourceNotFoundException("Room not found with id " + roomId));
        int imageId = imageService.uploadImage(imageBytes, fileName, fileType);
        room.getImageIds().add(imageId);
        saveRoom(room);
    }




    public void saveImageToFile(byte[] imageBytes, String filePath) throws IOException {
        FileUtils.writeByteArrayToFile(new File(filePath), imageBytes);
    }

}