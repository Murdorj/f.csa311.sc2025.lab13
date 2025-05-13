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
        fakeDatabase = mock(Database.class);
        when(fakeDatabase.getPassword("Scotty")).thenReturn(17214);

        stubRecommender = mock(RecSys.class);
        when(stubRecommender.getRecommendation("Scotty")).thenReturn("Animal House");

        mockPromoService = mock(PromoService.class);

        andrewWebService = new AndrewWebServices(fakeDatabase, stubRecommender, mockPromoService);
    }

    @Test
    public void testLogIn() {
        assertTrue(andrewWebService.logIn("Scotty", 17214));
    }

    @Test
    public void testGetRecommendation() {
        assertEquals("Animal House", andrewWebService.getRecommendation("Scotty"));
    }

    @Test
    public void testSendEmail() {
        andrewWebService.sendPromoEmail("test@example.com");

        verify(mockPromoService, times(1)).mailTo("test@example.com");
    }

    @Test
    public void testNoSendEmail() {
        andrewWebService.logIn("Scotty", 17214);

        verify(mockPromoService, never()).mailTo(anyString());
    }
}
