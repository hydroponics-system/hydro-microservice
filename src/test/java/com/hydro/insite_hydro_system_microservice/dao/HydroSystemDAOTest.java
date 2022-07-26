package com.hydro.insite_hydro_system_microservice.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import com.google.common.collect.Sets;
import com.hydro.factory.annotations.HydroDaoTest;
import com.hydro.factory.config.test.HydroSystemDAOTestConfig;
import com.hydro.factory.data.HydroSystemFactoryData;
import com.hydro.insite_hydro_system_microservice.client.domain.HydroSystem;
import com.hydro.insite_hydro_system_microservice.client.domain.PartNumber;
import com.hydro.insite_hydro_system_microservice.client.domain.request.HydroSystemGetRequest;

@ContextConfiguration(classes = HydroSystemDAOTestConfig.class)
@Sql("/scripts/system/hydrosystemDAO/init.sql")
@HydroDaoTest
public class HydroSystemDAOTest {

    @Autowired
    private HydroSystemDAO dao;

    @ParameterizedTest
    @MethodSource("provideHydroGetRequests")
    public void testGetSystemsEmptyRequest(HydroSystemGetRequest input, int resultLength) {
        assertEquals(resultLength, dao.getSystems(input).size(), "List size");
    }

    @ParameterizedTest
    @MethodSource("provideSystems")
    public void testRegisterSystem(HydroSystem sys, String resultStatus) {
        String actualResult = "";

        try {
            dao.registerSystem(sys);
            actualResult = "VALID";
        }
        catch(Exception e) {
            actualResult = "ERROR";
            System.out.println(e.getMessage() + "\n");
        }

        assertEquals(resultStatus, actualResult, "Result of system register");
    }

    @Test
    public void testUnregisterSystem() {
        HydroSystemGetRequest req = new HydroSystemGetRequest();
        req.setId(Sets.newHashSet(1));
        HydroSystem sys = dao.getSystems(req).get(0);

        assertEquals(1, sys.getId(), "System id is 1");

        dao.unregisterSystem(1);
        assertEquals(0, dao.getSystems(req).size(), "System should be deleted");
    }

    @Test
    public void testGetNextSystemId() {
        HydroSystemGetRequest req = new HydroSystemGetRequest();
        req.setId(Sets.newHashSet(4));

        assertEquals(4, dao.getNextSystemId(), "Next id should be 4");
        assertEquals(dao.registerSystem(HydroSystemFactoryData.hydroSystem()), 4, "System created with id 4");
        assertEquals(222222, dao.getSystems(req).get(0).getPartNumber().getSystemId(), "Part Number");
    }

    private static Stream<Arguments> provideHydroGetRequests() {
        HydroSystemGetRequest t1 = new HydroSystemGetRequest(); // ids
        HydroSystemGetRequest t2 = new HydroSystemGetRequest(); // uuid
        HydroSystemGetRequest t3 = new HydroSystemGetRequest(); // part number
        HydroSystemGetRequest t4 = new HydroSystemGetRequest(); // name

        t1.setId(Sets.newHashSet(1, 2));
        t2.setUuid(Sets.newHashSet("b59892f8-9d4f-32e6-8af3-c1a5c301e04c"));
        t3.setPartNumber(Sets.newHashSet("276674D000002"));
        t4.setName(Sets.newHashSet("partNumberTest"));

        return Stream.of(Arguments.of(t1, 2), Arguments.of(t2, 1), Arguments.of(t3, 1), Arguments.of(t4, 1));
    }

    private static Stream<Arguments> provideSystems() {
        HydroSystem t1 = HydroSystemFactoryData.hydroSystem(); // VALID: default
        HydroSystem t2 = HydroSystemFactoryData.hydroSystem(); // ERROR: Duplicate uuid
        HydroSystem t3 = HydroSystemFactoryData.hydroSystem(); // ERROR: Duplicate part Number
        HydroSystem t4 = HydroSystemFactoryData.hydroSystem(); // VALID: Same name
        HydroSystem t5 = HydroSystemFactoryData.hydroSystem(); // ERROR: User does not exist

        t2.setUuid("71d9ec65-265b-3388-a6e4-654128db3263");
        t3.setPartNumber(new PartNumber("276674D000002"));
        t4.setName("testSystem");
        t5.setInsertUserId(100);

        return Stream.of(Arguments.of(t1, "VALID"), Arguments.of(t2, "ERROR"), Arguments.of(t3, "ERROR"),
                         Arguments.of(t4, "VALID"), Arguments.of(t5, "ERROR"));
    }
}
