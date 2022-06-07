package com.hydro.annotations.aspects;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.annotation.Annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hydro.annotations.interfaces.HasAccess;
import com.hydro.app.user.client.domain.enums.WebRole;
import com.hydro.common.exceptions.InsufficientPermissionsException;
import com.hydro.factory.annotations.HydroTest;
import com.hydro.jwt.utility.JwtHolder;

@HydroTest
@ExtendWith(MockitoExtension.class)
public class HasAccessAspectTest {
    @Mock
    private ProceedingJoinPoint proceedingJoinPoint;

    @Mock
    private JwtHolder jwtHolder;

    @InjectMocks
    private HasAccessAspect accessAspcect;

    @Test
    public void testHasAccessAdminAccess() throws Throwable {
        when(jwtHolder.getWebRole()).thenReturn(WebRole.ADMIN);
        accessAspcect.access(proceedingJoinPoint, getHasAccessAnnotationInstance(WebRole.ADMIN));
        verify(proceedingJoinPoint).proceed();
    }

    @ParameterizedTest
    @EnumSource(WebRole.class)
    public void testHasAccessAllAccess(WebRole role) throws Throwable {
        when(jwtHolder.getWebRole()).thenReturn(role);
        accessAspcect.access(proceedingJoinPoint, getHasAccessAnnotationInstance(WebRole.USER));
        verify(proceedingJoinPoint).proceed();
    }

    @ParameterizedTest
    @EnumSource(value = WebRole.class, names = { "ADMIN" }, mode = EnumSource.Mode.EXCLUDE)
    public void testHasAccessInsufficentPermissions(WebRole role) throws Throwable {
        when(jwtHolder.getWebRole()).thenReturn(role);
        assertThrows(InsufficientPermissionsException.class,
                () -> accessAspcect.access(proceedingJoinPoint, getHasAccessAnnotationInstance(WebRole.ADMIN)));
        verify(proceedingJoinPoint, never()).proceed();
    }

    private HasAccess getHasAccessAnnotationInstance(WebRole role) {
        return new HasAccess() {
            @Override
            public WebRole value() {
                return role;
            }

            @Override
            public Class<? extends Annotation> annotationType() {
                return HasAccess.class;
            }
        };
    }
}
