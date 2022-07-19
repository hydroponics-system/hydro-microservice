package com.hydro.factory.data;

import com.hydro.app.system.client.domain.HydroSystem;
import com.hydro.app.system.client.domain.PartNumber;

/**
 * Class for holding common test data to be used in test classes.
 * 
 * @author Sam Butler
 * @since May 26, 2022
 */
public class HydroSystemFactoryData {

    public static HydroSystem hydroSystem() {
        HydroSystem sys = new HydroSystem();
        sys.setId(1);
        sys.setUuid("71d9ec65-265b-3388-a6e4-654128dr5678");
        sys.setPartNumber(new PartNumber("111111D222222"));
        sys.setName("testName");
        sys.setInsertUserId(1);
        return sys;
    }
}
