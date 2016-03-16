package de.dreamnetworx.fxmvp.base;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FxMvpDefaultNamingConventionConventionTest {

    @Test(expected = FxMvpException.class)
    public void shouldDetectInvalidPresenterName() throws Exception {
        //given
        FxMvpDefaultNamingConventionConvention cut = new FxMvpDefaultNamingConventionConvention();
        //when
        cut.getPresenterName("Filename");
        //then
        //must throw an FxMVPException because the filename follows not naming convention

    }

    @Test
    public void shouldGetPresenterNameByConvention() {
        //given
        FxMvpDefaultNamingConventionConvention cut = new FxMvpDefaultNamingConventionConvention();
        //when
        final String presenter = cut.getPresenterName("ExampleView");
        //then
        assertThat(presenter).isEqualTo("examplePresenterImpl");
    }
}