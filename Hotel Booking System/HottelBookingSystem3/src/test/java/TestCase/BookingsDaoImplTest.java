package TestCase;
import DAOImpl.BookingsDaoImpl;
import Entities.Bookings;
import junit.framework.TestCase;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class BookingsDaoImplTest extends TestCase {

    private BookingsDaoImpl bookingsDao;
    private SessionFactory factory;

    public void setUp() throws Exception {
        super.setUp();
        Configuration config = new Configuration();
        config.configure("hibernate.cfg.xml");
        factory = config.buildSessionFactory();
        bookingsDao = new BookingsDaoImpl(factory);
    }



    public void testDeleteBooking() {
        // Creating a mock booking to save and delete
        Bookings booking = new Bookings();
        booking.setBookingID(2);
        booking.setTotalAmount(200.0);

        // Saving the booking
        bookingsDao.saveBooking(booking);

        // Deleting the booking
        bookingsDao.deleteBooking(2);

        // Fetching the deleted booking and asserting its absence
        Bookings deletedBooking = fetchBooking(2);
        assertNull(deletedBooking);
    }

    public void testUpdateBooking() {
        // Creating a mock booking to save and update
        Bookings booking = new Bookings();
        booking.setBookingID(3);
        booking.setTotalAmount(300.0);

        // Saving the booking
        bookingsDao.saveBooking(booking);

        // Fetching the saved booking
        Bookings existingBooking = fetchBooking(3);
        assertNotNull(existingBooking);

        // Updating the booking
        existingBooking.setTotalAmount(400.0);
        bookingsDao.updateBooking(existingBooking);

        // Fetching the updated booking and asserting the changes
        Bookings updatedBooking = fetchBooking(3);
        assertNotNull(updatedBooking);
        assertEquals(400.0, updatedBooking.getTotalAmount());
    }

    // Utility method to fetch a booking by its ID
    private Bookings fetchBooking(int bookingId) {
        Session session = factory.openSession();
        Transaction transaction = null;
        Bookings booking = null;
        try {
            transaction = session.beginTransaction();
            booking = session.get(Bookings.class, bookingId);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return booking;
    }

    public void tearDown() throws Exception {
        super.tearDown();
        factory.close();
    }
}