package com.realestate.admin.service;

import com.realestate.admin.dto.ContactInquiryRequest;
import com.realestate.admin.dto.ContactInquiryResponse;
import com.realestate.admin.entity.ContactInquiry;
import com.realestate.admin.entity.InquiryStatus;
import com.realestate.admin.repository.ContactInquiryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContactInquiryService {

    private final ContactInquiryRepository contactInquiryRepository;

    @Transactional
    public String submitInquiry(ContactInquiryRequest request) {
        ContactInquiry inquiry = new ContactInquiry();
        inquiry.setName(request.getName());
        inquiry.setEmail(request.getEmail());
        inquiry.setPhone(request.getPhone());
        inquiry.setRole(request.getRole());
        inquiry.setSubject("Contact Inquiry - " + request.getRole());
        inquiry.setMessage(request.getMessage());
        inquiry.setStatus(InquiryStatus.NEW);

        contactInquiryRepository.save(inquiry);
        return "Thank you for contacting us! We will get back to you soon.";
    }

    public List<ContactInquiryResponse> getAllInquiries() {
        return contactInquiryRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<ContactInquiryResponse> getInquiriesByStatus(InquiryStatus status) {
        return contactInquiryRepository.findByStatusOrderByCreatedAtDesc(status)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public ContactInquiryResponse updateInquiryStatus(Long id, InquiryStatus status, String adminResponse) {
        ContactInquiry inquiry = contactInquiryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inquiry not found"));

        inquiry.setStatus(status);
        if (adminResponse != null) {
            inquiry.setAdminResponse(adminResponse);
            inquiry.setRespondedAt(LocalDateTime.now());
        }

        inquiry = contactInquiryRepository.save(inquiry);
        return convertToResponse(inquiry);
    }

    public long getNewInquiriesCount() {
        return contactInquiryRepository.countByStatus(InquiryStatus.NEW);
    }
    
    @Transactional
    public void deleteInquiry(Long id) {
        if (!contactInquiryRepository.existsById(id)) {
            throw new RuntimeException("Inquiry not found");
        }
        contactInquiryRepository.deleteById(id);
    }

    private ContactInquiryResponse convertToResponse(ContactInquiry inquiry) {
        ContactInquiryResponse response = new ContactInquiryResponse();
        response.setId(inquiry.getId());
        response.setName(inquiry.getName());
        response.setEmail(inquiry.getEmail());
        response.setPhone(inquiry.getPhone());
        response.setRole(inquiry.getRole());
        response.setSubject(inquiry.getSubject());
        response.setMessage(inquiry.getMessage());
        response.setStatus(inquiry.getStatus());
        response.setCreatedAt(inquiry.getCreatedAt());
        response.setRespondedAt(inquiry.getRespondedAt());
        response.setAdminResponse(inquiry.getAdminResponse());
        return response;
    }
}