package com.hrs.hotelbooking.model;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import com.hrs.hotelbooking.repository.HotelRepository;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("dev")
//@Transactional
class HotelTest {

    @Autowired
    private HotelRepository hotelRepository;

    @Test
    void testCreateHotels() {
        // Mumbai Hotels
        Hotel tajMahal = new Hotel();
        tajMahal.setCode("TAJ001");
        tajMahal.setName("Taj Mahal Palace");
        tajMahal.setCity("Mumbai");
        tajMahal.setStatus(1);
        tajMahal.setContactno("022-6665-3366");
        tajMahal.setCategory("5-star");
        tajMahal.setVendorno("V001");
        tajMahal.setAddress("Apollo Bunder, Colaba, Mumbai");
        tajMahal.setCompanyCode("TAJ");

        Hotel oberoi = new Hotel();
        oberoi.setCode("OBR002");
        oberoi.setName("The Oberoi");
        oberoi.setCity("Mumbai");
        oberoi.setStatus(1);
        oberoi.setContactno("022-6632-5757");
        oberoi.setCategory("5-star");
        oberoi.setVendorno("V002");
        oberoi.setAddress("Nariman Point, Marine Drive, Mumbai");
        oberoi.setCompanyCode("OBR");

        // Delhi Hotels
        Hotel leela = new Hotel();
        leela.setCode("LEL003");
        leela.setName("The Leela Palace");
        leela.setCity("Delhi");
        leela.setStatus(1);
        leela.setContactno("011-3933-1234");
        leela.setCategory("5-star");
        leela.setVendorno("V003");
        leela.setAddress("Diplomatic Enclave, Chanakyapuri, Delhi");
        leela.setCompanyCode("LEL");

        Hotel radisson = new Hotel();
        radisson.setCode("RAD004");
        radisson.setName("Radisson Blu Plaza");
        radisson.setCity("Delhi");
        radisson.setStatus(1);
        radisson.setContactno("011-4111-7777");
        radisson.setCategory("4-star");
        radisson.setVendorno("V004");
        radisson.setAddress("Connaught Place, Delhi");
        radisson.setCompanyCode("RAD");

        // Punjab Hotels
        Hotel tajChandigarh = new Hotel();
        tajChandigarh.setCode("TAJ005");
        tajChandigarh.setName("Taj Chandigarh");
        tajChandigarh.setCity("Chandigarh");
        tajChandigarh.setStatus(1);
        tajChandigarh.setContactno("0172-661-3000");
        tajChandigarh.setCategory("5-star");
        tajChandigarh.setVendorno("V005");
        tajChandigarh.setAddress("Sector 17, Chandigarh");
        tajChandigarh.setCompanyCode("TAJ");

        Hotel hyattAmritsar = new Hotel();
        hyattAmritsar.setCode("HYT006");
        hyattAmritsar.setName("Hyatt Regency Amritsar");
        hyattAmritsar.setCity("Amritsar");
        hyattAmritsar.setStatus(1);
        hyattAmritsar.setContactno("0183-519-1234");
        hyattAmritsar.setCategory("4-star");
        hyattAmritsar.setVendorno("V006");
        hyattAmritsar.setAddress("Near Golden Temple, Amritsar");
        hyattAmritsar.setCompanyCode("HYT");

        // Additional Hotels
        Hotel marriottMumbai = new Hotel();
        marriottMumbai.setCode("MAR007");
        marriottMumbai.setName("JW Marriott Mumbai");
        marriottMumbai.setCity("Mumbai");
        marriottMumbai.setStatus(1);
        marriottMumbai.setContactno("022-6693-3000");
        marriottMumbai.setCategory("5-star");
        marriottMumbai.setVendorno("V007");
        marriottMumbai.setAddress("Juhu Tara Road, Mumbai");
        marriottMumbai.setCompanyCode("MAR");

        Hotel itcDelhi = new Hotel();
        itcDelhi.setCode("ITC008");
        itcDelhi.setName("ITC Maurya");
        itcDelhi.setCity("Delhi");
        itcDelhi.setStatus(1);
        itcDelhi.setContactno("011-2611-2233");
        itcDelhi.setCategory("5-star");
        itcDelhi.setVendorno("V008");
        itcDelhi.setAddress("Sardar Patel Marg, Delhi");
        itcDelhi.setCompanyCode("ITC");

        // Save all hotels
        hotelRepository.save(tajMahal);
        hotelRepository.save(oberoi);
        hotelRepository.save(leela);
        hotelRepository.save(radisson);
        hotelRepository.save(tajChandigarh);
        hotelRepository.save(hyattAmritsar);
        hotelRepository.save(marriottMumbai);
        hotelRepository.save(itcDelhi);

        // Verify saved hotels
        assertNotNull(hotelRepository.findById("TAJ001"));
        assertNotNull(hotelRepository.findById("OBR002"));
        assertNotNull(hotelRepository.findById("LEL003"));
        assertNotNull(hotelRepository.findById("RAD004"));
        assertNotNull(hotelRepository.findById("TAJ005"));
        assertNotNull(hotelRepository.findById("HYT006"));
        assertNotNull(hotelRepository.findById("MAR007"));
        assertNotNull(hotelRepository.findById("ITC008"));

        // Verify hotel details
        Hotel savedTaj = hotelRepository.findById("TAJ001").orElse(null);
        assertNotNull(savedTaj);
        assertEquals("Taj Mahal Palace", savedTaj.getName());
        assertEquals("Mumbai", savedTaj.getCity());
        assertEquals("5-star", savedTaj.getCategory());
    }
} 