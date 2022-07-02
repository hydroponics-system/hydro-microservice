package com.hydro.app.system.service;

import static com.hydro.factory.data.HydroSystemFactoryData.hydroSystem;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.hydro.ActiveProfile;
import com.hydro.app.system.client.domain.HydroSystem;
import com.hydro.app.system.client.domain.request.HydroSystemGetRequest;
import com.hydro.app.system.dao.HydroSystemDAO;
import com.hydro.common.exceptions.NotFoundException;
import com.hydro.factory.annotations.HydroServiceTest;
import com.hydro.jwt.utility.JwtTokenUtil;

@HydroServiceTest
public class HydroSystemServiceTest {

    @Mock
    private HydroSystemDAO hydroSystemDAO;

    @Mock
    private ActiveProfile activeProfile;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @InjectMocks
    private HydroSystemService service;

    @Test
    public void testGetSystems() {
        service.getSystems(new HydroSystemGetRequest());

        verify(hydroSystemDAO).getSystems(any(HydroSystemGetRequest.class));
    }

    @Test
    public void testGetSystemByIdExists() {
        when(hydroSystemDAO.getSystems(any(HydroSystemGetRequest.class))).thenReturn(Arrays.asList(hydroSystem()));

        HydroSystem sys = service.getSystemById(1);

        verify(hydroSystemDAO).getSystems(any(HydroSystemGetRequest.class));
        assertEquals(1, sys.getId(), "System ID");
    }

    @Test
    public void testGetSystemByIdNotFound() {
        when(hydroSystemDAO.getSystems(any(HydroSystemGetRequest.class))).thenReturn(Collections.emptyList());

        NotFoundException e = assertThrows(NotFoundException.class, () -> service.getSystemById(12));

        verify(hydroSystemDAO).getSystems(any(HydroSystemGetRequest.class));
        assertEquals("ID not found for id: '12'", e.getMessage(), "Exception Message");
    }

}
