package AndrewWebServices;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class AndrewWebServicesTest {

    Database fakeDatabase;
    RecSys stubRecommender;
    PromoService mockPromoService;
    AndrewWebServices andrewWebService;

    @Before
    public void setUp() {
        // Fake: for database
        fakeDatabase = mock(Database.class);
        when(fakeDatabase.getPassword("Scotty")).thenReturn(17214);

        // Stub: for recommendation system
        stubRecommender = mock(RecSys.class);
        when(stubRecommender.getRecommendation("Scotty")).thenReturn("Animal House");

        // Mock: for promotional service
        mockPromoService = mock(PromoService.class);

        andrewWebService = new AndrewWebServices(fakeDatabase, stubRecommender, mockPromoService);
    }

    @Test
    public void testLogIn() {
        // No delay; uses fake password
        assertTrue(andrewWebService.logIn("Scotty", 17214));
    }

    @Test
    public void testGetRecommendation() {
        // Stubbed to return immediately
        assertEquals("Animal House", andrewWebService.getRecommendation("Scotty"));
    }

    @Test
    public void testSendEmail() {
        // Trigger sending promo email
        andrewWebService.sendPromoEmail("test@example.com");

        // Verify that promoService.mailTo() was called with the correct email
        verify(mockPromoService, times(1)).mailTo("test@example.com");
    }

    @Test
    public void testNoSendEmail() {
        // No email should be sent when we only log in
        andrewWebService.logIn("Scotty", 17214);

        // Verify that promoService.mailTo() was never called
        verify(mockPromoService, never()).mailTo(anyString());
    }
}
