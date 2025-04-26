package com.hrs.hotelbooking.util;

import com.hrs.hotelbooking.model.HotelMetadata;
import com.hrs.hotelbooking.model.SearchResponse;
import com.hrs.hotelbooking.utils.CacheUtil;

import java.util.Arrays;
import java.util.List;

public class TestUtil {

    public static void initializeHotelMetadataCache(CacheUtil cacheUtil) {
        // Mumbai Hotels
        HotelMetadata tajMahal = createTajMahalMetadata();
        HotelMetadata oberoi = createOberoiMetadata();
        HotelMetadata marriott = createMarriottMetadata();

        // Delhi Hotels
        HotelMetadata leela = createLeelaMetadata();
        HotelMetadata radisson = createRadissonMetadata();
        HotelMetadata itc = createITCMetadata();

        // Punjab Hotels
        HotelMetadata tajChandigarh = createTajChandigarhMetadata();
        HotelMetadata hyattAmritsar = createHyattAmritsarMetadata();

        // Add all hotels to cache
        List<HotelMetadata> hotels = Arrays.asList(
            tajMahal, oberoi, marriott, leela, radisson, itc, tajChandigarh, hyattAmritsar
        );

        for (HotelMetadata hotel : hotels) {
            cacheUtil.addHotelMetadata(hotel);
        }
    }

    public static HotelMetadata createTajMahalMetadata() {
        HotelMetadata metadata = new HotelMetadata();
        metadata.setHotelCode("TAJ001");
        metadata.setImageUrls(Arrays.asList(
            "https://foto.hrsstatic.com/fotos/0/2/269/213/80/000000/http%3A%2F%2Ffoto-origin.hrsstatic.com%2Ffoto%2F9%2F5%2F8%2F4%2F%2Fteaser_958455.jpg"
        ));
        metadata.setDescription("Luxury 5-star hotel in Mumbai");
        metadata.setAmenities(Arrays.asList("Swimming Pool", "Spa", "Fine Dining", "Business Center"));
        metadata.setRating(4.8);
        metadata.setTotalReviews(1200);
        metadata.setLocation("Colaba, Mumbai");
        metadata.setCheckInTime("14:00");
        metadata.setCheckOutTime("12:00");
        return metadata;
    }

    public static HotelMetadata createOberoiMetadata() {
        HotelMetadata metadata = new HotelMetadata();
        metadata.setHotelCode("OBR002");
        metadata.setImageUrls(Arrays.asList(
            "https://foto.hrsstatic.com/fotos/0/2/269/213/80/000000/http%3A%2F%2Ffoto-origin.hrsstatic.com%2Ffoto%2F9%2F5%2F8%2F4%2F%2Fteaser_958457.jpg"
        ));
        metadata.setDescription("5-star luxury hotel with sea view");
        metadata.setAmenities(Arrays.asList("Beach Access", "Multiple Restaurants", "Conference Hall"));
        metadata.setRating(4.9);
        metadata.setTotalReviews(1500);
        metadata.setLocation("Marine Drive, Mumbai");
        metadata.setCheckInTime("15:00");
        metadata.setCheckOutTime("11:00");
        return metadata;
    }

    public static HotelMetadata createMarriottMetadata() {
        HotelMetadata metadata = new HotelMetadata();
        metadata.setHotelCode("MAR007");
        metadata.setImageUrls(Arrays.asList(
            "https://foto.hrsstatic.com/fotos/0/2/269/213/80/000000/http%3A%2F%2Ffoto-origin.hrsstatic.com%2Ffoto%2F9%2F5%2F8%2F4%2F%2Fteaser_958458.jpg"
        ));
        metadata.setDescription("5-star luxury hotel in Juhu");
        metadata.setAmenities(Arrays.asList("Beach View", "Multiple Restaurants", "Conference Center"));
        metadata.setRating(4.7);
        metadata.setTotalReviews(1000);
        metadata.setLocation("Juhu Tara Road, Mumbai");
        metadata.setCheckInTime("14:00");
        metadata.setCheckOutTime("12:00");
        return metadata;
    }

    public static HotelMetadata createLeelaMetadata() {
        HotelMetadata metadata = new HotelMetadata();
        metadata.setHotelCode("LEL003");
        metadata.setImageUrls(Arrays.asList(
            "https://foto.hrsstatic.com/fotos/0/2/269/213/80/000000/http%3A%2F%2Ffoto-origin.hrsstatic.com%2Ffoto%2F9%2F5%2F8%2F4%2F%2Fteaser_958459.jpg"
        ));
        metadata.setDescription("5-star hotel in Delhi");
        metadata.setAmenities(Arrays.asList("Golf Course", "Spa", "Multiple Cuisines"));
        metadata.setRating(4.7);
        metadata.setTotalReviews(1000);
        metadata.setLocation("Chanakyapuri, Delhi");
        metadata.setCheckInTime("14:00");
        metadata.setCheckOutTime("12:00");
        return metadata;
    }

