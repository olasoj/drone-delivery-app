package com.example.dronedeliveryapp.drone;

import com.example.dronedeliveryapp.drone.model.*;
import com.example.dronedeliveryapp.drone.service.DroneService;
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
class DroneInboundRestControllerTest {

    @Mock()
    private DroneService droneService;

    @InjectMocks
    private DroneInboundRestController droneInboundRestController;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DroneRegistrationRequest> jsonResultAttempt;

    @BeforeEach
    void setUp() {
        JacksonTester.initFields(this, new ObjectMapper());

        mvc = MockMvcBuilders.standaloneSetup(droneInboundRestController)
//                .setControllerAdvice(new SuperHeroExceptionHandler())
                .build();
    }

    @Test
    void registerDrone() throws Exception {

        //given
        DroneRegistrationResult droneRegistrationResult = new DroneRegistrationResult("SEQ1");
        DroneRegistrationRequest droneRegistrationRequest = new DroneRegistrationRequest("SEQ1", DroneModel.CRUISERWEIGHT, 100d);

        given(droneService.registerDrone(droneRegistrationRequest))
                .willReturn(droneRegistrationResult);

        //when
        MockHttpServletResponse response = mvc.perform(
                        post("/drone/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonResultAttempt.write(droneRegistrationRequest).getJson())
                )
                .andReturn().getResponse();

        // then
        then(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    void getIdleDrone() throws Exception {

        //given
        DroneDTOWithPageResult droneDTOWithPageResult = new DroneDTOWithPageResult(
                Collections.emptyList(),
                new PagingInfo()
        );

        given(droneService.retrieveIdleDrones(1))
                .willReturn(droneDTOWithPageResult);

        //when
        MockHttpServletResponse response = mvc.perform(
                        get("/drone/idle")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn().getResponse();

        // then
        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void getDroneBatteryCapacity() throws Exception {
        //given
        given(droneService.retrieveDroneBatteryLevel("SEQ1"))
                .willReturn(new Percentage(2));

        //when
        MockHttpServletResponse response = mvc.perform(
                        get("/drone/battery-capacity/SEQ1")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn().getResponse();

        // then
        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }
}