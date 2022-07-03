package com.commerce.order.application.partner;

import com.commerce.order.domain.partner.PartnerCommand;
import com.commerce.order.domain.partner.PartnerInfo;
import com.commerce.order.domain.partner.PartnerService;
import com.commerce.order.domain.partner.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PartnerFacade {
    private final PartnerService partnerService;
    private final NotificationService notificationService;

    public PartnerInfo registerPartner(PartnerCommand command) {
        //1. partnerService regis
        //2. send email
        var partnerInfo = partnerService.registerPartner(command);
        notificationService.sendEmail(partnerInfo.getEmail(), "title", "description");
        return partnerInfo;
    }
}
