package DAOImpl;

import java.util.List;
import java.util.Scanner;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import Entities.RoomTypes;

public class RoomTypesDaoImpl {
	private SessionFactory factory;

	public RoomTypesDaoImpl(SessionFactory factory) {
		this.factory = factory;
	}

	// Main method for interacting with RoomTypes
	public static void main(String[] args) {
		// Configuration setup
		Configuration config = new Configuration();
		config.configure("hibernate.cfg.xml");
		SessionFactory factory = config.buildSessionFactory();
		// Creating RoomTypesDaoImpl instance
		RoomTypesDaoImpl roomTypesDao = new RoomTypesDaoImpl(factory);
		Scanner scanner = new Scanner(System.in);

		// Menu loop
		while (true) {
			System.out.println("Select operation:");
			System.out.println("1. Add Room Type");
			System.out.println("2. Read");
			System.out.println("3. Update");
			System.out.println("4. Delete");
			System.out.println("5. Exit");

			int choice = scanner.nextInt();
			scanner.nextLine(); // Consume newline

			switch (choice) {
			case 1:
				roomTypesDao.addRoomType(scanner);
				break;
			case 2:
				roomTypesDao.readAllRoomTypes();
				break;
			case 3:
				roomTypesDao.updateRoomType(scanner);
				break;
			case 4:
				roomTypesDao.deleteRoomType(scanner);
				break;
			case 5:
				factory.close();
				scanner.close();
				System.exit(0);
			default:
				System.out.println("Invalid choice. Please try again.");
			}
		}
	}

	// Method to add a new Room Type
	public void addRoomType(Scanner scanner) {
		// Taking input from user
		System.out.println("Enter Room Type Name:");
		String typeName = scanner.nextLine();
		System.out.println("Enter Room Purpose:");
		String purpose = scanner.nextLine();
		System.out.println("Enter Rent Price:");
		int rentPrice = scanner.nextInt();
		scanner.nextLine(); // Consume newline
		System.out.println("Enter Amenities:");
		String amenities = scanner.nextLine();

		// Database interaction
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			RoomTypes roomType = new RoomTypes();
			roomType.setTypeName(typeName);
			roomType.setPurpose(purpose);
			roomType.setRentPrice(rentPrice);
			roomType.setAmenities(amenities);
			session.save(roomType);
			tx.commit();
			System.out.println("Room Type added successfully.");
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	// Method to read all Room Types
	public List<RoomTypes> readAllRoomTypes() {
		Session session = factory.openSession();
		Transaction tx = null;
		List<RoomTypes> roomTypes = null; // Initialize the list

		try {
			tx = session.beginTransaction();
			Query query = session.createQuery("from RoomTypes");
			roomTypes = query.list(); // Assign the query result to the list
			tx.commit();

			 // Output the Room Types
			if (roomTypes.isEmpty()) {
				System.out.println("No Room Types found.");
			} else {
				for (RoomTypes roomType : roomTypes) {
					System.out.println(roomType);
				}
			}
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}

		return roomTypes; // Return the list
	}

	// Method to update a Room Type
	public void updateRoomType(Scanner scanner) {
		System.out.println("Enter Room Type ID to update:");
		int typeId = scanner.nextInt();
		scanner.nextLine(); // Consume newline
		RoomTypes roomType = getRoomTypeById(typeId);

		if (roomType != null) {
			// Updating the Room Type
			System.out.println("Enter new Room Type Name:");
			roomType.setTypeName(scanner.nextLine());
			System.out.println("Enter new Room Purpose:");
			roomType.setPurpose(scanner.nextLine());
			System.out.println("Enter new Rent Price:");
			roomType.setRentPrice(scanner.nextInt());
			scanner.nextLine(); // Consume newline
			System.out.println("Enter new Amenities:");
			roomType.setAmenities(scanner.nextLine());

			 // Database interaction
			Session session = factory.openSession();
			Transaction tx = null;
			try {
				tx = session.beginTransaction();
				session.update(roomType);
				tx.commit();
				System.out.println("Room Type updated successfully.");
			} catch (Exception e) {
				if (tx != null) {
					tx.rollback();
				}
				e.printStackTrace();
			} finally {
				session.close();
			}
		} else {
			System.out.println("Room Type not found.");
		}
	}

	// Method to delete a Room Type
	public void deleteRoomType(Scanner scanner) {
		// Input from user
		System.out.println("Enter Room Type ID to delete:");
		int typeId = scanner.nextInt();
		scanner.nextLine(); // Consume newline
		RoomTypes roomType = getRoomTypeById(typeId);

		if (roomType != null) {
			// Database interaction
			Session session = factory.openSession();
			Transaction tx = null;
			try {
				tx = session.beginTransaction();
				session.delete(roomType);
				tx.commit();
				System.out.println("Room Type deleted successfully.");
			} catch (Exception e) {
				if (tx != null) {
					tx.rollback();
				}
				e.printStackTrace();
			} finally {
				session.close();
			}
		} else {
			System.out.println("Room Type not found.");
		}
	}
	

	// Method to get a Room Type by its ID
	public RoomTypes getRoomTypeById(int typeId) {
		Session session = factory.openSession();
		Transaction tx = null;
		RoomTypes roomType = null;
		try {
			tx = session.beginTransaction();
			roomType = session.get(RoomTypes.class, typeId);
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		return roomType;
	}

}
