package com.example.dronedeliveryapp.delivery;

import com.example.dronedeliveryapp.delivery.model.DeliveryRequest;
import com.example.dronedeliveryapp.delivery.model.GetLoadedMedicationResult;
import com.example.dronedeliveryapp.delivery.service.DeliveryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class DeliveryInboundRestControllerTest {


    @Mock()
    private DeliveryService deliveryService;

    @InjectMocks
    private DeliveryInboundRestController deliveryInboundRestController;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DeliveryRequest> jsonResultAttempt;

    @BeforeEach
    void setUp() {
        JacksonTester.initFields(this, new ObjectMapper());

        mvc = MockMvcBuilders.standaloneSetup(deliveryInboundRestController)
//                .setControllerAdvice(new SuperHeroExceptionHandler())
                .build();
    }


    @Test
    void loadDrone() throws Exception {
        //given
        DeliveryRequest deliveryRequest = new DeliveryRequest(Collections.emptySet());
        given(deliveryService.loadDroneWithMedicationItem("SEQ1", deliveryRequest.medicationRegistrationRequests()))
                .willReturn(true);

        //when
        MockHttpServletResponse response = mvc.perform(
                        post("/delivery/SEQ1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonResultAttempt.write(deliveryRequest).getJson())
                )
                .andReturn().getResponse();

        // then
        then(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    void getLoadedDrone() throws Exception {

        GetLoadedMedicationResult getLoadedMedicationResult = GetLoadedMedicationResult.testGetLoadedMedicationResult();
        //given
        given(deliveryService.retrieveLoadedMedicationForAGivenDrone("SEQ1", 1))
                .willReturn(getLoadedMedicationResult);

        //when
        MockHttpServletResponse response = mvc.perform(
                        get("/delivery/SEQ1")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn().getResponse();

        // then
        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }
}