    public static HotelMetadata createRadissonMetadata() {
        HotelMetadata metadata = new HotelMetadata();
        metadata.setHotelCode("RAD004");
        metadata.setImageUrls(Arrays.asList(
            "https://foto.hrsstatic.com/fotos/0/2/269/213/80/000000/http%3A%2F%2Ffoto-origin.hrsstatic.com%2Ffoto%2F9%2F5%2F8%2F4%2F%2Fteaser_958460.jpg"
        ));
        metadata.setDescription("4-star business hotel");
        metadata.setAmenities(Arrays.asList("Business Center", "Gym", "Restaurant"));
        metadata.setRating(4.5);
        metadata.setTotalReviews(800);
        metadata.setLocation("Connaught Place, Delhi");
        metadata.setCheckInTime("13:00");
        metadata.setCheckOutTime("11:00");
        return metadata;
    }

    public static HotelMetadata createITCMetadata() {
        HotelMetadata metadata = new HotelMetadata();
        metadata.setHotelCode("ITC008");
        metadata.setImageUrls(Arrays.asList(
            "https://foto.hrsstatic.com/fotos/0/2/269/213/80/000000/http%3A%2F%2Ffoto-origin.hrsstatic.com%2Ffoto%2F9%2F5%2F8%2F4%2F%2Fteaser_958461.jpg"
        ));
        metadata.setDescription("5-star luxury hotel");
        metadata.setAmenities(Arrays.asList("Fine Dining", "Spa", "Business Center"));
        metadata.setRating(4.8);
        metadata.setTotalReviews(1100);
        metadata.setLocation("Sardar Patel Marg, Delhi");
        metadata.setCheckInTime("14:00");
        metadata.setCheckOutTime("12:00");
        return metadata;
    }

    public static HotelMetadata createTajChandigarhMetadata() {
        HotelMetadata metadata = new HotelMetadata();
        metadata.setHotelCode("TAJ005");
        metadata.setImageUrls(Arrays.asList(
            "https://foto.hrsstatic.com/fotos/0/2/269/213/80/000000/http%3A%2F%2Ffoto-origin.hrsstatic.com%2Ffoto%2F9%2F5%2F8%2F4%2F%2Fteaser_958462.jpg"
        ));
        metadata.setDescription("5-star luxury in Chandigarh");
        metadata.setAmenities(Arrays.asList("Garden", "Spa", "Fine Dining"));
        metadata.setRating(4.6);
        metadata.setTotalReviews(900);
        metadata.setLocation("Sector 17, Chandigarh");
        metadata.setCheckInTime("14:00");
        metadata.setCheckOutTime("12:00");
        return metadata;
    }

    public static HotelMetadata createHyattAmritsarMetadata() {
        HotelMetadata metadata = new HotelMetadata();
        metadata.setHotelCode("HYT006");
        metadata.setImageUrls(Arrays.asList(
            "https://foto.hrsstatic.com/fotos/0/2/269/213/80/000000/http%3A%2F%2Ffoto-origin.hrsstatic.com%2Ffoto%2F9%2F5%2F8%2F4%2F%2Fteaser_958463.jpg"
        ));
        metadata.setDescription("4-star hotel near Golden Temple");
        metadata.setAmenities(Arrays.asList("Temple View", "Restaurant", "Business Center"));
        metadata.setRating(4.4);
        metadata.setTotalReviews(700);
        metadata.setLocation("Near Golden Temple, Amritsar");
        metadata.setCheckInTime("14:00");
        metadata.setCheckOutTime("12:00");
        return metadata;
    }

    public static SearchResponse createTajMahalResponse() {
        SearchResponse response = new SearchResponse();
        response.setHotelCode("TAJ001");
        response.setName("Taj Mahal Palace");
        response.setCity("Mumbai");
        response.setCategory("5-star");
        response.setBasePrice(10000.0);
        response.setImageUrls(Arrays.asList("https://foto.hrsstatic.com/fotos/0/2/269/213/80/000000/http%3A%2F%2Ffoto-origin.hrsstatic.com%2Ffoto%2F9%2F5%2F8%2F4%2F%2Fteaser_958455.jpg"));
        response.setDescription("Luxury 5-star hotel in Mumbai");
        response.setAmenities(Arrays.asList("Swimming Pool", "Spa", "Fine Dining", "Business Center"));
        response.setRating(4.8);
        response.setTotalReviews(1200);
        response.setLocation("Colaba, Mumbai");
        response.setCheckInTime("14:00");
        response.setCheckOutTime("12:00");
        response.setAvailable(true);
        return response;
    }

    public static SearchResponse createOberoiResponse() {
        SearchResponse response = new SearchResponse();
        response.setHotelCode("OBR002");
        response.setName("The Oberoi");
        response.setCity("Mumbai");
        response.setCategory("5-star");
        response.setBasePrice(15000.0);
        response.setImageUrls(Arrays.asList("https://foto.hrsstatic.com/fotos/0/2/269/213/80/000000/http%3A%2F%2Ffoto-origin.hrsstatic.com%2Ffoto%2F9%2F5%2F8%2F4%2F%2Fteaser_958457.jpg"));
        response.setDescription("5-star luxury hotel with sea view");
        response.setAmenities(Arrays.asList("Beach Access", "Multiple Restaurants", "Conference Hall"));
        response.setRating(4.9);
        response.setTotalReviews(1500);
        response.setLocation("Marine Drive, Mumbai");
        response.setCheckInTime("15:00");
        response.setCheckOutTime("11:00");
        response.setAvailable(true);
        return response;
    }
} 