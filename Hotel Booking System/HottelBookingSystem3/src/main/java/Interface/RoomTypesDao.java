package Interface;


import Entities.RoomTypes;

import java.util.List;
import java.util.Scanner;

public interface RoomTypesDao {
    RoomTypes getRoomTypeById(int typeId);
    void updateRoomType(Scanner scanner);
    
    void deleteRoomType(Scanner scanner);
    List getAllRoomTypes();
}
