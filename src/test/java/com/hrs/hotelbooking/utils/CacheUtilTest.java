package com.hrs.hotelbooking.utils;

import com.hrs.hotelbooking.model.HotelMetadata;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CacheUtilTest {

    private CacheUtil cacheUtil;

    @BeforeEach
    void setUp() {
        cacheUtil = CacheUtil.getInstance();
        cacheUtil.clearAll();
    }

    @Test
    void testAddAndGetHotelMetadata() {
        // Mumbai Hotels
        HotelMetadata tajMahal = new HotelMetadata(
                "TAJ001",
                Arrays.asList(
                        "https://foto.hrsstatic.com/fotos/0/2/269/213/80/000000/http%3A%2F%2Ffoto-origin.hrsstatic.com%2Ffoto%2F9%2F5%2F8%2F4%2F%2Fteaser_958455.jpg",
                        "https://foto.hrsstatic.com/fotos/0/2/269/213/80/000000/http%3A%2F%2Ffoto-origin.hrsstatic.com%2Ffoto%2F9%2F5%2F8%2F4%2F%2Fteaser_958456.jpg"
                ),
                "Luxury 5-star hotel in Mumbai",
                Arrays.asList("Swimming Pool", "Spa", "Fine Dining", "Business Center"),
                4.8,
                1200,
                "Colaba, Mumbai",
                "14:00",
                "12:00",
                10000.0
        );

        HotelMetadata oberoi = new HotelMetadata(
                "OBR002",
                Arrays.asList(
                        "https://foto.hrsstatic.com/fotos/0/2/269/213/80/000000/http%3A%2F%2Ffoto-origin.hrsstatic.com%2Ffoto%2F9%2F5%2F8%2F4%2F%2Fteaser_958457.jpg"
                ),
                "5-star luxury hotel with sea view",
                Arrays.asList("Beach Access", "Multiple Restaurants", "Conference Hall"),
                4.9,
                1500,
                "Marine Drive, Mumbai",
                "15:00",
                "11:00",
                15000.0
        );

        // Delhi Hotels
        HotelMetadata leela = new HotelMetadata(
                "LEL003",
                Arrays.asList(
                        "https://foto.hrsstatic.com/fotos/0/2/269/213/80/000000/http%3A%2F%2Ffoto-origin.hrsstatic.com%2Ffoto%2F9%2F5%2F8%2F4%2F%2Fteaser_958458.jpg"
                ),
                "5-star hotel in Delhi",
                Arrays.asList("Golf Course", "Spa", "Multiple Cuisines"),
                4.7,
                1000,
                "Chanakyapuri, Delhi",
                "14:00",
                "12:00",
                10000.0
        );

        HotelMetadata radisson = new HotelMetadata(
                "RAD004",
                Arrays.asList(
                        "https://foto.hrsstatic.com/fotos/0/2/269/213/80/000000/http%3A%2F%2Ffoto-origin.hrsstatic.com%2Ffoto%2F9%2F5%2F8%2F4%2F%2Fteaser_958459.jpg"
                ),
                "4-star business hotel",
                Arrays.asList("Business Center", "Gym", "Restaurant"),
                4.5,
                800,
                "Connaught Place, Delhi",
                "13:00",
                "11:00",
                12000.0
        );

        // Punjab Hotels
        HotelMetadata tajChandigarh = new HotelMetadata(
                "TAJ005",
                Arrays.asList(
                        "https://foto.hrsstatic.com/fotos/0/2/269/213/80/000000/http%3A%2F%2Ffoto-origin.hrsstatic.com%2Ffoto%2F9%2F5%2F8%2F4%2F%2Fteaser_958460.jpg"
                ),
                "5-star luxury in Chandigarh",
                Arrays.asList("Garden", "Spa", "Fine Dining"),
                4.6,
                900,
                "Sector 17, Chandigarh",
                "14:00",
                "12:00",
                12000.0
        );

        HotelMetadata hyattAmritsar = new HotelMetadata(
                "HYT006",
                Arrays.asList(
                        "https://foto.hrsstatic.com/fotos/0/2/269/213/80/000000/http%3A%2F%2Ffoto-origin.hrsstatic.com%2Ffoto%2F9%2F5%2F8%2F4%2F%2Fteaser_958461.jpg"
                ),
                "4-star hotel near Golden Temple",
                Arrays.asList("Temple View", "Restaurant", "Business Center"),
                4.4,
                700,
                "Near Golden Temple, Amritsar",
                "14:00",
                "12:00",
                12000.0
        );

        // Add all hotels to cache
        List<HotelMetadata> hotels = Arrays.asList(
                tajMahal, oberoi, leela, radisson, tajChandigarh, hyattAmritsar
        );

        for (HotelMetadata hotel : hotels) {
            cacheUtil.addHotelMetadata(hotel);
        }

        // Test retrieval
        for (HotelMetadata hotel : hotels) {
            HotelMetadata retrieved = cacheUtil.getHotelMetadata(hotel.getHotelCode());
            assertNotNull(retrieved);
            assertEquals(hotel.getHotelCode(), retrieved.getHotelCode());
            assertEquals(hotel.getDescription(), retrieved.getDescription());
            assertEquals(hotel.getRating(), retrieved.getRating());
            assertEquals(hotel.getTotalReviews(), retrieved.getTotalReviews());
            assertEquals(hotel.getImageUrls().size(), retrieved.getImageUrls().size());
            assertEquals(hotel.getAmenities().size(), retrieved.getAmenities().size());
        }
    }

    @Test
    void testClearAll() {
        HotelMetadata hotel1 = new HotelMetadata(
                "TEST001",
                Arrays.asList("https://example.com/image1.jpg"),
                "Test Hotel 1",
                Arrays.asList("Test Amenity 1"),
                4.0,
                100,
                "Test Location 1",
                "14:00",
                "12:00",
                10000.0
        );

        HotelMetadata hotel2 = new HotelMetadata(
                "TEST002",
                Arrays.asList("https://example.com/image2.jpg"),
                "Test Hotel 2",
                Arrays.asList("Test Amenity 2"),
                4.5,
                200,
                "Test Location 2",
                "15:00",
                "11:00",
                10000.0
        );

        cacheUtil.addHotelMetadata(hotel1);
        cacheUtil.addHotelMetadata(hotel2);

        assertNotNull(cacheUtil.getHotelMetadata("TEST001"));
        assertNotNull(cacheUtil.getHotelMetadata("TEST002"));

        cacheUtil.clearAll();

        assertNull(cacheUtil.getHotelMetadata("TEST001"));
        assertNull(cacheUtil.getHotelMetadata("TEST002"));
    }
} 