package de.dreamnetworx.fxmvp.base;

import org.junit.Test;

public class FxMvpDefaultNamingConventionConventionTest {

    @Test(expected = FxMvpException.class)
    public void shouldDetectInvalidPresenterName() throws Exception {
        //given
        FxMvpDefaultNamingConventionConvention cut = new FxMvpDefaultNamingConventionConvention();
        //when
        cut.getPresenterName("filename");
        //then
        //must throw an FxMVPException because the filename follows not naming convention

    }
}