package com.hrs.hotelbooking.builder;

import com.hrs.hotelbooking.enumextension.CancelDetails_Enum;
import com.hrs.hotelbooking.enumextension.HotelDetails_Enum;
import com.hrs.hotelbooking.model.*;
import com.hrs.hotelbooking.utils.Constant;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Component
public class CancellationDetailBuilder {

    private static final int INITIAL_LINE_NO = 1000;
    private static final int LINE_NO_INCREMENT = 1000;

    private String generateCancellationId() {
        return "CAN" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private int generateLineNo(int index) {
        if (index > 0) {
            return INITIAL_LINE_NO + index;
        }
        return INITIAL_LINE_NO + (index * LINE_NO_INCREMENT);
    }

    public List<CancellationDetails> build(ServiceContext context, CancellationRequest request) {
        List<CancellationDetails> detailsList = new ArrayList<>();
        HotelierCancelResponse hotelierCancelResponse = (HotelierCancelResponse) context.getThirdPartyResponseList().get(Constant.HOTELIER_CANCEL_RESPONSE);
        int index = 0;
        if (!CollectionUtils.isEmpty(context.getBooking().getCancellationDetails())) {
            index = context.getBooking().getCancellationDetails().stream().max(Comparator.comparing(CancellationDetails::getLineNo)).map(CancellationDetails::getLineNo).orElse(0);
        }
        for (CancellationLine cancellationLine : request.getCancellationLines()) {
            HotelDetails hotelDetails = context.getBooking().getHotelDetails().stream().filter(detail -> detail.getLineno() == cancellationLine.getRoomLineNo()).findFirst().orElse(null);
            if (hotelDetails == null) {
                continue;
            }
            CancellationDetails details = new CancellationDetails();
            double penaltyAmount = hotelierCancelResponse.getHotelierPenaltyDataList().stream().filter(penalty -> penalty.getRoomLineNo() == cancellationLine.getRoomLineNo()).findFirst().orElse(null).getPenalty();
            if (request.getRequestType() == CancelDetails_Enum.RequestType.DateChange.getCode()) {
                penaltyAmount = 0;
            }
            details.setBookingId(request.getBookingId());
            details.setCancellationId(generateCancellationId());
            details.setLineNo(generateLineNo(index));
            details.setRoomLineNo(cancellationLine.getRoomLineNo());
            details.setCancellationDate(new Date());
            details.setRefundAmount(hotelDetails.getSellingPrice() - penaltyAmount);
            details.setCancellationReason(request.getReason());
            details.setCancellationStatus(HotelDetails_Enum.BookingStatus.CANCELLED.toString());
            details.setCancellationBy(request.getUserId());
            details.setRefundMode(CancelDetails_Enum.Refund_Adjustment.Refund_To_Original_Card.getCode());
            details.setRequestType(request.getRequestType());
            detailsList.add(details);
        }

        return detailsList;
    }
}
