package com.hrs.hotelbooking.controller;

import com.hrs.hotelbooking.model.HotelMetadata;
import com.hrs.hotelbooking.utils.CacheUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cache")
public class CacheController {

    private final CacheUtil cacheUtil;

    @Autowired
    public CacheController(CacheUtil cacheUtil) {
        this.cacheUtil = cacheUtil;
    }

    @Operation(
            summary = "Initialize Cache",
            description = "Initializes the cache with test hotel data for 8 hotels across different cities"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Cache initialized successfully",
                    content = @Content(schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = String.class))
            )
    })
    @PostMapping("/initialize")
    public ResponseEntity<String> initializeCache() {
        try {
            // Get all hotel codes from the cache
            List<String> hotelCodes = new ArrayList<>();
            hotelCodes.add("TAJ001"); // Taj Mahal Palace
            hotelCodes.add("OBR002"); // The Oberoi
            hotelCodes.add("MAR007"); // Marriott
            hotelCodes.add("LEL003"); // The Leela
            hotelCodes.add("RAD004"); // Radisson
            hotelCodes.add("ITC008"); // ITC
            hotelCodes.add("TAJ005"); // Taj Chandigarh
            hotelCodes.add("HYT006"); // Hyatt Amritsar

            // Create and add hotel metadata for each hotel
            for (String hotelCode : hotelCodes) {
                HotelMetadata metadata = createHotelMetadata(hotelCode);
                if (metadata != null) {
                    cacheUtil.addHotelMetadata(metadata);
                }
            }

            return ResponseEntity.ok("Cache initialized successfully with test data");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Failed to initialize cache: " + e.getMessage());
        }
    }

    @Operation(
            summary = "Clear Cache",
            description = "Clears all data from the cache"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Cache cleared successfully",
                    content = @Content(schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = String.class))
            )
    })
    @DeleteMapping("/clear")
    public ResponseEntity<String> clearCache() {
        try {
            cacheUtil.clearAll();
            return ResponseEntity.ok("Cache cleared successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Failed to clear cache: " + e.getMessage());
        }
    }

    @Operation(
            summary = "Get Cache Status",
            description = "Returns the number of hotels currently in the cache"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Cache status retrieved successfully",
                    content = @Content(schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = String.class))
            )
    })
    @GetMapping("/status")
    public ResponseEntity<String> getCacheStatus() {
        try {
            List<HotelMetadata> hotels = getAllHotelMetadata();
            return ResponseEntity.ok("Cache contains " + hotels.size() + " hotels");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Failed to get cache status: " + e.getMessage());
        }
    }

    @Operation(
            summary = "Get All Hotels",
            description = "Returns a list of all hotels currently in the cache"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Hotels retrieved successfully",
                    content = @Content(schema = @Schema(implementation = HotelMetadata.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    })
    @GetMapping("/hotels")
    public ResponseEntity<List<HotelMetadata>> getAllHotels() {
        try {
            List<HotelMetadata> hotels = getAllHotelMetadata();
            return ResponseEntity.ok(hotels);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    private List<HotelMetadata> getAllHotelMetadata() {
        List<String> hotelCodes = List.of(
                "TAJ001", "OBR002", "MAR007", "LEL003", "RAD004", "ITC008", "TAJ005", "HYT006"
        );

        return hotelCodes.stream()
                .map(cacheUtil::getHotelMetadata)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private HotelMetadata createHotelMetadata(String hotelCode) {
        HotelMetadata metadata = new HotelMetadata();
        metadata.setHotelCode(hotelCode);

        switch (hotelCode) {
            case "TAJ001":
                metadata.setImageUrls(List.of("https://foto.hrsstatic.com/fotos/0/2/269/213/80/000000/http%3A%2F%2Ffoto-origin.hrsstatic.com%2Ffoto%2F9%2F5%2F8%2F4%2F%2Fteaser_958455.jpg"));
                metadata.setDescription("Luxury 5-star hotel in Mumbai");
                metadata.setAmenities(List.of("Swimming Pool", "Spa", "Fine Dining", "Business Center"));
                metadata.setRating(4.8);
                metadata.setTotalReviews(1200);
                metadata.setLocation("Colaba, Mumbai");
                metadata.setCheckInTime("14:00");
                metadata.setCheckOutTime("12:00");
                metadata.setBasePrice(10000.0);
                break;
            case "OBR002":
                metadata.setImageUrls(List.of("https://foto.hrsstatic.com/fotos/0/2/269/213/80/000000/http%3A%2F%2Ffoto-origin.hrsstatic.com%2Ffoto%2F9%2F5%2F8%2F4%2F%2Fteaser_958457.jpg"));
                metadata.setDescription("5-star luxury hotel with sea view");
                metadata.setAmenities(List.of("Beach Access", "Multiple Restaurants", "Conference Hall"));
                metadata.setRating(4.9);
                metadata.setTotalReviews(1500);
                metadata.setLocation("Marine Drive, Mumbai");
                metadata.setCheckInTime("15:00");
                metadata.setCheckOutTime("11:00");
                metadata.setBasePrice(15000.0);
                break;
            case "MAR007":
                metadata.setImageUrls(List.of("https://foto.hrsstatic.com/fotos/0/2/269/213/80/000000/http%3A%2F%2Ffoto-origin.hrsstatic.com%2Ffoto%2F9%2F5%2F8%2F4%2F%2Fteaser_958458.jpg"));
                metadata.setDescription("5-star luxury hotel in Juhu");
                metadata.setAmenities(List.of("Beach View", "Multiple Restaurants", "Conference Center"));
                metadata.setRating(4.7);
                metadata.setTotalReviews(1000);
                metadata.setLocation("Juhu Tara Road, Mumbai");
                metadata.setCheckInTime("14:00");
                metadata.setCheckOutTime("12:00");
                metadata.setBasePrice(12000.0);
                break;
            case "LEL003":
                metadata.setImageUrls(List.of("https://foto.hrsstatic.com/fotos/0/2/269/213/80/000000/http%3A%2F%2Ffoto-origin.hrsstatic.com%2Ffoto%2F9%2F5%2F8%2F4%2F%2Fteaser_958459.jpg"));
                metadata.setDescription("5-star hotel in Delhi");
                metadata.setAmenities(List.of("Golf Course", "Spa", "Multiple Cuisines"));
                metadata.setRating(4.7);
                metadata.setTotalReviews(1000);
                metadata.setLocation("Chanakyapuri, Delhi");
                metadata.setCheckInTime("14:00");
                metadata.setCheckOutTime("12:00");
                metadata.setBasePrice(10000.0);
                break;
            case "RAD004":
                metadata.setImageUrls(List.of("https://foto.hrsstatic.com/fotos/0/2/269/213/80/000000/http%3A%2F%2Ffoto-origin.hrsstatic.com%2Ffoto%2F9%2F5%2F8%2F4%2F%2Fteaser_958460.jpg"));
                metadata.setDescription("4-star business hotel");
                metadata.setAmenities(List.of("Business Center", "Gym", "Restaurant"));
                metadata.setRating(4.5);
                metadata.setTotalReviews(800);
                metadata.setLocation("Connaught Place, Delhi");
                metadata.setCheckInTime("13:00");
                metadata.setCheckOutTime("11:00");
                metadata.setBasePrice(12000.0);
                break;
            case "ITC008":
                metadata.setImageUrls(List.of("https://foto.hrsstatic.com/fotos/0/2/269/213/80/000000/http%3A%2F%2Ffoto-origin.hrsstatic.com%2Ffoto%2F9%2F5%2F8%2F4%2F%2Fteaser_958461.jpg"));
                metadata.setDescription("5-star luxury hotel");
                metadata.setAmenities(List.of("Fine Dining", "Spa", "Business Center"));
                metadata.setRating(4.8);
                metadata.setTotalReviews(1100);
                metadata.setLocation("Sardar Patel Marg, Delhi");
                metadata.setCheckInTime("14:00");
                metadata.setCheckOutTime("12:00");
                metadata.setBasePrice(13000.0);
                break;
            case "TAJ005":
                metadata.setImageUrls(List.of("https://foto.hrsstatic.com/fotos/0/2/269/213/80/000000/http%3A%2F%2Ffoto-origin.hrsstatic.com%2Ffoto%2F9%2F5%2F8%2F4%2F%2Fteaser_958462.jpg"));
                metadata.setDescription("5-star luxury in Chandigarh");
                metadata.setAmenities(List.of("Garden", "Spa", "Fine Dining"));
                metadata.setRating(4.6);
                metadata.setTotalReviews(900);
                metadata.setLocation("Sector 17, Chandigarh");
                metadata.setCheckInTime("14:00");
                metadata.setCheckOutTime("12:00");
                metadata.setBasePrice(12000.0);
                break;
            case "HYT006":
                metadata.setImageUrls(List.of("https://foto.hrsstatic.com/fotos/0/2/269/213/80/000000/http%3A%2F%2Ffoto-origin.hrsstatic.com%2Ffoto%2F9%2F5%2F8%2F4%2F%2Fteaser_958463.jpg"));
                metadata.setDescription("4-star hotel near Golden Temple");
                metadata.setAmenities(List.of("Temple View", "Restaurant", "Business Center"));
                metadata.setRating(4.4);
                metadata.setTotalReviews(700);
                metadata.setLocation("Near Golden Temple, Amritsar");
                metadata.setCheckInTime("14:00");
                metadata.setCheckOutTime("12:00");
                metadata.setBasePrice(12000.0);
                break;
            default:
                return null;
        }

        return metadata;
    }
} 