package com.realestate.admin.service;

import com.realestate.admin.dto.ContactInquiryRequest;
import com.realestate.admin.dto.ContactInquiryResponse;
import com.realestate.admin.entity.ContactInquiry;
import com.realestate.admin.entity.InquiryStatus;
import com.realestate.admin.repository.ContactInquiryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContactInquiryServiceTest {

    @Mock
    private ContactInquiryRepository contactInquiryRepository;

    @InjectMocks
    private ContactInquiryService contactInquiryService;

    private ContactInquiry testInquiry;
    private ContactInquiryRequest inquiryRequest;

    @BeforeEach
    void setUp() {
        testInquiry = new ContactInquiry();
        testInquiry.setId(1L);
        testInquiry.setName("Test User");
        testInquiry.setEmail("test@example.com");
        testInquiry.setMessage("Test message");
        testInquiry.setStatus(InquiryStatus.NEW);

        inquiryRequest = new ContactInquiryRequest();
        inquiryRequest.setName("New User");
        inquiryRequest.setEmail("new@example.com");
        inquiryRequest.setMessage("New inquiry message");
        inquiryRequest.setRole("BUYER");
        inquiryRequest.setPhone("1234567890");
    }

    @Test
    void submitInquiry_Success() {
        when(contactInquiryRepository.save(any(ContactInquiry.class))).thenReturn(testInquiry);

        String result = contactInquiryService.submitInquiry(inquiryRequest);

        assertNotNull(result);
        assertTrue(result.contains("Thank you"));
        verify(contactInquiryRepository).save(any(ContactInquiry.class));
    }

    @Test
    void getAllInquiries_Success() {
        List<ContactInquiry> inquiries = Arrays.asList(testInquiry);
        when(contactInquiryRepository.findAllByOrderByCreatedAtDesc()).thenReturn(inquiries);

        List<ContactInquiryResponse> result = contactInquiryService.getAllInquiries();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(contactInquiryRepository).findAllByOrderByCreatedAtDesc();
    }

    @Test
    void updateInquiryStatus_Success() {
        when(contactInquiryRepository.findById(anyLong())).thenReturn(Optional.of(testInquiry));
        when(contactInquiryRepository.save(any(ContactInquiry.class))).thenReturn(testInquiry);

        ContactInquiryResponse result = contactInquiryService.updateInquiryStatus(1L, InquiryStatus.RESPONDED, "Admin response");

        assertNotNull(result);
        verify(contactInquiryRepository).save(any(ContactInquiry.class));
    }

    @Test
    void updateInquiryStatus_NotFound_ThrowsException() {
        when(contactInquiryRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> 
            contactInquiryService.updateInquiryStatus(1L, InquiryStatus.RESPONDED, "Admin response"));
    }
}