package com.hydro.app.system.service;

import static com.hydro.factory.data.HydroSystemFactoryData.hydroSystem;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.hydro.ActiveProfile;
import com.hydro.app.system.client.domain.HydroSystem;
import com.hydro.app.system.client.domain.request.HydroSystemGetRequest;
import com.hydro.app.system.dao.HydroSystemDAO;
import com.hydro.app.user.client.domain.enums.WebRole;
import com.hydro.common.enums.Environment;
import com.hydro.common.exceptions.InsufficientPermissionsException;
import com.hydro.common.exceptions.NotFoundException;
import com.hydro.common.util.HydroLogger;
import com.hydro.factory.annotations.HydroServiceTest;
import com.hydro.jwt.utility.JwtHolder;
import com.hydro.jwt.utility.JwtTokenUtil;

@HydroServiceTest
public class HydroSystemServiceTest {

    @Mock
    private HydroSystemDAO hydroSystemDAO;

    @Mock
    private ActiveProfile activeProfile;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private JwtHolder jwtHolder;

    @Mock
    private HydroLogger logger;

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

    @Test
    public void testGetSystemByUUIDExists() {
        when(hydroSystemDAO.getSystems(any(HydroSystemGetRequest.class))).thenReturn(Arrays.asList(hydroSystem()));

        HydroSystem sys = service.getSystemByUUID("71d9ec65-265b-3388-a6e4-654128dr5678");

        verify(hydroSystemDAO).getSystems(any(HydroSystemGetRequest.class));
        assertEquals("71d9ec65-265b-3388-a6e4-654128dr5678", sys.getUUID(), "System ID");
    }

    @Test
    public void testGetSystemByUUIDNotFound() {
        when(hydroSystemDAO.getSystems(any(HydroSystemGetRequest.class))).thenReturn(Collections.emptyList());

        NotFoundException e = assertThrows(NotFoundException.class, () -> service.getSystemByUUID("invalid"));

        verify(hydroSystemDAO).getSystems(any(HydroSystemGetRequest.class));
        assertEquals("UUID not found for id: 'invalid'", e.getMessage(), "Exception Message");
    }

    @Test
    public void testRegisterSystem() {
        when(hydroSystemDAO.registerSystem(any(HydroSystem.class))).thenReturn(10);
        when(hydroSystemDAO.getNextSystemId()).thenReturn(10L);
        when(activeProfile.getEnvironment()).thenReturn(Environment.DEVELOPMENT);
        when(jwtHolder.getUserId()).thenReturn(1);

        HydroSystem s = service.registerSystem("testSystem");

        verify(hydroSystemDAO).registerSystem(any(HydroSystem.class));
        assertEquals(10, s.getId(), "System Id");
        assertEquals("testSystem", s.getName(), "System Name");
        assertEquals(Environment.DEVELOPMENT, s.getPartNumber().getEnvironment(), "Part Number Environment");
        assertEquals(10, s.getPartNumber().getSystemId(), "Part Number System Id");
        assertEquals(UUID.nameUUIDFromBytes(s.getPartNumber().toString().getBytes()).toString(), s.getUUID(), "UUID");
        assertEquals(1, s.getInsertUserId(), "Insert User ID");
    }

    @Test
    public void testUnregisterSystemRoleAdmin() {
        when(hydroSystemDAO.getSystems(any(HydroSystemGetRequest.class))).thenReturn(Arrays.asList(hydroSystem()));
        when(jwtHolder.getUserId()).thenReturn(12);
        when(jwtHolder.getWebRole()).thenReturn(WebRole.ADMIN);

        service.unregisterSystem(8);
        verify(hydroSystemDAO).unregisterSystem(8);
    }

    @Test
    public void testUnregisterSystemUserWhoCreatedSystem() {
        when(hydroSystemDAO.getSystems(any(HydroSystemGetRequest.class))).thenReturn(Arrays.asList(hydroSystem()));
        when(jwtHolder.getUserId()).thenReturn(1);

        service.unregisterSystem(8);
        verify(hydroSystemDAO).unregisterSystem(8);
    }

    @Test
    public void testUnregisterSystemInsufficentPermissions() {
        when(hydroSystemDAO.getSystems(any(HydroSystemGetRequest.class))).thenReturn(Arrays.asList(hydroSystem()));
        when(jwtHolder.getUserId()).thenReturn(12);
        when(jwtHolder.getWebRole()).thenReturn(WebRole.USER);

        InsufficientPermissionsException e = assertThrows(InsufficientPermissionsException.class,
                () -> service.unregisterSystem(8));

        verify(hydroSystemDAO, never()).unregisterSystem(anyInt());
        assertEquals("Insufficient permissions! You can not unregister this system.", e.getMessage(),
                "Exception Message");
    }
}
